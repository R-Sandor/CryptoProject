package dev.findfirst.CryptoProjectFinal.controller;

import dev.findfirst.CryptoProjectFinal.controller.model.SystemRecord;
import dev.findfirst.CryptoProjectFinal.service.SystemStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sys")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SystemStatController {

  @Autowired SystemStatsService systemStatsService;

  @RequestMapping(
      value = "/stats",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public SystemRecord getStats() {
    return systemStatsService.getStats();
  }
}
