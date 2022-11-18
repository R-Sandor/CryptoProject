package dev.findfirst.CryptoProjectFinal.utility;

import org.springframework.stereotype.Component;

@Component
public class KeyGenerator {

  public int genRandomInBitSize(int keySize) {
    --keySize; // accounting the operator applied to the base.
    int max = 2 << keySize;
    int min = (2 << keySize - 1) + 1;
    return (int) ((Math.random() * (max - min)) + min);
  }

  /**
   * The same as generate genRandomInBitSize but accounts for the result of p.
   *
   * @param keySize the bit size
   * @return a valid priv prime value
   */
  public int genDprime(int keySize, int max) {
    --keySize; // accounting the operator applied to the base.
    int pMinus2 = max - 2;
    int min = (2 << keySize - 1) + 1;
    return (int) ((Math.random() * (pMinus2 - min)) + min);
  }

  public long fastMod(long c, int y, int p) {
    long retVal = 1; // Initialize result
    c = c % p; // Update c if it is more than or equal to p
    while (y > 0) {
      // If y is odd, multiply x with result
      if ((y & 1) > 0) retVal = (retVal * c) % p;

      // y must be even now
      y = y >> 1; // y = y/2
      c = (c * c) % p;
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
  public boolean primeTest(int pt, int s) {
    long z = fastMod(2, pt - 1, pt);
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
  public int generatePrime(int primeSize) {
    int prime = genRandomInBitSize(primeSize);
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
  public KeysRec generateKeys(int alpha, int keySize) {
    int p = generatePrime(keySize);
    int kpriv = genDprime(keySize, p);
    long kpub = fastMod(alpha, kpriv, p);
    return new KeysRec(kpub, kpriv, alpha, p);
  }

  public record KeysRec(long kpub, int kpriv, int a, int p) {}
}
