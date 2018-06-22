package leetcode;

import org.junit.Test;

import java.util.Arrays;

/**
 Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

 Note:

 The number of elements initialized in nums1 and nums2 are m and n respectively.
 You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional
 elements from nums2.

 Example:

 Input:
 nums1 = [1,2,3,0,0,0], m = 3
 nums2 = [2,5,6],       n = 3

 Output: [1,2,2,3,5,6]

 https://leetcode.com/problems/merge-sorted-array/description/
 * */
public class MergeSortedArray {

    @Test
    public void test() {
        int[][][] testCase = {
                {{1,2,3,0,0,0}, {2,5,6}},
                {{1,2,3,0,0,0,0,0,0,0,0,0}, {2,5,6,7,8,9,10}},
                {{1,2,3,0,0,0,0,0,0,0,0,0}, {0}},
                {{1,2,3,4,5,6,7,8,9,0,0,0}, {4,0,0,0,0}},
                {{4,0,0,0,0,0,0,0,0,0,0,0,0,0,}, {1,2,3,4,5,6,7,8,9,0,0,0,0,0}},
                {{4,5,0,0,0,0,0,0,0,0,0,0,0,0,}, {1,2,3,4,5,6,7,8,9,0,0,0,0,0}},
                {{1,2,3,4,0,0,0,0,0,0,0,0,0,0,}, {1,2,3,4,5,6,7,8,9,0,0,0,0,0}},
        };
        int[][] mn = {
                {3,3},
                {3,7},
                {3,0},
                {9,1},
                {1,9},
                {2,9},
                {4,9},
        };

        for(int i = 0; i < testCase.length; i++) {
            merge(testCase[i][0],  mn[i][0], testCase[i][1], mn[i][1]);
            System.out.println(Arrays.toString(testCase[i][0]));
        }
    }

    //do it in O(n) time
    //tips:do it from right to left
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        for(int i = m, j = 0; j < n; i++, j++) {
            nums1[i] = nums2[j];
        }
        Arrays.sort(nums1, 0, m + n);
    }
}
