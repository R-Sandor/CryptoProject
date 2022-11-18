package dev.findfirst.CryptoProjectFinal.service;

import dev.findfirst.CryptoProjectFinal.utility.KeyGenerator.KeysRec;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** Baby Step Gaint step is an attack used against Diffie-Hellman/Algamal Crypto Systems. */
@Component
@Slf4j
public class BabyStepGiaintStep {

  private long fastMod(long c, int y, int p) {
    long retVal = 1; // Initialize result
    c = c % p; // Update x if it is more than or equal to p
    while (y > 0) {
      // If y is odd, multiply x with result
      if ((y & 1) > 0) retVal = (retVal * c) % p;

      // y must be even now
      y = y >> 1; // y = y/2
      c = (c * c) % p;
    }
    return retVal;
  }

  private long discreteLogarithm(int a, long l, int p, int privKey) {
    int m = (int) (Math.sqrt(p - 1));

    Map<Long, Long> lookup = new HashMap<>();
    // Store all values of a^(m*i) of LHS --
    // I really think of this as the right hand side of the equation: alpha^
    long cur = 1;
    for (long i = 0; i < m; i++) {
      lookup.put(cur, i);
      cur = (cur * a) % p;
    }

    // find a^-m
    // fermat's little theorem to find the inverse!
    long c = fastMod(a, m * (p - 2), p);

    for (int i = 0; i < m; i++) {
      // Calculate (a^-m)xg * b and check
      // for collision
      long y = (l * (fastMod(c, i, p))) % p;
      if (lookup.getOrDefault(y, 0l) > 0) {
        long val = i * m + lookup.get(y);
        log.debug("val {}", val);
        /*
         * This is a simism. As there possible collisions.
         * For example 5^13787 and 5^62927 mod 65221 both provide
         * the public key of 39327.
         *
         * In reality one would need to check if
         * the private key when raised to the power of a given public
         * key creates the session key.
         */
        if (val == privKey) return val;
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
  public long solveTimer(int a, int b, int p, int privKey) {
    long start = System.currentTimeMillis();
    long x = discreteLogarithm(a, b, p, privKey);
    return System.currentTimeMillis() - start;
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
    log.debug("x {}", x);
    return System.currentTimeMillis() - start;
  }
}
