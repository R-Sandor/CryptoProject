package dev.findfirst.CryptoProjectFinal.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import dev.findfirst.CryptoProjectFinal.crypto.KeyGenerator.KeysRec;

/** Baby Step Gaint step is an attack used against Diffie-Hellman/Algamal Crypto Systems. */
@Component
@Slf4j
public class BabyStepGiaintStep {

  private long fastMod(long a, long b, long p) {
    long retVal = 1; // Initialize result
    a = a % p; // Update x if it is more than or equal to p
    while (b > 0) {
      // If y is odd, multiply x with result
      if ((b & 1) > 0) retVal = (retVal * a) % p;

      // y must be even now
      b = b >> 1; // y = y/2
      a = (a * a) % p;
    }
    return retVal;
  }

  private long discreteLogarithm(long a, long kpub, long p, long kpriv) {
    int m = (int) Math.ceil((Math.sqrt(p - 1)));

    Map<Long, Integer> lookup = new HashMap<>();

    log.debug("a {}, b {}, p {}, privKey {}", a, kpub, p, kpriv);
    // Store all values of a^(m*i) of LHS.
    // I really think of this as the right hand side of the equation: alpha^
    long cur = 1;
    for (int i = 0; i < m; i++) {
      lookup.put(cur, i);
      cur = (cur * a) % p;
    }

    // find a^-m
    // fermat's little theorem to find the inverse!
    long mInv = fastMod(a, m * (p - 2), p);

    for (int i = 0; i < m; i++) {
      // Calculate (a^-m)xg * b and check
      // for collision
      long y = (kpub * (fastMod(mInv, i, p))) % p;
      if (lookup.getOrDefault(y, 0) > 0) {
        long val = i * m + lookup.get(y);
        log.debug("Collisions {}", val);
        /*
         * This is a simism. As there possible collisions.
         * For example 5^13787 and 5^62927 mod 65221 both provide
         * the public key of 39327.
         *
         * In reality one would need to check if
         * the private key when raised to the power of a given public
         * key creates the session key.
         */
        if (val == kpriv) return val;
      }
    }
    return -1;
  }

  /**
   * Calculates the time it takes to solve for x using Baby Step Giant Step
   *
   * @param a
   * @param b
   * @param p
   * @return return solve time
   */
  public long solveTimer(KeysRec keysRec) {
    long start = System.currentTimeMillis();
    long x = discreteLogarithm(keysRec.a(), keysRec.kpub(), keysRec.p(), keysRec.kpriv());
    log.debug("Key Found: {}", x);
    return System.currentTimeMillis() - start;
  }

  public long bigKeyDiscrete(long alpha, int bitLength) {
    long start = System.currentTimeMillis();
    SecureRandom rnd = new SecureRandom();
    BigInteger tmp;
    BigInteger a = BigInteger.valueOf(alpha);
    BigInteger p = BigInteger.probablePrime(bitLength, rnd);
    BigInteger kpriv = BigInteger.probablePrime(bitLength, rnd);
    BigInteger kpub = a.modPow(kpriv, p);

    int comp = kpriv.compareTo(p.subtract(BigInteger.TWO));
    // If kpriv and p are equal then subtract 2 from kpriv.
    if (comp == 0) {
      kpriv = kpriv.subtract(BigInteger.TWO);
    }
    // if kpriv is greater than p
    // swap them, take the space to time trade off and use a tmp.
    else if (comp > 0) {
      tmp = kpriv;
      kpriv = p;
      p = tmp;
    }

    BigInteger m = p.subtract(BigInteger.ONE).sqrt();

    Map<BigInteger, BigInteger> lookup = new HashMap<>();

    log.debug("a {}, b {}, p {}, privKey {}", a, kpub, p, kpriv);
    // Store all values of a^(m*i) of LHS.
    // I really think of this as the right hand side of the equation: alpha^
    BigInteger cur = BigInteger.ONE;
    for (BigInteger i = BigInteger.ZERO; i.compareTo(m) < 0; i = i.add(BigInteger.ONE)) {
      lookup.put(cur, i);
      cur = cur.multiply(a).mod(p);
    }

    // find a^-m
    // fermat's little theorem to find the inverse!
    BigInteger fermats = m.multiply(p.subtract(BigInteger.TWO));
    // same thing as the fastMod for the smaller keys
    BigInteger mInv = a.modPow(fermats, p);

    for (BigInteger i = BigInteger.ZERO; i.compareTo(m) < 0; i = i.add(BigInteger.ONE)) {
      // Calculate (a^-m)xg * b and check
      // for collision
      BigInteger y = kpub.multiply(mInv.modPow(i, p)).mod(p);
      if (lookup.getOrDefault(y, BigInteger.ZERO).compareTo(BigInteger.ZERO) > 0) {
        BigInteger val = i.multiply(m).add(lookup.get(y));
        log.debug("Collisions {}", val);
        if (val.equals(kpriv)) { 
          log.debug("Keyfound {}", val.toString());
          break;
        }
      }
    }
    return System.currentTimeMillis() - start;
  }
}
