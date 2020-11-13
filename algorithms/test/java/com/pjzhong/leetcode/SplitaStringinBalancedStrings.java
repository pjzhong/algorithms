package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * alanced strings are those who have equal quantity of 'L' and 'R' characters.
 *
 * Given a balanced string s split it in the maximum amount of balanced strings.
 *
 * Return the maximum amount of splitted balanced strings.
 *
 *
 *
 * Example 1:
 *
 * <p>Input: s = "RLRRLLRLRL" </p>
 * <p>Output: 4</p>
 * Explanation: s can be split into "RL", "RRLL", "RL", "RL", each substring contains same number of
 * 'L' and 'R'.
 *
 * @link https://leetcode.com/problems/split-a-string-in-balanced-strings/
 **/
public class SplitaStringinBalancedStrings {

  @Test
  public void test() {
    String[] tests = {"RLRRLLRLRL", "RLLLLRRRLR", "LLLLRRRR"};

    for (String t : tests) {
      System.out.println(balancedStringSplit(t));
    }
  }


  public int balancedStringSplit(String s) {
    int count = 0;
    int res = 0;
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == 'L') {
        ++count;
      } else {
        --count;
      }

      if (count == 0) {
        res++;
      }
    }
    return res;
  }
}
