package dev.findfirst.CryptoProjectFinal.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KeyGenerator {

  public long genRandomInBitSize(int keySize) {
    --keySize; // accounting the operator applied to the base.
    long max = 2 << keySize;
    long min = (2 << keySize - 1) + 1;
    return (long) ((Math.random() * (max - min)) + min);
  }

  /**
   * The same as generate genRandomInBitSize but accounts for the result of p.
   *
   * @param keySize the bit size
   * @return a valid priv prime value
   */
  public long genDprime(int keySize, long p) {
    --keySize; // accounting the operator applied to the base.
    long pMinus2 = p - 2;
    long min = (2 << keySize - 1) + 1;
    return (long) ((Math.random() * (pMinus2 - min)) + min);
  }

  public long fastMod(long c, long l, long pt) {
    long retVal = 1; // Initialize result
    c = c % pt; // Update c if it is more than or equal to p
    while (l > 0) {
      // If y is odd, multiply x with result
      if ((l & 1) > 0) retVal = (retVal * c) % pt;

      // y must be even now
      l = l >> 1; // y = y/2
      c = (c * c) % pt;
    }
    return retVal;
  }

  /**
   * Miller-Rabin Primality Test
   *
   * @param pt prime candidate
   * @param s security paramater
   * @return if it likely a prime
   */
  public boolean primeTest(long pt, int s) {
    long z = fastMod(2l, pt - 1, pt);
    if (z == 1) return true;
    for (int i = 0; i < s - 1; i++) {
      if (z == pt - 1) return true;
      z = fastMod(z, 2, pt);
    }
    if (z == pt - 1) return true;
    return false;
  }

  /**
   * Generates a prime of a given size that is prime.
   *
   * @param primeSize
   * @return
   */
  public long generatePrime(int primeSize) {
    long prime = genRandomInBitSize(primeSize);
    while (!primeTest(prime, 4)) {
      prime = genRandomInBitSize(primeSize);
    }
    return prime;
  }

  /**
   * Generate keys kpub, kpriv, and p parameter
   *
   * @param keySize key size in bits
   */
  public KeysRec generateKeys(long alpha, int keySize) {
    long p = generatePrime(keySize);
    long kpriv = genDprime(keySize, p);
    long kpub = fastMod(alpha, kpriv, p);
    return new KeysRec(kpub, kpriv, alpha, p);
  }

  public BigKeys generateBigKeys(long alpha, int keySize) {
    SecureRandom rnd = new SecureRandom();
    BigInteger tmp;
    BigInteger a = BigInteger.valueOf(alpha);
    log.debug("a {}", a);
    BigInteger p = BigInteger.probablePrime(keySize, rnd);
    BigInteger kpriv = BigInteger.probablePrime(keySize, rnd);

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
    BigInteger kpub = a.modPow(kpriv, p);
    log.debug("kpub {}", kpub);

    return new BigKeys(kpub, kpriv, a, p, keySize);
  }

  public record KeysRec(long kpub, long kpriv, long a, long p) {}

  public record BigKeys(
      BigInteger kpub, BigInteger kpriv, BigInteger a, BigInteger p, int bitsize) {}
}
