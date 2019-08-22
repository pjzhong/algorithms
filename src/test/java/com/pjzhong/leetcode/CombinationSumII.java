package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.*;

/**
 Given a collection of candidate numbers (candidates) and a target number (target),
 find all unique combinations in candidates where the candidate numbers sums to target.

 Each number in candidates may only be used once in the combination.

 Note:

 All numbers (including target) will be positive integers.
 The solution set must not contain duplicate combinations.
 Example 1:

 Input: candidates = [10,1,2,7,6,1,5], target = 8,
 A solution set is:
 [
 [1, 7],
 [1, 2, 5],
 [2, 6],
 [1, 1, 6]
 ]
 Example 2:

 Input: candidates = [2,5,2,1,2], target = 5,
 A solution set is:
 [
 [1,2,2],
 [5]
 ]

 note:a straightforward solution is to use set.......But I want to find another way If I can

 https://leetcode.com/problems/combination-sum-ii/description/
 * */
public class CombinationSumII {

    @Test
    public void test() {
        int[][] testCases = {
                {10,1,2,7,6,1,5},
                {2,5,2,1,2},
                {1,1,1,2,3},
        };
        int[] target = {8, 5,5};

        for(int i = 0; i < testCases.length; i++) {
            combinationSum2(testCases[i], target[i]).forEach(System.out::println);
            System.out.println();
        }
    }

    private List<List<Integer>> result;
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        result = new LinkedList<>();
        Arrays.sort(candidates);
        combinationSum2(candidates, target, 0, new ArrayList<>());
        return result;
    }

    public void combinationSum2(int[] candidates, int target, int start , List<Integer> count) {
        if(0 < target) {
            int last = -1;
            for(int i = start; i < candidates.length && candidates[i] <= target; i++) {
                if(last != candidates[i]) {
                    count.add(candidates[i]);
                    combinationSum2(candidates, target - candidates[i], i + 1, count);
                    last = count.remove(count.size() - 1);
                }
            }
        } else if(target == 0) {
            result.add(new ArrayList<>(count));
        }
    }
}
