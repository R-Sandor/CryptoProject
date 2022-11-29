package dev.findfirst.CryptoProjectFinal.crypto;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import dev.findfirst.CryptoProjectFinal.crypto.rsa.RsaKeyGenerator.RsaKeys;
import lombok.extern.slf4j.Slf4j;

/**
 * Fermat's factorization method 
 * Algorithm borrowed from Chris Christensen 
 * @see https://www.nku.edu/~christensen/Mathematical%20attack%20on%20RSA.pdf
 */
@Component
@Slf4j
public class FermatsFactorization  {

    public long solveTime(RsaKeys keys) {
        long start = System.currentTimeMillis();
        String x = solveFermatFactorization(keys.n());
        log.debug("Key Found: {}", x);
        return System.currentTimeMillis() - start;
    }

    private String solveFermatFactorization(BigInteger n) {
        // check if one of the factors is two 
        if(!n.testBit(0)) {
            return "[ " + n.divide(BigInteger.TWO) + ", " + 2 + " ]";
        }
             
        BigInteger [] arr = n.sqrtAndRemainder();
        if (arr[1].compareTo(BigInteger.ZERO) != 0 ) { 
            arr[0] = arr[0].add(BigInteger.ONE);
        }
        BigInteger a = arr[0];

        // if n is a perfect root,
        // then both its square roots are its factors
        BigInteger aSqrd = a.multiply(a);
        if(aSqrd.compareTo(n) == 0) {
            return "[ " + a + ", " + a + " ]";
        }

        BigInteger b;
        while(true) {
            BigInteger b1 = a.multiply(a).subtract(n);
            b = b1.sqrt();
             
            if(b.multiply(b).compareTo(b1) == 0)
                break;
            else
                a = a.add(BigInteger.ONE);
        }
        return " [ " + a.subtract(b) + ", " + a.add(b) + " ]";
    }
    
}
