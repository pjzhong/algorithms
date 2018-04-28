import org.junit.Test;

/**
 Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum
 and return its sum.

 Example:

 Input: [-2,1,-3,4,-1,2,1,-5,4],
 Output: 6
 Explanation: [4,-1,2,1] has the largest sum = 6.
 Follow up:

 If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach,
 which is more subtle.

 https://leetcode.com/problems/maximum-subarray/description/
 * */
public class MaximumSubarray {

    @Test
    public void test() {
        int[][] testCases = {
                {-2,1,-3,4,-1,2,1,-5,4},
                {-2,1,-3,-4,-1,2,1,5,4},
                {1},
        };

        for(int[] testCase : testCases) {
            System.out.println(maxSubArray(testCase));
        }
    }

    //MySolution - after optimize readability
    public int maxSubArray(int[] nums) {
        int max = nums[0], prev = nums[0];
        for(int i = 1; i < nums.length; i++) {
            prev = Math.max(prev + nums[i], nums[i]);
            if(max < prev) { max = prev; }
        }

        return max;
    }
}
