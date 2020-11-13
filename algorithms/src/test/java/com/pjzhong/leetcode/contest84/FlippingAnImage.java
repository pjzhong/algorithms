package com.pjzhong.leetcode.contest84;

import org.junit.Test;

import java.util.Arrays;

/**
 * Two images A and B are given, represented as binary, square matrices of the same size.
 * (A binary matrix has only 0s and 1s as values.)

 We translate one image however we choose (sliding it left, right, up, or down any number of units),
 and place it on top of the other image.
 After, the overlap of this translation is the number of positions that have a 1 in both images.

 (Note also that a translation does not include any kind of rotation.)

 What is the largest possible overlap?

 Example 1:

 Input: A = [[1,1,0],
 [0,1,0],
 [0,1,0]]
 B = [[0,0,0],
 [0,1,1],
 [0,0,1]]
 Output: 3
 Explanation: We slide A to right by 1 unit and down by 1 unit.
 Notes:

 1 <= A.length = A[0].length = B.length = B[0].length <= 30
 0 <= A[i][j], B[i][j] <= 1

 https://leetcode.com/problems/image-overlap/description/
 * */
public class FlippingAnImage {

    @Test
    public void test() {
        int[][][] testCases = {
                {{1,1,0,0}, {1,0,0,1}, {0,1,1,1}, {1,0,1,0}},
                {{0,0,0,0},{1,1,1,1}},
                {{0}},
        };

        for(int[][] test : testCases) {
            flipAndInvertImage(test);
            for(int[] t : test) {
                System.out.println(Arrays.toString(t));
            }
            System.out.println();
        }
    }

    public int[][] flipAndInvertImage(int[][] A) {
        for(int[] a : A) {
            flip(a);
            invert(a);
        }
        return A;
    }

    private void flip(int[] a) {
        int temp = 0, lo = 0, hi = a.length - 1;

        while(lo < hi) {
            temp = a[lo];
            a[lo] = a[hi];
            a[hi] = temp;
            lo++;hi--;
        }
    }

    private void invert(int[] a) {
        for(int i = 0; i < a.length; i++) {
            //a[i] = a[i] == 0 ? 1 : 0;
            a[i] ^= 1;
        }
    }
}
