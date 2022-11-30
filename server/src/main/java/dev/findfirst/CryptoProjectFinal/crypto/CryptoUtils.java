package dev.findfirst.CryptoProjectFinal.crypto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CryptoUtils {

  public static long genRandomInBitSize(int keySize) {
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
  public static long genDprime(int keySize, long p) {
    --keySize; // accounting the operator applied to the base.
    long pMinus2 = p - 2;
    long min = (2 << keySize - 1) + 1;
    return (long) ((Math.random() * (pMinus2 - min)) + min);
  }

  public static long fastMod(long a, long power, long n) {
    long retVal = 1; // Initialize result
    a = a % n; // Update c if it is more than or equal to p
    while (power > 0) {
      // If y is odd, multiply x with result
      if ((power & 1) > 0) retVal = (retVal * a) % n;

      // y must be even now
      power = power >> 1; // y = y/2
      a = (a * a) % n;
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
  public static boolean primeTest(long pt, int s) {
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
  public static long generatePrime(int primeSize) {
    long prime = genRandomInBitSize(primeSize);
    while (!primeTest(prime, 4)) {
      prime = genRandomInBitSize(primeSize);
    }
    return prime;
  }
}
