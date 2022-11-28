package dev.findfirst.CryptoProjectFinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.findfirst.CryptoProjectFinal.crypto.PollardRho;
import dev.findfirst.CryptoProjectFinal.crypto.rsa.RsaKeyGenerator;

@RestController
@RequestMapping("api/rsa/")
public class RsaController {
    
  @Autowired PollardRho pollardRho;
  @Autowired RsaKeyGenerator keyGen;

  @RequestMapping(
      value = "/pr/{keysize}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public long getPollarRhoPerformacne(@PathVariable int keysize) {
    // if the bit size is greater than 31 bits then BigIntegers need to be used.
    return pollardRho.solveTime(keyGen.generateKeys(keysize));
  }

}
