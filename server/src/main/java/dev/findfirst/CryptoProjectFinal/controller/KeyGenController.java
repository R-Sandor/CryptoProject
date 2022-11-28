package dev.findfirst.CryptoProjectFinal.controller;

import dev.findfirst.CryptoProjectFinal.crypto.diffiehellman.DHKeyGenerator;
import dev.findfirst.CryptoProjectFinal.crypto.diffiehellman.DHKeyGenerator.HexKeys;
import dev.findfirst.CryptoProjectFinal.crypto.rsa.RsaKeyGenerator;
import dev.findfirst.CryptoProjectFinal.crypto.rsa.RsaKeyGenerator.RsaHexKeys;
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

  @Autowired DHKeyGenerator dhKeyGen;
  @Autowired RsaKeyGenerator rsaKeyGen;

  @RequestMapping(
      value = "dh/{alpha}/{keysize}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public HexKeys getDhKeys(@PathVariable long alpha, @PathVariable int keysize) {
    return dhKeyGen.generateBigKeys(alpha, keysize).getHexKeys();
  }

  /**
   * Gets a RSA key of a given size.
   *
   * @param keysize the desired keysize
   * @return the keys
   */
  @RequestMapping(
      value = "rsa/{keysize}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public RsaHexKeys getRsaKeys(@PathVariable int keysize) {
    return rsaKeyGen.generateKeys(keysize).getHexKeys();
  }
}
