package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Find all possible combinations of k numbers that add up to a number n, given that only numbers
 * from 1 to 9 can be used and each combination should be a unique set of numbers.
 *
 * Note:
 *
 * All numbers will be positive integers. The solution set must not contain duplicate combinations.
 * Example 1:
 *
 * Input: k = 3, n = 7 Output: [[1,2,4]] Example 2:
 *
 * Input: k = 3, n = 9 Output: [[1,2,6], [1,3,5], [2,3,4]]
 *
 * https://leetcode.com/problems/combination-sum-iii/description/
 */
public class CombinationSumIII {

  @Test
  public void test() {
    int[][] testCases = {
        {3, 7},
        {3, 9},
        {3, 0}
    };

    for (int[] test : testCases) {
      combinationSum3(test[0], test[1]).forEach(System.out::println);
      System.out.println();
    }
  }

  public List<List<Integer>> combinationSum3(int k, int n) {
    List<List<Integer>> result = new LinkedList<>();
    combine(1, k, n, new ArrayList<>(), result);
    return result;
  }

  public void combine(int start, int k, int target, List<Integer> nums,
      List<List<Integer>> result) {
    if (k == 0) {
      if (target == 0) {
        result.add(new ArrayList<>(nums));
      }
    } else {
      for (int i = start, size = 9 - (k - 1); i <= size; i++) {
        nums.add(i);
        combine(i + 1, k - 1, target - i, nums, result);
        nums.remove(nums.size() - 1);
      }
    }
  }
}
