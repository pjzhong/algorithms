package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * The Hamming distance between two integers is the number of positions at which the corresponding
 * bits are different.
 *
 * Now your job is to find the total Hamming distance between all pairs of the given numbers.
 *
 * @author ZJP
 * @link https://leetcode.com/problems/total-hamming-distance/
 * @since 2020年01月02日 23:26:44
 **/
public class TotalHammingDistance {

  @Test
  public void test() {
    int[][] tests = {
        {4, 14, 2},
        {0},
        {-1, 0},
        {Integer.MIN_VALUE, Integer.MAX_VALUE}
    };

    for (int[] t : tests) {
      System.out.println(totalHammingDistance(t));
    }
  }

  /**
   * brute force
   *
   * @since 2020年01月02日 23:01:14
   */
  public int totalHammingDistance(int[] nums) {
    int res = 0;
    for (int i = 0; i < nums.length; i++) {
      for (int j = i + 1; j < nums.length; j++) {
        res += Integer.bitCount(nums[i] ^ nums[j]);
      }
    }
    return res;
  }

  private int HammingDistance(int a, int b) {
    int aXORb = a ^ b;
    int res = 0;
    while (0 != aXORb) {
      res += (aXORb & 1);
      aXORb >>>= 1;
    }
    return res;
  }


  /**
   * excellent solution
   *
   * @link https://leetcode.com/problems/total-hamming-distance/discuss/457802/O(N)-JAVA-SOLUTION-%2B-EXAMPLE
   * @since 2020年01月02日 23:02:24
   */
  public int totalHammingDistance_logN(int[] nums) {
    int res = 0;
    for (int i = 0; i < 32; i++) {
      int ones = 0;
      for (int j = 0; j < nums.length; j++) {
        ones += (nums[j] >> i) & 1;
      }
      res += ones * (nums.length - ones);
    }
    return res;
  }
}
