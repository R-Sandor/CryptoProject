package dev.findfirst.CryptoProjectFinal.crypto.rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RsaKeyGenerator {

  public RsaKeys generateKeys(int keySize) {
    BigInteger p, q, n, e, d, phiN;
    SecureRandom rnd = new SecureRandom();
    p = BigInteger.probablePrime(keySize / 2, rnd);
    q = BigInteger.probablePrime(keySize / 2, rnd);
    n = p.multiply(q);
    log.debug("p {}, q {}, n {}", p, q, n);
    phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    log.debug("q {}", q);
    edPair ed = genPrivPubExp(phiN, rnd);
    e = ed.e();
    d = ed.d();

    return new RsaKeys(p, q, n, e, d, keySize);
  }

  public edPair genPrivPubExp(BigInteger phiN, SecureRandom rnd) {
    BigInteger e = new BigInteger(phiN.bitLength(), rnd);
    BigInteger d;
    // RSA requires that e be an exponent from 1 to phi(n) - 1.
    while (e.compareTo(phiN.subtract(BigInteger.ONE)) > 0) {
      e = new BigInteger(phiN.bitLength(), rnd);
    }
    try {
      d = e.modInverse(phiN);
    }
    // if no inverse is found this expection will be thrown and
    // another pair will be tried.
    catch (ArithmeticException ae) {
      return genPrivPubExp(phiN, rnd);
    }
    return new edPair(e, d);
  }

  public record RsaKeys(
      BigInteger p, BigInteger q, BigInteger n, BigInteger e, BigInteger d, int bitsize) {
    public RsaHexKeys getHexKeys() {
      return new RsaHexKeys(
          p.toString(16), q.toString(16), n.toString(16), e.toString(16), d.toString(16), bitsize);
    }
  }

  public record RsaHexKeys(String p, String q, String n, String e, String d, int bitsize) {}

  public record edPair(BigInteger e, BigInteger d) {}
}
