package dev.findfirst.CryptoProjectFinal.crypto.diffiehellman;

import static dev.findfirst.CryptoProjectFinal.crypto.CryptoUtils.fastMod;
import static dev.findfirst.CryptoProjectFinal.crypto.CryptoUtils.genDprime;
import static dev.findfirst.CryptoProjectFinal.crypto.CryptoUtils.generatePrime;

import java.math.BigInteger;
import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DHKeyGenerator {

  /**
   * Generate keys kpub, kpriv, and p parameter
   *
   * @param keySize key size in bits
   */
  public DHKeysRec generateKeys(long alpha, int keySize) {
    long p = generatePrime(keySize);
    long kpriv = genDprime(keySize, p);
    long kpub = fastMod(alpha, kpriv, p);
    return new DHKeysRec(kpub, kpriv, alpha, p);
  }

  public DHKeys generateBigKeys(long alpha, int keySize) {
    SecureRandom rnd = new SecureRandom();
    BigInteger tmp;
    BigInteger a = BigInteger.valueOf(alpha);
    log.debug("a {}", a);
    BigInteger p = BigInteger.probablePrime(keySize, rnd);
    BigInteger kpriv = BigInteger.probablePrime(keySize, rnd);

    int comp = kpriv.compareTo(p.subtract(BigInteger.TWO));
    // If kpriv and p are equal then subtract 2 from kpriv.
    if (comp == 0) {
      kpriv = kpriv.subtract(BigInteger.TWO);
    }
    // if kpriv is greater than p
    // swap them, take the space to time trade off and use a tmp.
    else if (comp > 0) {
      tmp = kpriv;
      kpriv = p;
      p = tmp;
    }
    BigInteger kpub = a.modPow(kpriv, p);
    log.debug("kpub {}", kpub);

    return new DHKeys(kpub, kpriv, a, p, keySize);
  }

  public record DHKeysRec(long kpub, long kpriv, long a, long p) {}

  public record DHKeys(BigInteger kpub, BigInteger kpriv, BigInteger a, BigInteger p, int bitsize) {
    public HexKeys getHexKeys() {
      return new HexKeys(
          kpub.toString(16), kpriv.toString(16), a.toString(16), p.toString(16), bitsize);
    }
  }

  public record HexKeys(String kpub, String kpriv, String a, String p, int bitsize) {}
}
