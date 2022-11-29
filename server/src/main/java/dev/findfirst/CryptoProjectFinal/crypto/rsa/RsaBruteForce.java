package dev.findfirst.CryptoProjectFinal.crypto.rsa;

import dev.findfirst.CryptoProjectFinal.crypto.rsa.RsaKeyGenerator.RsaKeys;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RsaBruteForce {

  public String solveRsaBruteForce(BigInteger n) {
    return "";
  }

  public long solveTime(RsaKeys keys) {
    long start = System.currentTimeMillis();
    String x = solveRsaBruteForce(keys.n());
    log.debug("Key Found: {}", x);
    return System.currentTimeMillis() - start;
  }
}
