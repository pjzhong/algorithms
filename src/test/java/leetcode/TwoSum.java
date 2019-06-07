package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * Given an array of integers, return indices of the two numbers such that they add up to a specific
 * target.
 *
 * You may assume that each input would have exactly one solution, and you may not use the same
 * element twice.
 *
 * Example:
 *
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 *
 * return [0, 1].
 *
 * @since 2019年06月07日 11:09:47
 * @link https://leetcode.com/problems/two-sum/
 **/
public class TwoSum {

  @Test
  public void test() {
    int[][] tests = {
        {2, 7, 11, 5}
    };
    int[] targets = {9};

    for (int i = 0; i < targets.length; i++) {
      int[] res = twoSum(tests[i], targets[i]);
      System.out.println(Arrays.toString(res));
    }
  }

  public int[] twoSum(int[] nums, int target) {
    int[] result = new int[2];
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      int find = target - nums[i];
      if (map.containsKey(find)) {
        result[1] = i;
        result[0] = map.get(find);
        break;
      }

      map.put(nums[i], i);
    }
    return result;
  }

}
