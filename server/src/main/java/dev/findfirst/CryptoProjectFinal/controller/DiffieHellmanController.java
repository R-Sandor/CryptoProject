package dev.findfirst.CryptoProjectFinal.controller;

import dev.findfirst.CryptoProjectFinal.crypto.KeyGenerator;
import dev.findfirst.CryptoProjectFinal.crypto.PollardRho;
import dev.findfirst.CryptoProjectFinal.crypto.diffiehellman.BabyStepGiantStep;
import dev.findfirst.CryptoProjectFinal.crypto.diffiehellman.DiffieHellmanBruteForce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dh")
@Slf4j
public class DiffieHellmanController {

  @Autowired KeyGenerator keyGen;
  @Autowired BabyStepGiantStep babyStep;
  @Autowired DiffieHellmanBruteForce bruteForce;
  @Autowired PollardRho pollardRho;

  @RequestMapping(
      value = "/babyStep/{alpha}/{keysize}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public long getBabyStepPerformance(@PathVariable long alpha, @PathVariable int keysize) {
    // if the bit size is greater than 31 bits then BigIntegers need to be used.
    if (keysize > 30) {
      return babyStep.solveTime(keyGen.generateBigKeys(alpha, keysize));
    }
    return babyStep.solveTime(keyGen.generateKeys(alpha, keysize));
  }

  @RequestMapping(
      value = "/bf/{alpha}/{keysize}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public long getBruteForcePerformance(@PathVariable long alpha, @PathVariable int keysize) {
    // if the bit size is greater than 31 bits then BigIntegers need to be used.
    return bruteForce.solveTime(keyGen.generateBigKeys(alpha, keysize));
  }

  @RequestMapping(
      value = "/pr/{alpha}/{keysize}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public long getPollarRhoPerformacne(@PathVariable long alpha, @PathVariable int keysize) {
    // if the bit size is greater than 31 bits then BigIntegers need to be used.
    return pollardRho.solveTime(keyGen.generateBigKeys(alpha, keysize));
  }
}
