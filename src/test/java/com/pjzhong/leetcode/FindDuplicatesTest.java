package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive),
 * prove that at least one duplicate number must exist. Assume that there is only one duplicate number,
 * find the duplicate one.
 *
 * Note:
 * You must not modify the array (assume the array is read only).
 * You must use only constant, O(1) extra space.
 * Your runtime complexity should be less than O(n^2).
 * There is only one duplicate number in the array, but it could be repeated more than once.
 *
 * COPY FROM LEETCODE
 *
 *  How to find the duplicate or remove other integers except the duplicate
 */
public class FindDuplicatesTest {

    @Test
    public void test() {
        int[][] testCases = {
                {1, 1, 1, 2, 3, 4},
                {1, 3, 3, 4, 2, 3},
                {1, 3, 4, 2, 5},
        };

        for(int[] testCase : testCases) {
            System.out.println(findDuplicate(testCase));
            System.out.println(findDuplicateByBinarySearch(testCase));
        }
    }

    /**
     * official solution
     * */
    private int findDuplicate(int[] nums) {
        int slow = nums[0], fast = nums[nums[0]];
        while(slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }

        fast = 0;
        while(fast != slow) {
            fast = nums[fast];
            slow = nums[slow];
        }

        return fast;
    }

    /**
     * At first the search space is numbers between 1 to n.
     * Each com.pjzhong.time I select a number mid (which is the one in the middle) and count all the numbers equal to or less than mid.
     * Then if the count is more than mid, the search space will be [1 mid] otherwise [mid+1 n].
     * I do this until search space is only one number.
     * */
    private int findDuplicateByBinarySearch(int[] nums) {
        int low = 1, height = nums.length - 1;
        while(low < height) {
            int mid = low + (height - low) / 2, count = 0;
            for(int i : nums) {
                if(i <= mid) { count++; }
            }

            if(count <= mid) {
                low = mid + 1;
            } else {
                height = mid;
            }

        }

        return low;
    }
}
