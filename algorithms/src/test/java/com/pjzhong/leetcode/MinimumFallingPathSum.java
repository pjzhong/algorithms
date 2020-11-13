package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * Given a square array of integers A, we want the minimum sum of a falling path through A.
 *
 * A falling path starts at any element in the first row, and chooses one element from each row. The
 * next row's choice must be in a column that is different from the previous row's column by at most
 * one.
 *
 *
 *
 * Example 1:
 *
 * <p>Input: [[1,2,3],[4,5,6],[7,8,9]]</p>
 * <p>Output: 12</p>
 * Explanation: The possible falling paths are: [1,4,7], [1,4,8], [1,5,7], [1,5,8], [1,5,9] [2,4,7],
 * [2,4,8], [2,5,7], [2,5,8], [2,5,9], [2,6,8], [2,6,9] [3,5,7], [3,5,8], [3,5,9], [3,6,8], [3,6,9]
 * The falling path with the smallest sum is [1,4,7], so the answer is 12.
 *
 * @link https://leetcode.com/problems/minimum-falling-path-sum/
 * @since 2020年03月31日 20:18:48
 **/
public class MinimumFallingPathSum {

  @Test
  public void test() {
    int[][][] tests = {
        {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
        {{-51, -35, 74}, {-62, 14, -53}, {94, 61, -10}}
    };

    for (int[][] test : tests) {
      System.out.println(minFallingPathSum(test));
    }
  }

  public int minFallingPathSum(int[][] A) {
    final int length = A.length - 1;
    for (int r = 1; r <= length; r++) {
      int[] prev = A[r - 1];
      int[] now = A[r];
      for (int c = 0; c <= length; c++) {
        int cur = now[c];
        int left = cur + prev[Math.max(0, c - 1)];
        int mid = cur + prev[c];
        int right = cur + prev[Math.min(length, c + 1)];
        now[c] = Math.min(left, Math.min(mid, right));
      }
    }

    int min = Integer.MAX_VALUE;
    for (int i : A[length]) {
      if (i < min) {
        min = i;
      }
    }
    return min;
  }

}
