package leetcode;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * Given an array of integers and an integer k, find out whether there are two distinct indices i
 * and j in the array such that nums[i] = nums[j] and the absolute difference between i and j is at
 * most k.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,1], k = 3 Output: true
 *
 * Example 2:
 *
 * Input: nums = [1,0,1,1], k = 1 Output: true
 *
 * Example 3:
 *
 * Input: nums = [1,2,3,1,2,3], k = 2 Output: false
 *
 * @link https://leetcode.com/problems/contains-duplicate-ii/
 */
public class ContainsDuplicateII {

  @Test
  public void test() {
    int[][] tests = {{1, 2, 3, 1}, {1, 0, 1, 1}, {1, 2, 3, 1, 2, 3}};
    int[] ks = {3, 1, 2};

    for (int i = 0; i < ks.length; i++) {
      System.out.printf("%s%n", containsNearbyDuplicate(tests[i], ks[i]));
    }
  }

  public boolean containsNearbyDuplicate(int[] nums, int k) {
    Map<Integer, Integer> idxs = new HashMap<>();
    for (int i = 0, size = nums.length; i < size; i++) {
      if (idxs.containsKey(nums[i])) {
        if (i - idxs.get(nums[i]) <= k) {
          return true;
        }
      }
      idxs.put(nums[i], i);
    }

    return false;
  }
}
