package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 Given a set of distinct integers, nums, return all possible subsets (the power set).

 Note: The solution set must not contain duplicate subsets.

 Example:

 Input: nums = [1,2,3]
 Output:
 [
 [3],
 [1],
 [2],
 [1,2,3],
 [1,3],
 [2,3],
 [1,2],
 []
 ]
 https://leetcode.com/problems/subsets/description/
 * */
public class Subsets {

    @Test
    public void test() {
        int[][] testCases = {
                {1, 2, 3},
                {1,2,3,4,5}
        };

        for(int[] nums : testCases) {
            subsets(nums).forEach(System.out::println);
            System.out.println();
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new LinkedList<>();
        subset(nums, 0, new ArrayList<>(), result);
        return result;
    }

    //This version is more concise than mine.....You didn't understand this problem deeply
    private void subset(int[] nums , int start , List<Integer> count, List<List<Integer>> result) {
        result.add(new ArrayList<>(count));
        for(int i = start; i < nums.length; i++) {
            count.add(nums[i]);
            subset(nums, i + 1,  count, result);
            count.remove(count.size() - 1);
        }
    }
}
