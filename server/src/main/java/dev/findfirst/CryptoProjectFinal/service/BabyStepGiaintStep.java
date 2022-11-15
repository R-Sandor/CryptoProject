package dev.findfirst.CryptoProjectFinal.service;

public class BabyStepGiaintStep {
  private String test;

  static int fastMod(int x, int y, int p) {
    int retVal = 1; // Initialize result
    x = x % p; // Update x if it is more than or
    // equal to p

    while (y > 0) {
      // If y is odd, multiply x with result
      if ((y & 1) > 0) retVal = (retVal * x) % p;

      // y must be even now
      y = y >> 1; // y = y/2
      x = (x * x) % p;
    }
    return retVal;
  }

  static int discreteLogarithm(int a, int b, int p) {
    int m = (int) (Math.sqrt(p) + 1);

    // Calculate a ^ n
    int[] t = new int[p];
    int an = 1;
    // Store all values of a^(n*i) of LHS
    for (int i = 0, cur = an; i < m; i++) {
      t[cur] = i;
      cur = (cur * a) % p;
    }

    // find a^-m
    System.out.println(m * (p - 2));
    int c = fastMod(a, m * (p - 2), p);
    System.out.println(c);

    for (int i = 0; i < m; i++) {
      // Calculate (a ^ -m)xg * b and check
      // for collision
      int y = (b * (fastMod(c, i, p))) % p;
      System.out.println("y " + y);
      if (t[y] > 0) {
        return i * m + t[y];
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    int a = 2, b = 4, m = 11;
    System.out.println(
        discreteLogarithm(
            Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])));
  }
}
