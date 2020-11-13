package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 *
 * The robot can only move either down or right at any point in com.pjzhong.time. The robot is
 * trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 *
 * How many possible unique paths are there?
 *
 *
 * Above is a 7 x 3 grid. How many possible unique paths are there?
 *
 * Note: m and n will be at most 100.
 *
 * Example 1:
 *
 * Input: m = 3, n = 2 Output: 3 Explanation: From the top-left corner, there are a total of 3 ways
 * to reach the bottom-right corner: 1. Right -> Right -> Down 2. Right -> Down -> Right 3. Down ->
 * Right -> Right Example 2:
 *
 * Input: m = 7, n = 3 Output: 28
 *
 * https://leetcode.com/problems/unique-paths/description/
 */
public class UniquePaths {

  @Test
  public void test() {
    int[][] testCases = {
        {3, 2}, {7, 3}
    };

    for (int[] t : testCases) {
      System.out.println(uniquePaths(t[0], t[1]));
    }
  }

  public int uniquePaths(int m, int n) {
    int[] dp = new int[n];
    dp[0] = 1;

    for (int r = 0; r < m; r++) {
      for (int c = 1; c < n; c++) {
        dp[c] += dp[c - 1];
      }
    }

    return dp[n - 1];
  }
}
