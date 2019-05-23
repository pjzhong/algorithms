package leetcode;

import org.junit.Test;

import java.util.Arrays;

/**
 * Given an array, rotate the array to the right by k steps, where k is non-negative.
 * <p>
 * Example 1:
 * <p>
 * Input: [1,2,3,4,5,6,7] and k = 3
 * <p>
 * Output: [5,6,7,1,2,3,4] Explanation:
 * <p>
 * <p>
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 * <p>
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 * <p>
 * rotate 3 steps to the right:[5,6,7,1,2,3,4]
 * <p>
 * Example 2:
 * <p>
 * Input: [-1,-100,3,99] and k = 2
 * <p>
 * Output: [3,99,-1,-100] Explanation:
 * <p>
 * rotate 1 steps to the right:[99,-1,-100,3]
 * <p>
 * rotate 2 steps to the right: [3,99,-1,-100]
 * <p>
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

    /**
     * @link https://leetcode.com/problems/rotate-array/discuss/54250/Easy-to-read-Java-solution
     */
    public void rotate(int[] nums, int k) {
        int moves = k % nums.length;
        if (moves == 0) {
            return;
        }
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, moves - 1);
        reverse(nums, moves, nums.length - 1);

    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }


    public void copySolution(int[] nums, int k) {
        int moves = k % nums.length;
        if (moves == 0) {
            return;
        }

        int[] save = new int[nums.length];
        System.arraycopy(nums, 0, save, 0, nums.length);
        for (int i = 0, size = save.length; i < size; i++) {
            int idx = (i + moves) % size;
            nums[idx] = save[i];
        }
    }


    @Deprecated
    private void copySolution2(int[] nums, int k) {
        int moves = k % nums.length;
        if (moves == 0) {
            return;
        }

        // Worked, But hurst to understand
        int[] save = new int[moves];
        System.arraycopy(nums, nums.length - moves, save, 0, moves);
        for (int to = nums.length - 1, i = to - moves; 0 <= i; i--, to--) {
            nums[to] = nums[i];
        }
        System.arraycopy(save, 0, nums, 0, moves);
    }

}
