package com.pjzhong.leetcode;

/**
 * Write a function that takes an unsigned integer and return the number of '1' bits it has (also
 * known as the Hamming weight).
 *
 * @author ZJP
 * @link https://leetcode.com/problems/number-of-1-bits/submissions/
 * @since 2020年01月02日 22:22:18
 **/
public class Numberof1Bits {

  // you need to treat n as an unsigned value
  public int hammingWeight(int n) {
    int res = 0;
    while (0 != n) {
      res += (n & 1);
      n >>>= 1;
    }
    return res;
  }
}
