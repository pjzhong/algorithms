package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.Arrays;

/**
 * QUESTION COPY FROM LEETCODE
 *
 * You are given a list of non-negative integers, a1, a2, ..., an, and a target, S.
 * Now you have 2 symbols + and -. For each integer, you should choose one from + and - as its new symbol.
 * Find out how many ways to assign symbols to make sum of integers equal to target S.
 *
 *
 * Example 1:
 * Input: nums is [1, 1, 1, 1, 1], S is 3.
 * Output: 5
 * Explanation:
 *
 * -1+1+1+1+1 = 3
 * +1-1+1+1+1 = 3
 * +1+1-1+1+1 = 3
 * +1+1+1-1+1 = 3
 * +1+1+1+1-1 = 3
 *
 * There are 5 ways to assign symbols to make the sum of nums be target 3.
 *
 * Note:
 * The length of the given array is positive and will not exceed 20.
 * The sum of elements in the given array will not exceed 1000.
 * Your output answer is guaranteed to be fitted in a 32-bit integer.
 */
public class TargetSumTest {

    @Test
    public void test() {
        int[][] testCases ={
                {1,1,1,1,1},
                {1,1,2,1,1},
                {1,1,2,1,1,2},
        };
        int[] targets = {3, 4, 2};

        for(int i = 0; i < testCases.length; i++) {
            targetSum(testCases[i], targets[i]);
        }

    }

    private void targetSum(int[] nums, int S) {
        System.out.println(targetSumRecursive(S, 0, nums));
        /************************************************/
        int[][] memorize = new int[nums.length][2001];
        for(int[] i : memorize) { Arrays.fill(i, Integer.MIN_VALUE);}
        System.out.println(targetSumRecursiveMemorize(S, 0, nums, memorize));
        /************************************************/
    }

    private int targetSumRecursive(int target, int start, int [] integer) {
        if(start >= integer.length) {return target == 0 ? 1 : 0;}

        int count = 0;
        count += targetSumRecursive(target + integer[start], start + 1, integer);
        count += targetSumRecursive(target - integer[start], start + 1, integer);
        return count;
    }

    private int targetSumRecursiveMemorize(int target, int start, int [] integer, int[][] memorize) {
        if(start >= integer.length) {return target == 0 ? 1 : 0;}

        if(memorize[start][target + 1000] != Integer.MIN_VALUE) {//plus 1000 to make sure the target is positive
            return memorize[start][target + 1000];
        }
        int count = 0;
        count += targetSumRecursiveMemorize(target + integer[start], start + 1, integer, memorize);
        count += targetSumRecursiveMemorize(target - integer[start], start + 1, integer, memorize);
        memorize[start][target + 1000] = count;
        return count;
    }

    /**
     *           F(target) == 1 target == 0
       边界值：
                 F(target) == 0 target < 0

                          | F(target + integers[i])
       子结构：F(target)
                         | F(target - integers[i])

       状态转移公式: F(target) = F(target + integers[i]) + F(target - integers[i])
       这个是递归的结构，那动态规划的思想是怎样的？
     *
     * */
    private int targetSumDp(int[] integers, int target) {
        int added = 0, notAdded = 0;
        return -1;
    }


}
