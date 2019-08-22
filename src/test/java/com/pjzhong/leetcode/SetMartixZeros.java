package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.
 *
 * Example 1:
 *
 * Input: [ [1,1,1], [1,0,1], [1,1,1] ] Output: [ [1,0,1], [0,0,0], [1,0,1] ] Example 2:
 *
 * Input: [ [0,1,2,0], [3,4,5,2], [1,3,1,5] ] Output: [ [0,0,0,0], [0,4,5,0], [0,3,1,0] ] Follow
 * up:
 *
 * A straight forward solution using O(mn) space is probably a bad idea. A simple improvement uses
 * O(m + n) space, but still not the best solution. Could you devise a constant space solution?
 *
 * https://leetcode.com/problems/set-matrix-zeroes/description/
 */
public class SetMartixZeros {

  @Test
  public void test() {
    int[][][] testCases = {
        {
            {0, 1, 2, 0},
            {3, 4, 5, 2},
            {1, 3, 1, 5},
        },
        {
            {0},
            {2},
            {3}
        },
        {
            {Integer.MIN_VALUE},
            {2},
            {3}
        },
        {
            {1, 0}
        },
    };

    for (int[][] test : testCases) {
      setZeroes(test);
    }
  }

  public void setZeroes(int[][] matrix) {
    if (matrix == null || matrix.length == 0) {
      return;
    }
    int R = matrix.length, C = matrix[0].length, LIMIT = Math.max(R, C);
    List<Integer> zeros = new LinkedList<>();
    for (int row = 0; row < R; row++) {
      for (int col = 0; col < C; col++) {
        if (matrix[row][col] == 0) {
          zeros.add(row * LIMIT + col);
        }
      }
    }

    for (int zeroIndex : zeros) {
      int row = zeroIndex / LIMIT, col = zeroIndex % LIMIT;
      for (int r = 0; r < R; r++) {
        matrix[r][col] = 0;
      }
      for (int c = 0; c < C; c++) {
        matrix[row][c] = 0;
      }
    }
  }
}
