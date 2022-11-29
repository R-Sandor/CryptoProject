package dev.findfirst.CryptoProjectFinal;

import static dev.findfirst.CryptoProjectFinal.crypto.CryptoUtils.fastMod;
import static dev.findfirst.CryptoProjectFinal.crypto.CryptoUtils.genRandomInBitSize;
import static dev.findfirst.CryptoProjectFinal.crypto.CryptoUtils.primeTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.findfirst.CryptoProjectFinal.crypto.diffiehellman.DHKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class CryptoProjectFinalApplicationTests {

  @Autowired DHKeyGenerator keyGen;

  @Test
  void numInRangeOfBits() {
    int keySize = 4;
    assertTrue(
        () -> {
          long max = 2 << keySize - 1;
          long min = (2 << keySize - 2) + 1;
          long genKey = genRandomInBitSize(keySize);
          return min <= genKey && genKey <= max;
        });
  }

  @Test
  void TestPrime() {
    assertFalse(primeTest(15, 4));
    assertTrue(primeTest(11, 4));
  }

  @Test
  void findPrimeForBitSize() {
    long priKey = genRandomInBitSize(16);
    while (!primeTest(priKey, 4)) {
      priKey = genRandomInBitSize(16);
    }
    assertTrue(primeTest(priKey, 4));
  }

  @Test
  void calcPublicKey() {
    int priKey = 62927;
    int p = 65521;
    int alpha = 5;

    assertEquals(39327, fastMod(alpha, priKey, p));
  }
}
