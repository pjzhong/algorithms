package com.pjzhong.leetcode;

import java.util.TreeSet;
import org.junit.Test;

/**
 * Given an array of integers, find out whether there are two distinct indices i and j in the array
 * such that the absolute difference between nums[i] and nums[j] is at most t and the absolute
 * difference between i and j is at most k.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,1], k = 3, t = 0 Output: true
 *
 * Example 2:
 *
 * Input: nums = [1,0,1,1], k = 1, t = 2 Output: true
 *
 * Example 3:
 *
 * Input: nums = [1,5,9,1,5,9], k = 2, t = 3 Output: false
 *
 * @link https://com.pjzhong.leetcode.com/problems/contains-duplicate-iii/
 */
public class ContainsDuplicateIII {

  @Test
  public void test() {
    int[][] tests = {{1, 2, 3, 1}, {1, 0, 1, 1}, {1, 2, 3, 1, 2, 3}, {-3, 3}, {-1, 2147483647},
        {2147483647, -2147483647}, {1, 5, 9, 1, 5, 9}};
    int[] ks = {3, 1, 2, 2, 1, 1, 2};
    int[] ts = {0, 2, 3, 4, 2147483647, 2147483647, 3};

    for (int i = 0; i < ks.length; i++) {
      System.out.printf("%s%n", containsNearbyAlmostDuplicate(tests[i], ks[i], ts[i]));
    }
  }

  /**
   * @link https://com.pjzhong.leetcode.com/problems/contains-duplicate-iii/discuss/61655/Java-O(N-lg-K)-solution
   */
  public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
    TreeSet<Long> ind = new TreeSet<>();
    for (int i = 0; i < nums.length; i++) {
      long temp = nums[i];
      Long floor = ind.floor(temp + t);//the greatest ,less than or equal
      Long ceil = ind.ceiling(temp - t);//the least, greater than or equal
      if ((ceil != null && ceil <= temp) || (floor != null && temp <= floor)) {
        return true;
      }

      // if temp equal to nums[i - k], this algorithms would return true before running here;
      // so removing a element should remain in the set, wouldn't be a problem
      ind.add(temp);
      if (k <= i) {
        ind.remove((long) nums[i - k]);
      }

    }
    return false;
  }

  private boolean bruteForce(int[] nums, int k, int t) {
    //Brute force O(n^2)
    for (int i = 0, size = nums.length; i < size; i++) {
      long temp = nums[i];
      for (int j = Math.min(i + k, size - 1); i < j; j--) {
        if (Math.abs(temp - nums[j]) <= t) {
          return true;
        }
      }
    }
    return false;
  }
}
