package dev.findfirst.CryptoProjectFinal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.findfirst.CryptoProjectFinal.utility.KeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class CryptoProjectFinalApplicationTests {

  @Autowired KeyGenerator keyGen;

  @Test
  void numInRangeOfBits() {
    int keySize = 4;
    assertTrue(
        () -> {
          long max = 2 << keySize - 1;
          long min = (2 << keySize - 2) + 1;
          long genKey = keyGen.genRandomInBitSize(keySize);
          return min <= genKey && genKey <= max;
        });
  }

  @Test
  void primeTest() {
    assertFalse(keyGen.primeTest(15, 4));
    assertTrue(keyGen.primeTest(11, 4));
  }

  @Test
  void findPrimeForBitSize() {
    int priKey = keyGen.genRandomInBitSize(16);
    while (!keyGen.primeTest(priKey, 4)) {
      priKey = keyGen.genRandomInBitSize(16);
    }
    assertTrue(keyGen.primeTest(priKey, 4));
  }

  @Test
  void calcPublicKey() {
    int priKey = 62927;
    int p = 65521;
    int alpha = 5;

    assertEquals(39327, keyGen.fastMod(alpha, priKey, p));
  }
}
