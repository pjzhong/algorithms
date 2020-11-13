package com.pjzhong.leetcode.contest93;

import org.junit.Test;

public class BinaryGap {

  @Test
  public void test() {
    int[] testCases = {22, 5, 6, 8, 138, 13};

    for (int i : testCases) {
      System.out.println(binaryGap(i));
    }
  }

  public int binaryGap(int N) {
    char[] binary = Integer.toString(N, 2).toCharArray();

    int result = 0, pre = -1;
    for (int i = binary.length - 1; 0 <= i; i--) {
      if (binary[i] == '1') {
        if (pre == -1) {
          pre = i;
        } else {
          result = Math.max(result, i + 1 == pre ? 1 : pre - i);
          pre = i;
        }
      }
    }

    return result;
  }
}
