package com.pjzhong.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import org.junit.Test;

/**
 * Given an array nums of n integers and an integer target, are there elements a, b, c, and d in
 * nums such that a + b + c + d = target? Find all unique quadruplets in the array which gives the
 * sum of target.
 *
 * Notice that the solution set must not contain duplicate quadruplets.
 *
 *
 *
 * Example 1:
 *
 * <p>Input: nums = [1,0,-1,0,-2,2], target = 0</p>
 * <p>Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]</p>
 *
 * @link https://leetcode.com/problems/4sum/
 * @since 2020年12月13日 16:29:04
 **/
public class Sum4 {

  @Test
  public void test() {
    int[][] tests = {
        {0, 1, 5, 0, 1, 5, 5, -4},
        {1, 0, -1, 0, 0, -2, 2},
        {},
        {-5, 5, 4, -3, 0, 0, 4, -2}
    };
    int[] targets = {11, 0, 0, 4};
    int idx = 0;
    for (int[] test : tests) {
      List<List<Integer>> result = fourSum(test, targets[idx]);
      System.out.println(result);
      idx++;
    }
  }

  public List<List<Integer>> fourSum(int[] nums, int target) {
    if (nums.length <= 0) {
      return Collections.emptyList();
    }
    List<List<Integer>> result = new ArrayList<>();
    Deque<Integer> temp = new ArrayDeque<>();

    Arrays.sort(nums);
    fourSum(result, temp, 0, 0, nums, target);

    return new ArrayList<>(result);
  }

  public void fourSum(List<List<Integer>> result, Deque<Integer> temp, int idx, int sum, int[] nums,
      int target) {
    if (temp.size() == 4) {
      if (sum == target) {
        result.add(Arrays.asList(temp.toArray(new Integer[0])));
      }
      return;
    }

    int prev = Integer.MIN_VALUE, max = nums[nums.length - 1];
    int left = 4 - temp.size() - 1;
    for (int i = idx, size = nums.length; i < size; ++i) {
      int num = nums[i];
      if (prev == num) {
        continue;
      }

      if (sum + num + left * max < target) {
        continue;
      }

      prev = num;
      sum += num;
      temp.addLast(num);
      fourSum(result, temp, i + 1, sum, nums, target);
      temp.pollLast();
      sum -= num;
    }
  }

}
