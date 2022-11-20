package dev.findfirst.CryptoProjectFinal.crypto.diffiehellman;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import dev.findfirst.CryptoProjectFinal.crypto.KeyGenerator.BigKeys;
import lombok.extern.slf4j.Slf4j;
import dev.findfirst.CryptoProjectFinal.crypto.SolveTimer;

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
