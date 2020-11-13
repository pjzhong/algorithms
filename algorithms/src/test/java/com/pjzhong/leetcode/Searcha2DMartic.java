package com.pjzhong.leetcode;

/**
 Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:

 Integers in each row are sorted from left to right.
 The first integer of each row is greater than the last integer of the previous row.
 Example 1:

 Input:
 matrix = [
 [1,   3,  5,  7],
 [10, 11, 16, 20],
 [23, 30, 34, 50]
 ]
 target = 3
 Output: true
 Example 2:

 Input:
 matrix = [
 [1,   3,  5,  7],
 [10, 11, 16, 20],
 [23, 30, 34, 50]
 ]
 target = 13
 Output: false

 https://leetcode.com/problems/search-a-2d-matrix/description/
 * */
public class Searcha2DMartic {

    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0)  { return false;}

        int R = matrix.length, C = matrix[0].length;
        for(int row = 0; row < R; row++) {
            if(target <= matrix[row][C - 1]) {
                int index = binarySearch(matrix[row], target);
                return  index != -1;
            }
        }

        return false;
    }

    private int binarySearch(int[] array, int target) {
        int lo = 0, hi = array.length - 1;

        int mid = -1, compare = 0;
        while(lo <= hi) {
            mid = (lo + hi) / 2;
            compare = target - array[mid];

            if(compare < 0) {
                hi = mid - 1;
            } else if (0 < compare) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }
}
