package com.pjzhong.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

/**
 * Given a collection of numbers that might contain duplicates, return all possible unique
 * permutations.
 *
 * @link https://com.pjzhong.leetcode.com/problems/3sum/
 */
public class ThreeSum {

  @Test
  public void test() {
    int[][] testCases = {
        {1, 1, 2, 2},
        {-1, 0, 1, 2, -1, -4},
        {1, 1, -2, -2, 1, 1},
        {0, 0, 0}
    };

    for (int[] test : testCases) {
      List<List<Integer>> res = threeSum(test);
      res.forEach(System.out::println);
      System.out.println();
    }
  }

  //Slow
  public List<List<Integer>> threeSum(int[] nums) {
    Set<List<Integer>> res = new HashSet<>();
    int size = nums.length;

    Arrays.sort(nums);
    for (int i = 0; i < size; i++) {
      for (int j = i + 1; j < size; j++) {
        int find = 0 - (nums[i] + nums[j]);
        int idx = Arrays.binarySearch(nums, j + 1, size, find);
        if (j < idx) {
          res.add(Arrays.asList(nums[i], nums[j], nums[idx]));
        }
      }
    }

    return new ArrayList<>(res);
  }
}
