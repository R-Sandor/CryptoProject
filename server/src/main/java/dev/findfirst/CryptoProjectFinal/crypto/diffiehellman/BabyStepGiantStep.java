package dev.findfirst.CryptoProjectFinal.crypto.diffiehellman;

import dev.findfirst.CryptoProjectFinal.crypto.SolveTimer;
import dev.findfirst.CryptoProjectFinal.crypto.diffiehellman.DHKeyGenerator.DHKeys;
import dev.findfirst.CryptoProjectFinal.crypto.diffiehellman.DHKeyGenerator.DHKeysRec;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** Baby Step Gaint step is an attack used against Diffie-Hellman/Algamal Crypto Systems. */
@Component
@Slf4j
public class BabyStepGiantStep implements SolveTimer {

  /**
   * This is the same thing as BigInteger's modPow.
   *
   * @param a the base.
   * @param b the exponent
   * @param p the mod p.
   * @return a^m mod p
   */
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

  /**
   * Small key Baby Step Gaint Step
   *
   * @param a the base
   * @param kpub public key
   * @param p mod p parameter
   * @param kpriv private key
   * @return the value of the found key.
   */
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
   * @param DHKeysRec small keys record.
   * @return return solve time
   */
  public long solveTime(DHKeysRec keysRec) {
    long start = System.currentTimeMillis();
    long x = discreteLogarithm(keysRec.a(), keysRec.kpub(), keysRec.p(), keysRec.kpriv());
    log.debug("Key Found: {}", x);
    return System.currentTimeMillis() - start;
  }

  /**
   * Handles keys larger than 2^30.
   *
   * @param keysRec public keys and encryption parametes
   * @return solve time in milliseconds.
   */
  @Override
  public long solveTime(DHKeys keysRec) {
    long start = System.currentTimeMillis();
    String x = bigKeyDiscrete(keysRec.kpub(), keysRec.kpriv(), keysRec.a(), keysRec.p());
    log.debug("Key Found: {}", x);
    return System.currentTimeMillis() - start;
  }

  /**
   * Solve the DLP using Baby Step Gain Step using keys larger than 2^30
   *
   * @param kpub public key
   * @param kpriv private key
   * @param a base
   * @param p parameter
   * @return value of the found private key.
   */
  public String bigKeyDiscrete(BigInteger kpub, BigInteger kpriv, BigInteger a, BigInteger p) {
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
          return val.toString();
        }
      }
    }
    return "-1";
  }
}
