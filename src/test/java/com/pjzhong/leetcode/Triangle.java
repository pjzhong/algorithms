package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a triangle, find the minimum path sum from top to bottom. Each step you may move to
 * adjacent numbers on the row below.
 *
 * For example, given the following triangle
 *
 * [ [2], [3,4], [6,5,7], [4,1,8,3] ] The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5
 * + 1 = 11).
 *
 * Note:
 *
 * Bonus point if you are able to do this using only O(n) extra space, where n is the total number
 * of rows in the triangle.
 *
 * https://leetcode.com/problems/triangle/description/
 */
public class Triangle {

  public List<List<List<Integer>>> buildTestCases() {
    Integer[][][] testCases = {
        {{2}},
        {
            {2},
            {3, 4},
            {6, 5, 7},
            {4, 1, 8, 3},
        },
        {
            {1},
            {2, 3},
            {4, 5, 6},
            {7, 8, 9, 10},
        },
    };

    List<List<List<Integer>>> lists = new ArrayList<>();
    for (int i = 0; i < testCases.length; i++) {
      List<List<Integer>> triangle = new ArrayList<>();
      for (Integer[] t : testCases[i]) {
        triangle.add(Arrays.asList(t));
      }
      lists.add(triangle);
    }

    return lists;
  }

  @Test
  public void test() {
    for (List<List<Integer>> t : buildTestCases()) {
      System.out.println(minimumTotal(t));
    }
  }

  /**
   * My solution
   */
  public int minimumTotal(List<List<Integer>> triangle) {
    if (triangle.size() == 1) {
      triangle.get(0).get(0);
    }

    int ROW = triangle.size();
    for (int level = 1; level < ROW; level++) {
      List<Integer> pre = triangle.get(level - 1), now = triangle.get(level);
      for (int i = 0, size = pre.size(); i <= size; i++) {
        if (0 < i && i < size) {
          now.set(i, Math.min(pre.get(i - 1), pre.get(i)) + now.get(i));
        } else if (0 == i) {
          now.set(0, now.get(0) + pre.get(0));
        } else {
          now.set(size, now.get(size) + pre.get(size - 1));
        }
      }
    }

    int min = Integer.MAX_VALUE;
    for (int i : triangle.get(ROW - 1)) {
      min = Math.min(min, i);
    }
    return min;
  }

  /**
   * A more concise solution, using Sentinel element. Greatly reduce the complexity of the code
   *
   * from leetCode
   */
  public int moreConciseSolution(List<List<Integer>> triangle) {
    int[] dp = new int[triangle.size() + 1];// + 1, 哨兵元素，极大的简化了代码复杂度

    for (int i = triangle.size() - 1; 0 <= i; i--) {
      List<Integer> ints = triangle.get(i);
      for (int j = 0; j < ints.size(); j++) {
        dp[j] = ints.get(j) + Math.min(dp[j], dp[j + 1]);
      }
    }

    return dp[0];
  }
}
