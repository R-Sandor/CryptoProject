package dev.findfirst.CryptoProjectFinal.controller;

import dev.findfirst.CryptoProjectFinal.crypto.KeyGenerator;
import dev.findfirst.CryptoProjectFinal.crypto.diffiehellman.BabyStepGiaintStep;
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
public class AlgorithmController {

  @Autowired BabyStepGiaintStep babyStep;
  @Autowired KeyGenerator keyGen;

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
    // return babyStep.solveTimer(new KeysRec(39327, 62927, 5, 65521));
  }

@RequestMapping(
      value = "/bruteforce/{alpha}/{keysize}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public long getBruteForcePerformance(@PathVariable long alpha, @PathVariable int keysize) {
    // if the bit size is greater than 31 bits then BigIntegers need to be used.
    return babyStep.solveTime(keyGen.generateKeys(alpha, keysize));
    // return babyStep.solveTimer(new KeysRec(39327, 62927, 5, 65521));
  }

}
