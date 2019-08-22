package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right
 * which minimizes the sum of all numbers along its path.
 *
 * Note: You can only move either down or right at any point in com.pjzhong.time.
 *
 * Example 1: [[1,3,1], [1,5,1], [4,2,1]] Given the above grid map, return 7. Because the path
 * 1→3→1→1→1 minimizes the sum.
 *
 * https://leetcode.com/problems/minimum-path-sum/description/
 */
public class MinimumPathSum {

  @Test
  public void test() {
    int[][][] testCases = {
        {
            {1, 3, 1},
            {1, 5, 1},
            {4, 2, 1}
        }
    };

    for (int[][] testCase : testCases) {
      System.out.println(minPathSum(testCase));
    }
  }

  /**
   * 1) Optimal Substructure The path to reach (i, j) must be through one the 3 cells:(i - 1, j),
   * (i, j - 1) or (i - 1, j - 1) So minimum cost to reach (i, j) can be written as "minimum of the
   * 3 cells plus cost[i][j]"
   *
   * minCost(i, j) = min(minCost(i - 1, j), minCost(i, j - 1), minCost(i - 1, j - 1))
   *
   * 2) Overlapping Subproblems Try to implement a recursive solution without memorize the solutions
   * of Subproblems and draw a graph from it to see overlapping Subproblems
   */
  private int minPathSum(int[][] grid) {
    final int rowSize = grid.length, colSize = grid[0].length;
    for (int i = 1; i < colSize; ++i) {
      grid[0][i] += grid[0][i - 1];
    }

    for (int i = 1; i < rowSize; ++i) {
      grid[i][0] += grid[i - 1][0];
      for (int j = 1; j < colSize; ++j) {
        //can only go down and right from [i, j] can go to [i + 1, j] or [i, j + 1]
        grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);

        //can only go down, right and diagonally lower
        // from [i, j] can go to [i + 1, j] , [i, j + 1], [i + 1][j + 1]
        //grid[i][j] += Math.min(Math.min(grid[i - 1][j], grid[i][j - 1]), grid[i - 1][j - 1]);
      }
    }

    return grid[rowSize - 1][colSize - 1];
  }
}
