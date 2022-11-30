package dev.findfirst.CryptoProjectFinal.crypto.rsa;

import dev.findfirst.CryptoProjectFinal.crypto.rsa.RsaKeyGenerator.RsaKeys;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RsaBruteForce {

  public String solveRsaBruteForce(BigInteger n) {
    Set<BigInteger> primes = new HashSet<>();
    for (BigInteger i = BigInteger.ZERO; i.compareTo(n) < 0; i = i.add(BigInteger.ONE)) {
      if (primes.contains(i) || i.isProbablePrime(2)) {
        primes.add(i);
        for (BigInteger x = BigInteger.ZERO; x.compareTo(n) < 0; x = x.add(BigInteger.ONE)) {
          if (primes.contains(x) || x.isProbablePrime(2)) {
            primes.add(x);
            if (i.multiply(x).compareTo(n) == 0) {
              return "[ " + x + ", " + i + " ]";
            }
          }
        }
      } else {
        continue;
      }
    }
    return "";
  }

  public long solveTime(RsaKeys keys) {
    long start = System.currentTimeMillis();
    String x = solveRsaBruteForce(keys.n());
    log.debug("Key Found: {}", x);
    return System.currentTimeMillis() - start;
  }
}
