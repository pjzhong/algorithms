package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.
 *
 * Example:
 *
 * Input: n = 4, k = 2 Output: [ [2,4], [3,4], [2,3], [1,2], [1,3], [1,4], ]
 *
 * https://leetcode.com/problems/combinations/description/
 */
public class Combinations {

  @Test
  public void test() {
    int[][] testCases = {
        {4, 2},
        {5, 3},
        {1, 1},
        {1, 0}
    };

    for (int[] test : testCases) {
      combine(test[0], test[1]).forEach(System.out::println);
      System.out.println();
    }
  }

  public List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> result = new LinkedList<>();
    combine(1, n, k, new ArrayList<>(), result);
    return result;
  }

  public void combine(int start, int n, int k, List<Integer> nums, List<List<Integer>> result) {
    if (k <= 0) {
      result.add(new ArrayList<>(nums));
      return;
    }
    for (int i = start, size = n - (k - 1); i <= size; i++) {
      nums.add(i);
      combine(i + 1, n, k - 1, nums, result);
      nums.remove(nums.size() - 1);
    }
  }
}
