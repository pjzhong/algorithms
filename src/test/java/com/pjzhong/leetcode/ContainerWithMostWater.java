package com.pjzhong.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i,
 * ai). n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0).
 * Find two lines, which together with x-axis forms a container, such that the container contains
 * the most water.
 *
 * Note: You may not slant the container and n is at least 2.
 *
 * Example:
 *
 * <p>Input: [1,8,6,2,5,4,8,3,7]</p>
 * Output: 49
 *
 * @link https://leetcode.com/problems/container-with-most-water/
 **/
public class ContainerWithMostWater {

  @Test
  public void test() {
    int[][] tests = {
        {1, 1, 1, 1, 1},
        {1, 1, 1, 7, 1, 1, 1},
        {1, 8, 6, 2, 5, 4, 8, 3, 7},
    };

    for (int[] test : tests) {
      assertEquals(maxArea(test), bruteForce(test));
    }
  }

  public int maxArea(int[] height) {
    int max = Integer.MIN_VALUE;

    int left = 0, right = height.length - 1;
    while (left < right) {
      int x = right - left, y = Math.min(height[left], height[right]);
      max = Math.max(max, x * y);

      if (height[left] < height[right]) {
        left++;
      } else {
        right--;
      }
    }
    return max;
  }

  public int bruteForce(int[] height) {
    int max = Integer.MIN_VALUE;

    for (int i = 0; i < height.length; i++) {
      for (int j = i + 1; j < height.length; j++) {
        int x = j - i, y = Math.min(height[i], height[j]);
        max = Math.max(max, x * y);
      }
    }

    return max;
  }

}
