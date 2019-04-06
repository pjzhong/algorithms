package leetcode;


import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

/**
 * Given an array of integers, find if the array contains any duplicates.
 *
 * Your function should return true if any value appears at least twice in the array, and it should
 * return false if every element is distinct.
 *
 * Example 1:
 *
 * Input: [1,2,3,1] Output: true
 *
 * Example 2:
 *
 * Input: [1,2,3,4] Output: false
 *
 * Example 3:
 *
 * Input: [1,1,1,3,3,4,3,2,4,2] Output: true
 *
 * @link https://leetcode.com/problems/contains-duplicate/
 */
public class ContainsDuplicate {

  @Test
  public void test() {
    int[][] tests = {{1, 2, 3, 1}, {1, 2, 3, 4}, {1, 1, 1, 3, 3, 4, 3, 2, 4, 2}};

    for (int[] test : tests) {
      System.out.printf("%s%n", containsDuplicate(test));
    }
  }

  public boolean containsDuplicate(int[] nums) {
    Set<Integer> sets = new HashSet<>(nums.length);

    for (int i : nums) {
      if (!sets.add(i)) {
        return true;
      }
    }

    return false;
  }
}
