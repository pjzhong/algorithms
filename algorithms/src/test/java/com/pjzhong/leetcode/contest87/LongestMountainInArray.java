package com.pjzhong.leetcode.contest87;

import org.junit.Test;

/**
 * Let's call any (contiguous) subarray B (of A) a mountain if the following properties hold:
 * <p>
 * B.length >= 3
 * There exists some 0 < i < B.length - 1 such that B[0] < B[1] < ... B[i-1] < B[i] > B[i+1] > ... > B[B.length - 1]
 * (Note that B could be any subarray of A, including the entire array A.)
 * <p>
 * Given an array A of integers, return the length of the longest mountain.
 * <p>
 * Return 0 if there is no mountain.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: [2,1,4,7,3,2,5]
 * Output: 5
 * Explanation: The largest mountain is [1,4,7,3,2] which has length 5.
 * Example 2:
 * <p>
 * Input: [2,2,2]
 * Output: 0
 * Explanation: There is no mountain.
 * <p>
 * <p>
 * Note:
 * <p>
 * 0 <= A.length <= 10000
 * 0 <= A[i] <= 10000
 * <p>
 * https://leetcode.com/problems/longest-mountain-in-array/description/
 */
public class LongestMountainInArray {

    @Test
    public void test() {
        int[][] testCases = {
                {2, 1, 4, 7, 3, 2, 5},
                {2, 2, 2},
                {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
                {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
        };

        for (int[] t : testCases) {
            System.out.println(longestMountain(t));
        }
    }

    public int longestMountain(int[] A) {
        int longest = 0;

        int leftCount = 0, rightCount = 0;
        for (int i = 1, size = A.length - 1; i < size; i++) {
            leftCount = rightCount = 0;
            for (int left = i - 1; 0 <= left && A[left] < A[left + 1]; left--) {
                leftCount++;
            }
            for (int right = i + 1; right < A.length && A[right] < A[right - 1]; right++) {
                rightCount++;
            }
            if (0 < leftCount && 0 < rightCount && 3 <= (leftCount + rightCount + 1)) {
                longest = Math.max(longest, 1 + leftCount + rightCount);
            }
        }

        return longest;
    }
}
