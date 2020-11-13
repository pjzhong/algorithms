package com.pjzhong.dsa.sort;

public class LinearCombinations {

  /**
   * Let g, h Be relatively prime x(g, h) = (g - 1) * (h - 1) -1 = gh - g - h = gh - (g + h)
   */
  public int maxNotCombinations(int g, int h) {
    return (g * h) - (g + h);
  }
}
