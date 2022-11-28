package dev.findfirst.CryptoProjectFinal.crypto;

import dev.findfirst.CryptoProjectFinal.crypto.diffiehellman.DHKeyGenerator.DHKeys;
import dev.findfirst.CryptoProjectFinal.crypto.rsa.RsaKeyGenerator.RsaKeys;

import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Pollard's Rho Method. Has the space advantage over Baby Step Gaint Step, but depending on the
 * prime it can be more performant i.e. in the case of a smaller prime factors.
 *
 * <p>Borrowed algorithm from Wikipedia adapted from C++ to Java.
 *
 * @see https://en.wikipedia.org/wiki/Pollard%27s_rho_algorithm
 */
@Component
@Slf4j
public class PollardRho implements SolveTimer {

  record Axb(
      BigInteger x,
      BigInteger a,
      BigInteger b,
      BigInteger n,
      BigInteger N,
      BigInteger alpha,
      BigInteger beta) {
    public Axb {
      int xtmp = x.mod(BigInteger.valueOf(3)).intValue();
      switch (xtmp) {
        case 0 -> {
          x = x.multiply(x).mod(N);
          a = a.multiply(BigInteger.TWO).mod(n);
          b = b.multiply(BigInteger.TWO).mod(n);
        }
        case 1 -> {
          x = x.multiply(alpha).mod(N);
          a = a.add(BigInteger.ONE).mod(n);
        }
        case 2 -> {
          x = x.multiply(beta).mod(N);
          b = b.add(BigInteger.ONE).mod(n);
        }
      }
    }
  }

  public String solveDHPollardRho(DHKeys keys) {
    log.debug("a {}, b {}, p {}, privKey {}", keys.a(), keys.kpub(), keys.p(), keys.kpriv());
    BigInteger alpha = keys.a();
    BigInteger beta = keys.kpub();
    BigInteger N = keys.p();
    BigInteger n = N.subtract(BigInteger.ONE);
    BigInteger x = BigInteger.ONE, a = BigInteger.ZERO, b = BigInteger.ZERO;
    BigInteger X = x, A = a, B = b;
    for (BigInteger i = BigInteger.ONE; i.compareTo(n) < 0; i = i.add(BigInteger.ONE)) {
      Axb axb = new Axb(x, a, b, n, N, alpha, beta);
      x = axb.x;
      a = axb.a;
      b = axb.b;
      axb = new Axb(X, A, B, n, N, alpha, beta);
      X = axb.x;
      A = axb.a;
      B = axb.b;
      axb = new Axb(X, A, B, n, N, alpha, beta);
      X = axb.x;
      A = axb.a;
      B = axb.b;
      log.debug("i {}, x {}, a {}, b {}, X {}, A {}, B {}", i, x, b, X, A, B);
      if (x.equals(X)) {
        return x.toString();
      }
    }
    return "-1";
  }

  @Override
  public long solveTime(DHKeys keys) {
    long start = System.currentTimeMillis();
    String x = solveDHPollardRho(keys);
    log.debug("Key Found: {}", x);
    return System.currentTimeMillis() - start;
  }

  /* TODO: Simplify the two keys to use a generic key for
   * for the solveTimes were applicable. 
  */
  /**
   * Same as SolveTimers method except with an RSA Key.
   * @param keys the private/public key record
   * @return solve time
   */
  public long solveTime(RsaKeys keys) {
   long start = System.currentTimeMillis();
    String x = solveRsaPollardRho(keys);
    log.debug("Key Found: {}", x);
    return System.currentTimeMillis() - start;
}

/**
 * Same as solveDHPollardRho 
 * @param keys private/public RSA key
 * @return found key value.
 */
private String solveRsaPollardRho(RsaKeys keys) {
    log.debug(" b {}, p {}, privKey {}",  keys.e(), keys.p(), keys.d());
    BigInteger alpha = BigInteger.valueOf(2);
    BigInteger beta = keys.e();
    BigInteger N = keys.p();
    BigInteger n = N.subtract(BigInteger.ONE);
    BigInteger x = BigInteger.ONE, a = BigInteger.ZERO, b = BigInteger.ZERO;
    BigInteger X = x, A = a, B = b;
    for (BigInteger i = BigInteger.ONE; i.compareTo(n) < 0; i = i.add(BigInteger.ONE)) {
      Axb axb = new Axb(x, a, b, n, N, alpha, beta);
      x = axb.x;
      a = axb.a;
      b = axb.b;
      axb = new Axb(X, A, B, n, N, alpha, beta);
      X = axb.x;
      A = axb.a;
      B = axb.b;
      axb = new Axb(X, A, B, n, N, alpha, beta);
      X = axb.x;
      A = axb.a;
      B = axb.b;
      log.debug("i {}, x {}, a {}, b {}, X {}, A {}, B {}", i, x, b, X, A, B);
      if (x.equals(X)) {
        return x.toString();
      }
    }
    return "-1";
}
}
