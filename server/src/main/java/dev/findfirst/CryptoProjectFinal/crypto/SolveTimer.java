package dev.findfirst.CryptoProjectFinal.crypto;

import dev.findfirst.CryptoProjectFinal.crypto.KeyGenerator.BigKeys;

public interface SolveTimer {

  /**
   * Every Type of attack needs to implment a solve time.
   *
   * @param keys encrpytion parameters
   * @return solve time.
   */
  public long solveTime(BigKeys keys);
}
