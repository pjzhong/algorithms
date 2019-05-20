package leetcode;

import java.util.Arrays;
import org.junit.Test;

/**
 * Given an array, rotate the array to the right by k steps, where k is non-negative.
 *
 * Example 1:
 *
 * Input: [1,2,3,4,5,6,7] and k = 3
 *
 * Output: [5,6,7,1,2,3,4] Explanation:
 *
 *
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 *
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 *
 * rotate 3 steps to the right:[5,6,7,1,2,3,4]
 *
 * Example 2:
 *
 * Input: [-1,-100,3,99] and k = 2
 *
 * Output: [3,99,-1,-100] Explanation:
 *
 * rotate 1 steps to the right:[99,-1,-100,3]
 *
 * rotate 2 steps to the right: [3,99,-1,-100]
 *
 * Note: Try to come up as many solutions as you can, there are at least 3 different ways to solve
 * this problem. Could you do it in-place with O(1) extra space?
 *
 * @link https://leetcode.com/problems/rotate-array/
 **/
public class RotateArray {

  @Test
  public void test() {
    int[][] tests = {
        {1, 2, 3, 4, 5, 6, 7},
        {1, 2, 3, 4, 5, 6, 7},
        {1, 2, 3, 4, 5, 6, 7},
        {-1, -100, 3, 99},
        {-1, -100, 3, 99},
        {-1, -100, 3, 99},
    };
    int[] ks = {3, 1, 7, 2, 1, 3};

    for (int i = 0; i < ks.length; i++) {
      rotate(tests[i], ks[i]);
      System.out.println(Arrays.toString(tests[i]));
    }
  }

  public void rotate(int[] nums, int k) {
    //TODO in-place solution
  }


  public void copySolution(int[] nums, int k) {
    int moves = k % nums.length;
    if (moves == 0) {
      return;
    }

    int[] save = new int[moves];
    System.arraycopy(nums, nums.length - moves, save, 0, moves);
    for (int to = nums.length - 1, i = to - moves; 0 <= i; i--, to--) {
      nums[to] = nums[i];
    }
    System.arraycopy(save, 0, nums, 0, moves);
  }

}
