package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 1/7/2018.
 */
public class BitOnes {

  @Test
  public void test() {
    int ones = 0, n = 15;
    System.out.println(Integer.bitCount(n));
    for (int i = 32, num = n; i > 0; i--) {
      if ((num & 1) == 1) {
        ones++;
      }
      num = num >>> 1;
    }
    System.out.println(ones);
    return;
  }

  @Test
  public void test1() {
    Map<Integer, Double> ones = new HashMap<>();

    for(int i = 0; i < 10000; i++) {
      ones.put(i, 1d);
    }

    System.out.println();
  }
}
