package com.pjzhong.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Given an array of distinct integers arr, find all pairs of elements with the minimum absolute
 * difference of any two elements.
 *
 * Return a list of pairs in ascending order(with respect to pairs), each pair [a, b] follows
 *
 * <p>a, b are from arr</p>
 * <p>a < b</p>
 * <p>b - a equals to the minimum absolute difference of any two elements in arr</p>
 *
 *
 *
 * <p>Input: arr = [4,2,1,3]</p>
 * <p>Output: [[1,2],[2,3],[3,4]]</p>
 * <p>Explanation: The minimum absolute difference is 1. List all pairs with difference equal to 1
 * in ascending order.</p>
 *
 * @link https://leetcode.com/problems/minimum-absolute-difference/
 **/
public class MinimumAbsoluteDifference {

  @Test
  public void test() {
    int[][] tests = {
        {4, 2, 1, 3},
        {1, 3, 6, 10, 15},
        {3, 8, -10, 23, 19, -4, -14, 27}
    };

    for (int[] t : tests) {
      System.out.println(minimumAbsDifference(t));
    }
  }

  public List<List<Integer>> minimumAbsDifference(int[] arr) {
    Arrays.sort(arr);
    int min = Integer.MAX_VALUE;
    for (int i = 1; i < arr.length; i++) {
      min = Math.min(min, Math.abs(arr[i - 1] - arr[i]));
    }

    List<List<Integer>> result = new ArrayList<>();
    for (int i1 : arr) {
      int t1 = Arrays.binarySearch(arr, i1 + min);
      if (0 < t1) {
        result.add(Arrays.asList(i1, arr[t1]));
      }
    }

    return result;
  }

}
