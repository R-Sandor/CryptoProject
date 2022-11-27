package dev.findfirst.CryptoProjectFinal.controller;

import dev.findfirst.CryptoProjectFinal.crypto.diffiehellman.DHKeyGenerator;
import dev.findfirst.CryptoProjectFinal.crypto.diffiehellman.DHKeyGenerator.HexKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/keygen")
@Slf4j
public class KeyGenController {

  @Autowired DHKeyGenerator keyGen;

  @RequestMapping(
      value = "create/{alpha}/{keysize}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public HexKeys getBabyStepPerformance(@PathVariable long alpha, @PathVariable int keysize) {
    return keyGen.generateBigKeys(alpha, keysize).getHexKeys();
  }
}
