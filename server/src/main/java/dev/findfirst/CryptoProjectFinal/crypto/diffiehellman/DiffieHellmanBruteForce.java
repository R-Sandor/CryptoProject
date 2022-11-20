package dev.findfirst.CryptoProjectFinal.crypto.diffiehellman;

import dev.findfirst.CryptoProjectFinal.crypto.KeyGenerator.BigKeys;
import dev.findfirst.CryptoProjectFinal.crypto.SolveTimer;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Class to stimulate a naive brute force attempt. This is more or less a baseline of gauging the
 * performance of various methods.
 */
@Component
@Slf4j
public class DiffieHellmanBruteForce implements SolveTimer {

  @Override
  public long solveTime(BigKeys keys) {
    long start = System.currentTimeMillis();
    String x = bruteForce(keys);
    log.debug("Key Found: {}", x);
    return System.currentTimeMillis() - start;
  }

  public String bruteForce(BigKeys keys) {
    log.debug("a {}, b {}, p {}, privKey {}", keys.a(), keys.kpub(), keys.p(), keys.kpriv());
    BigInteger maxValue = keys.a().pow(keys.bitsize());
    log.debug("maxValue {}", maxValue);
    for (BigInteger i = BigInteger.ZERO; i.compareTo(maxValue) < 0; i = i.add(BigInteger.ONE)) {
      BigInteger canidateKey = keys.a().modPow(i, keys.p());
      log.debug("i: {}: candidateKey {}", i, canidateKey);
      if (canidateKey.compareTo(keys.kpub()) == 0) {
        return i.toString();
      }
    }
    return "-1";
  }
}
