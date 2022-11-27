package dev.findfirst.CryptoProjectFinal.crypto;

import dev.findfirst.CryptoProjectFinal.crypto.diffiehellman.DHKeyGenerator.DHKeys;

public interface SolveTimer {

  /**
   * Every Type of attack needs to implment a solve time.
   *
   * @param keys encrpytion parameters
   * @return solve time.
   */
  public long solveTime(DHKeys keys);
}
