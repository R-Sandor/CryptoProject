package dev.findfirst.CryptoProjectFinal.controller;

import dev.findfirst.CryptoProjectFinal.service.BabyStepGiaintStep;
import dev.findfirst.CryptoProjectFinal.utility.KeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alg")
@Slf4j
public class AlgorithmController {

  @Autowired BabyStepGiaintStep babyStep;
  @Autowired KeyGenerator keyGen;

  @RequestMapping(
      value = "/babyStep/{alpha}/{keysize}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public long getBabyStepPerformance(@PathVariable int alpha, @PathVariable int keysize) {
    return babyStep.solveTimer(keyGen.generateKeys(alpha, keysize));
    // return babyStep.solveTimer(new KeysRec(39327, 62927, 5, 65521));
  }
}
