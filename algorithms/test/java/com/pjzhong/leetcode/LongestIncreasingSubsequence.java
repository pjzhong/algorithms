package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * Given an unsorted array of integers, find the length of longest increasing subsequence.
 * For example,
 * Given [10, 9, 2, 5, 3, 7, 101, 18],
 * The longest increasing subsequence is [2, 3, 7, 101], therefore the length is 4. Note that there may be more than
 * one LIS combination, it is only necessary for you to return the length.
 * <p>
 * Your algorithm should run in O(n2) complexity.
 * <p>
 * Follow up: Could you improve it to O(n log n) com.pjzhong.time complexity?
 * <p>
 * https://leetcode.com/problems/longest-increasing-subsequence/description/
 */
public class LongestIncreasingSubsequence {

    @Test
    public void test() {
        int[][] testCases = {
                {10, 9, 2, 5, 3, 7, 101, 18},
                {1, 3, 6, 7, 9, 4, 10, 5, 6},
                {10, 22, 9, 33, 21, 50, 41, 60, 80},
        };

        for (int[] test : testCases) {
            System.out.println(lengthOfLIS(test));
        }
    }

    private int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        return DPSolution(nums);
    }

    private int DPSolution(int[] nums) {
        int maxIdx = 0;

        int[] dp = new int[nums.length];
        for (int i = 0, size = nums.length; i < size; i++) {
            dp[i] = 1;
            if (nums[maxIdx] < nums[i]) {
                dp[i] = dp[maxIdx] + 1;
            } else {
                for (int k = i - 1; 0 <= k; --k) {
                    if (nums[k] < nums[i]) {
                        dp[i] = dp[k] + 1;
                        break;
                    }
                }
            }
            if (dp[maxIdx] < dp[i]) {
                maxIdx = i;
            }
        }

        return dp[maxIdx];
    }
}
