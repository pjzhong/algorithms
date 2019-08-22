package com.pjzhong.leetcode.contest88;

import org.junit.Test;

/**
 * User Accepted: 1861 User Tried: 1888 Total Accepted: 1911 Total Submissions: 2465 Difficulty:
 * Easy Let's call an array A a mountain if the following properties hold:
 *
 * A.length >= 3 There exists some 0 < i < A.length - 1 such that A[0] < A[1] < ... A[i-1] < A[i] >
 * A[i+1] > ... > A[A.length - 1] Given an array that is definitely a mountain, return any i such
 * that A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1].
 *
 * Example 1:
 *
 * Input: [0,1,0] Output: 1 Example 2:
 *
 * Input: [0,2,1,0] Output: 1 Note:
 *
 * 3 <= A.length <= 10000 0 <= A[i] <= 10^6 A is a mountain, as defined above.
 * https://leetcode.com/contest/weekly-contest-89/problems/peak-index-in-a-mountain-array/
 */
public class PeakIndexinaMountainArray {

  @Test
  public void test() {
    int[][] testCases = {
        {0, 1, 0},
        {0, 2, 1, 0},
    };

    for (int[] t : testCases) {
      System.out.println(peakIndexInMountainArray(t));
    }
  }

  public int peakIndexInMountainArray(int[] A) {
    int left, right;
    for (int i = 1; i < A.length; i++) {
      left = i - 1;
      right = i + 1;
        for (; 0 <= left && A[left] < A[left + 1]; left--) {
            ;
        }
        for (; right < A.length && A[right] < A[right - 1]; right++) {
            ;
        }

      if (left == -1 && right == A.length) {
        return i;
      }
    }
    return -1;
  }
}
