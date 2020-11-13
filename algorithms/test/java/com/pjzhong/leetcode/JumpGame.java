package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * Given an array of non-negative integers, you are initially positioned at the first index of the
 * array.
 *
 * Each element in the array represents your maximum jump length at that position.
 *
 * Determine if you are able to reach the last index.
 *
 * @link https://com.pjzhong.leetcode.com/problems/jump-game/
 **/
public class JumpGame {

  @Test
  public void test() {
    int[][] tests = {
        {2, 3, 1, 1, 4},
        {3, 2, 1, 0, 4},
        {3, 2, 2, 0, 4},
        {3, 2, 2, 0, 0, 0, 4},
        {0, 12, 13, 14, 15, 0, 6},
        {1, 12, 13, 14, 15, 1, 0},
        {0},
        {2, 0, 0},
        {1, 0, 0},
        {2, 0, 0, 1},
    };

    for (int[] t : tests) {
      System.out.println(canJump(t));
    }
  }

  public boolean canJump(int[] nums) {
    int max = 0;
    for (int i = 0; i < nums.length; i++) {
      max = Math.max(max, nums[i] + i);
      if (max <= i) {
        break;
      }
    }

    return nums.length - 1 <= max;
  }
}
