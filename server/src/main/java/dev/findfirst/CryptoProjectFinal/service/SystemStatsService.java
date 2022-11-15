package dev.findfirst.CryptoProjectFinal.service;

import dev.findfirst.CryptoProjectFinal.controller.model.SystemRecord;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

@Service
@Component
public class SystemStatsService {

  private SystemInfo si = new SystemInfo();
  HardwareAbstractionLayer hal = si.getHardware();
  CentralProcessor cpu = hal.getProcessor();

  public SystemRecord getStats() {
    return new SystemRecord(
        cpu.getMaxFreq(), cpu.getLogicalProcessorCount(), hal.getMemory().getTotal());
  }
}
