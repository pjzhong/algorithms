package leetcode;

import org.junit.Test;

import java.util.*;

/**
Given a collection of distinct integers, return all possible permutations.

Example:

Input: [1,2,3]
Output:
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]

 https://leetcode.com/problems/permutations/description/
* */
public class Permutations {

    @Test
    public void test() {
        int[][] testCases = {
                {1, 2, 3}
        };

        for(int[] test : testCases) {
            permute(test).forEach(System.out::println);
            System.out.println();
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        return doPermute(nums, 0, new LinkedList<>());
    }

    private List<List<Integer>> doPermute(int[] nums, int start, List<List<Integer>> result) {
        if(nums.length <= start) {
            List<Integer> temp = new ArrayList<>();
            for (int num : nums) { temp.add(num); }
            result.add(temp);
            return result;
        }

        int temp;
        for(int i = start; i < nums.length; i++) {
            temp = nums[start]; nums[start] = nums[i]; nums[i] = temp;
            doPermute(nums, start + 1, result);
            temp = nums[start]; nums[start] = nums[i]; nums[i] = temp;
        }

        return result;
    }
}
