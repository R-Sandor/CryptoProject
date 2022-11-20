package dev.findfirst.CryptoProjectFinal.crypto;

import java.util.ArrayList;
import java.util.List;

public class EEA {

  private List<Integer> si = new ArrayList<>();
  private List<Integer> ti = new ArrayList<>();
  private List<Integer> qi1 = new ArrayList<>(List.of(0, 0));
  private List<Integer> ri = new ArrayList<>();
  private int aInv = 0;

  public static int solveEEA(int r0, int r1) {
    EEA eea = new EEA();
    eea.si.add(1);
    eea.si.add(0);
    eea.ti.add(0);
    eea.ti.add(1);
    if (r1 > r0) {
      r0 += r1;
      r1 = r0 - r1;
      r0 -= r1; // swap
    }
    eea.ri.add(r0);
    eea.ri.add(r1);
    eea.aInv = eea.gcdEEA(r0, r1);
    if (eea.aInv < 0) {
      eea.aInv = r0 + eea.aInv;
    }
    return eea.aInv;
  }

  public int gcdEEA(int r0, int r1) {
    int ri = r0 % r1;
    int qi_1 = (r0 - ri) / r1;

    this.ri.add(ri);
    qi1.add(qi_1);
    si.add(si.get(si.size() - 2) - qi_1 * si.get(si.size() - 1));
    ti.add(ti.get(ti.size() - 2) - qi_1 * ti.get(ti.size() - 1));
    if (ri == 0) {
      return ti.get(ti.size() - 2);
    }
    return gcdEEA(r1, ri);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("======================================\n");
    sb.append("| i |   r_i  | q_i-1|    si  |   ti  |\n");
    sb.append("--------------------------------------\n");
    for (int i = 0; i < ri.size(); i++) {
      sb.append("| " + i + " |  ")
          .append(lPad("" + ri.get(i), 4))
          .append("  | " + lPad("" + qi1.get(i), 4) + " |  ")
          .append(lPad("" + si.get(i), 4))
          .append("  | " + lPad("" + ti.get(i), 4) + "  | \n");
    }
    sb.append("======================================\n");
    return sb.toString();
  }
  // borrowed from https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java
  public String lPad(String s, int n) {
    return String.format("%" + n + "s", s);
  }
}
