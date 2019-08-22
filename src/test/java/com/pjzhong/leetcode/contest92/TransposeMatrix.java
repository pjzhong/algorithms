package com.pjzhong.leetcode.contest92;

import org.junit.Test;

import java.util.Arrays;

/**
 Given a matrix A, return the transpose of A.

 The transpose of a matrix is the matrix flipped over it's main diagonal, switching the row and column indices of the
 matrix.



 Example 1:

 Input: [[1,2,3],[4,5,6],[7,8,9]]
 Output: [[1,4,7],[2,5,8],[3,6,9]]
 Example 2:

 Input: [[1,2,3],[4,5,6]]
 Output: [[1,4],[2,5],[3,6]]


 Note:

 1 <= A.length <= 1000
 1 <= A[0].length <= 1000

 problem:https://leetcode.com/problems/transpose-matrix/description/
 * */
public class TransposeMatrix {

    @Test
    public void test() {
        int[][][]  test = {
                {
                        {1,2,3},
                        {4,5,6}
                },
                {
                        {1},
                        {2}
                }
        };

        for(int[][] t : test) {
            for(int[] i : transpose(t)) {
                System.out.println(Arrays.toString(i));
            }
        }
    }

    public int[][] transpose(int[][] A) {
        final int ROW = A.length, COL = A[0].length;
        int[][] result = new int[COL][ROW];

        for(int c = 0; c < COL; c++) {
            for(int r = 0; r < ROW; r++) {
                result[c][r] = A[r][c];
            }
        }
        return result;
    }
}
