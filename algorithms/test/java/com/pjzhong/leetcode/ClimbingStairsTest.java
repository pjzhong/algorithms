package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * You are climbing a stair case. It takes n steps to reach to the top.
 *
 * Each com.pjzhong.time you can either climb 1 or 2 steps. In how many distinct ways can you climb
 * to the top?
 *
 * Note: Given n will be a positive integer.
 *
 *
 * Example 1:
 *
 * Input: 2 Output:  2 Explanation:  There are two ways to climb to the top.
 *
 * 1. 1 step + 1 step 2. 2 steps Example 2:
 *
 * Input: 3 Output:  3 Explanation:  There are three ways to climb to the top.
 *
 * 1. 1 step + 1 step + 1 step 2. 1 step + 2 steps 3. 2 steps + 1 step
 *
 * https://leetcode.com/problems/climbing-stairs/description/
 */
public class ClimbingStairsTest {

  @Test
  public void test() {
    int[] testCases = {2, 3};

    for (int i : testCases) {
      System.out.println(climbingStairsTest(i));
    }
  }

  public int climbingStairsTest(int n) {
    int first = 0, second = 1;

    for (int i = 1; i <= n; ++i) {
      second = first + second;
      first = second - first;
    }
    return second;
  }
}
