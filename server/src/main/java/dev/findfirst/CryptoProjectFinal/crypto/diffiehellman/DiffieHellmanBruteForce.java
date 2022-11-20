package dev.findfirst.CryptoProjectFinal.crypto.diffiehellman;

import dev.findfirst.CryptoProjectFinal.crypto.KeyGenerator.BigKeys;
import dev.findfirst.CryptoProjectFinal.crypto.SolveTimer;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    BigInteger maxValue = keys.a().pow(keys.bitsize());
    for (BigInteger i = BigInteger.ZERO; i.compareTo(maxValue) < 0; i = i.add(BigInteger.ONE)) {
      BigInteger canidateKey = keys.a().modPow(i, keys.p());
      if (canidateKey.compareTo(keys.kpub()) == 0) {
        return canidateKey.toString();
      }
    }

    return "-1";
  }
}
