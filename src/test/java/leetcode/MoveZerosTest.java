package leetcode;

import org.junit.Test;

/**
 * Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 *
 * For example, given nums = [0, 1, 0, 3, 12], after calling your function, nums should be [1, 3, 12, 0, 0].
 * Note:
 * You must do this in-place without making a copy of the array.
 * Minimize the total number of operations.
 */
public class MoveZerosTest {

    @Test
    public void test() {
        int[][] testCases = {
                {0, 1, 0, 3, 12},
                {0, 1, 0, 3, 4, 6, 0, 12, 0}
        };

        for(int[] testCase : testCases) {
            moveZerosUseInsertSort(testCase);
        }
    }

    private void moveZerosUseInsertSort(int[] nums) {
        for(int i = nums.length - 1, zeroBounds = nums.length - 1; i >= 0; i--) {
            if(nums[i] == 0) {
                for(int index = i;  index <= zeroBounds - 1; index++) {
                    nums[index] = nums[index + 1];
                }
                nums[zeroBounds--] = 0;
            }
        }
    }

    private void moveZeros(int[] nums) {
        
    }
}
