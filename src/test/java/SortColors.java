import org.junit.Test;

import java.util.Arrays;

/**
 * Given an array with n objects colored red, white or blue, sort them so that objects of the same color are adjacent,
 * with the colors in the order red, white and blue.
 * Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
 *
 * Note:
 * You are not suppose to use the library's sort function for this problem.
 *
 * click to show follow up.
 *
 * Follow up:
 *
 * A rather straight forward solution is a two-pass algorithm using counting sort.
 * First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's,
 * then 1's and followed by 2's.
 *
 * Could you come up with an one-pass algorithm using only constant space?
 *
 * https://leetcode.com/problems/sort-colors/description/
 * */
public class SortColors {

    @Test
    public void test() {
        int[][] testCases = {
                {0,1,0,0,2,1},
                {0,2,1,0,2,1},
                {2,1,0,0,2,1},
                {1,1,2},
                {2,1,2},
                {0,1,2},
                {0},
                {1,2},
                {2,1},
                {2,1,0}
        };

        for(int[] colors : testCases) {
            sortColors(colors);
            System.out.println(Arrays.toString(colors));
        }
    }

    /**
     * after i iterations
     * guaranteed from 0 to i - 1 are ordered(red white) and the last (nums.length - blueIndex) elements is ordered
     * */
    public void sortColors(int[] nums) {
        for(int i = 0, redIndex = 0, blueIndex = nums.length; i < blueIndex;) {
            switch (nums[i]) {
                case 0:swap(nums, i++, redIndex++);break;
                case 2:swap(nums, i, --blueIndex);break;
                default:i++;
            }
        }
    }

    private void swap(int[] num, int i, int j) {
        if(i == j) { return; }
        num[i] = num[i] + num[j];
        num[j] = num[i] - num[j];
        num[i] = num[i] - num[j];
    }
}
