package dev.findfirst.CryptoProjectFinal.controller;

import dev.findfirst.CryptoProjectFinal.crypto.FermatsFactorization;
import dev.findfirst.CryptoProjectFinal.crypto.PollardRho;
import dev.findfirst.CryptoProjectFinal.crypto.rsa.RsaBruteForce;
import dev.findfirst.CryptoProjectFinal.crypto.rsa.RsaKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rsa/")
public class RsaController {

  @Autowired RsaBruteForce bruteForce;
  @Autowired PollardRho pollardRho;
  @Autowired FermatsFactorization fermats;
  @Autowired RsaKeyGenerator keyGen;

  @RequestMapping(
      value = "/bf/{keysize}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public long getBrutePerformacne(@PathVariable int keysize) {
    // if the bit size is greater than 31 bits then BigIntegers need to be used.
    return bruteForce.solveTime(keyGen.generateKeys(keysize));
  }

  @RequestMapping(
      value = "/pr/{keysize}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public long getPollarRhoPerformacne(@PathVariable int keysize) {
    // if the bit size is greater than 31 bits then BigIntegers need to be used.
    return pollardRho.solveTime(keyGen.generateKeys(keysize));
  }

  @RequestMapping(
      value = "/fermats/{keysize}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public long getFermatsPerformacne(@PathVariable int keysize) {
    // if the bit size is greater than 31 bits then BigIntegers need to be used.
    return fermats.solveTime(keyGen.generateKeys(keysize));
  }
}
