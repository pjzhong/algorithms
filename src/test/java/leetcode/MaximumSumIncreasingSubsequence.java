package leetcode;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Given an array of n positive integers. Write a program to find the sum of maximum sum subsequence of the given array
 * such that the intgers in the subsequence are sorted in increasing order. For example,
 * if input is {1, 101, 2, 3, 100, 4, 5}, then output should be 106 (1 + 2 + 3 + 100),
 * if the input array is {3, 4, 5, 10}, then output should be 22 (3 + 4 + 5 + 10)
 * and if the input array is {10, 5, 4, 3}, then output should be 10
 *
 *
 * https://www.geeksforgeeks.org/dynamic-programming-set-14-maximum-sum-increasing-subsequence/
 * */
public class MaximumSumIncreasingSubsequence {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));

        int testCase = scanner.nextInt();
        for(int i = 0; i < testCase; i++) {
            int[] price = new int[scanner.nextInt()];
            scanner.nextLine();
            String[] input = scanner.nextLine().split(" ");
            for(int j = 0; j < input.length; j++) { price[j] = Integer.parseInt(input[j]); }

            System.out.println(maximumSumIncreasingSubsequence(price));
        }
    }

    /**
     * MySolution
     * */
    private static int maximumSumIncreasingSubsequence(int[] nums) {
        int maxIdx = 0;

        int[] dp = new int[nums.length];
        for(int i = 0, size = nums.length; i < size; i++) {
            dp[i] = nums[i];
            if(nums[maxIdx] < nums[i]) {
                dp[i] += dp[maxIdx];
            } else {
                for(int k = i - 1; 0 <= k; --k) {
                    if(nums[k] < nums[i] && (dp[i] < dp[k] + nums[i])) {//
                        dp[i] = dp[k] + nums[i];
                    }
                }
            }
            if(dp[maxIdx] < dp[i]) { maxIdx = i; }
        }

        return dp[maxIdx];
    }

    /**
     * The official solution is more elegance and concise
     * */
    private static int officialSolution(int[] nums) {
        int[] dp = new int[nums.length];
        for(int i = 0; i < nums.length; i++) {
            dp[i] = nums[i];
            for(int j = 0; j < i; j++) {
                if(nums[j] < nums[i] && (dp[i] < dp[j] + nums[i])) {
                    dp[i] = dp[j] + nums[i];
                }
            }
        }
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < dp.length; i++) {
            if(max < dp[i]) { max = dp[i];}
        }

        return max;
    }
}
