package leetcode;

import org.junit.Test;

import java.util.Arrays;

/**
 Given an integer array, find three numbers whose product is maximum and output the maximum product.

 Example 1:
 Input: [1,2,3]
 Output: 6
 Example 2:
 Input: [1,2,3,4]
 Output: 24

 Note:
 The length of the given array will be in range [3,104] and all elements are in the range [-1000, 1000].
 Multiplication of any three numbers in the input won't exceed the range of 32-bit signed integer.

 https://leetcode.com/problems/maximum-product-of-three-numbers/description/
 * */
public class MaximumProductOfThreeNumbers {

    @Test
    public void test() {
        int[][] testCases = {
                {1,2,3},
                {-9, -3, -10, -1},
                {-1,-2,40, -40},
                {-4, -3, -2, -1, 60},
                {-4, -3, -2, -1, 60, 0}

        };
        for(int[] test : testCases) {
            System.out.println(maximumProduct(test));
        }
    }

    /**
     * A newbie solution, shame......
     * */
    public int maximumProduct(int[] nums) {
        if(nums.length <= 3) { return nums[0] * nums[1] * nums[2]; }
        Arrays.sort(nums);
        int theMaxPositive = nums[nums.length - 1], max = Integer.MIN_VALUE;
        for(int i = nums.length - 1; 2 <= i; i--) {
            if(0 < nums[i - 1]) {
                max = Math.max(max, nums[i - 2] * nums[i - 1] * nums[i]);
            } else {
                max = Math.max(max, nums[i - 2] * nums[i - 1] * theMaxPositive);
            }
        }

        return max;
    }

    //not the fast approach, but simple enough. found in https://leetcode.com/problems/maximum-product-of-three-numbers/discuss/126781/java-two-lines-code
    //there a faster solution, the the readability is that great
    public int twoLines(int[] nums) {
        Arrays.sort(nums);
        return Math.max(nums[0] * nums[1], nums[nums.length - 3] * nums[nums.length - 2]) * nums[nums.length - 1];
    }
}
