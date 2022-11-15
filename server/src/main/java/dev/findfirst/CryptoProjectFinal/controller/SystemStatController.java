package dev.findfirst.CryptoProjectFinal.controller;

import dev.findfirst.CryptoProjectFinal.controller.model.SystemRecord;
import dev.findfirst.CryptoProjectFinal.service.SystemStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sys")
public class SystemStatController {

  @Autowired SystemStatsService systemStatsService;

  @RequestMapping("stats")
  public SystemRecord getStats() {
    return systemStatsService.getStats();
  }
}
