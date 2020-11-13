package com.pjzhong.leetcode;

/**
 * The Hamming distance between two integers is the number of positions at which the corresponding
 * bits are different.
 *
 * Given two integers x and y, calculate the Hamming distance.
 *
 * @author ZJP
 * @link https://leetcode.com/problems/hamming-distance/
 * @since 2020年01月02日 22:18:56
 **/
public class HammingDistance {

  public int hammingDistance(int x, int y) {
    int aXORb = x ^ y;
    int res = 0;
    while (0 != aXORb) {
      res += (aXORb & 1);
      aXORb >>>= 1;
    }
    return res;
  }
}
