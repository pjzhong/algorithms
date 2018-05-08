import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 Given a set of candidate numbers (candidates) (without duplicates) and a target number (target),
 find all unique combinations in candidates where the candidate numbers sums to target.

 The same repeated number may be chosen from candidates unlimited number of times.

 Note:

 All numbers (including target) will be positive integers.
 The solution set must not contain duplicate combinations.
 Example 1:

 Input: candidates = [2,3,6,7], target = 7,
 A solution set is:
 [
 [7],
 [2,2,3]
 ]
 Example 2:

 Input: candidates = [2,3,5], target = 8,
 A solution set is:
 [
 [2,2,2,2],
 [2,3,3],
 [3,5]
 ]

 https://leetcode.com/problems/combination-sum/description/
 * */
public class CombinationSum {

    @Test
    public void test() {
        int[][] testCases = {
                {2,3,6,7},
                {2,3,5},
        };
        int[] target = {7, 8};

        for(int i = 0; i < testCases.length; i++) {
            combinationSum(testCases[i], target[i]).forEach(System.out::println);
            System.out.println();
        }
    }

    private List<List<Integer>> result;
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        result = new LinkedList<>();
        combinationSum(candidates, target, 0, 0, new LinkedList<>());
        return result;
    }

    public void combinationSum(int[] candidates, int target, int sum, int start , LinkedList<Integer> count) {
        if(target < sum) { return;}
        if(candidates.length <= start || sum == target) {
            result.add(new ArrayList<>(count));
        }
        for(int i = start; i < candidates.length; i++) {
            count.add(candidates[i]);
            combinationSum(candidates, target, sum + candidates[i], i, count);
            count.removeLast();
        }
    }
}
