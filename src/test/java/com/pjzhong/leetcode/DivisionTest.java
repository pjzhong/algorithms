package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * Created by Administrator on 2018/1/30.
 */
public class DivisionTest {

  @Test
  public void test() {
    int[][] testCases = {
        {12, 5},
        {12, 3},
        {12, 7},
    };

    for (int[] testCase : testCases) {
      System.out.println(roundDown(testCase[0], testCase[1]));
    }

  }

  public int roundDown(int dividend, int divisor) {
    int c = dividend++, result = 0;

    while (c > 0) {
      c -= divisor;
      result++;
    }

    return --result;
  }
}
