package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.Arrays;

/**
 * 设计一个就地算法shift(int[] A, int n, int k)， 在O(n）时间内将数组A[0,n)
 * 中的元素整体循环左移K。例如，数组A[] = {1,2,3,4,5,6}经shift(A, 6, 2)之后，
 * 有A[] = {3,4,5,6,1,2}
 *
 * 清华大学-计算机系列教材
 *
 * 数据结构(C++语言版)，第三版
 */
public class ShiftTest {

    @Test
    public void test() {
        int[][] testCases = {
                {1,2,3,4,5,6},
                {1,3,1},
                {1,3,1,3,1},
                {1,1,1},
                {1,1,1,1},
                {2,5,10,1},
                {2,1,1,2},
                {1000,1,1,10},
                {2,1,1,2,1000,1,1,100},
                {2,1,1,2,1000,1,1,100,10000, 1, 1, 100},
                {226,174,214,16,218,48,153,131,128,17,157,142,88,43,37,157,43,221,191,68,206,23,225,82,54,118,111,46,
                        80,49,245,63,25,194,72,80,143,55,209,18,55,122,65,66,177,101,63,201,172,130,103,225,142,46,86,185,62,
                        138,212,192,125,77,223,188,99,228,90,25,193,211,84,239,119,234,85,83,123,120,131,203,219,10,
                        82,35,120,180,249,106,37,169,225,54,103,55,166,124}
        };

        for(int[] testCase : testCases) {
            shift(testCase, 2, 2);
            System.out.println(Arrays.toString(testCase));
        }
    }

    private void shift(int[] A, int n, int k) {
        k = k % (n);
        reverse(A, 0, k + 1);
        reverse(A, k + 2, A.length - 1);
        reverse(A, 0, A.length - 1);
    }

    private void reverse(int[] a, int lo, int hi) {
        while(lo < hi) {
            a[lo] = a[lo] + a[hi];
            a[hi]= a[lo] - a[hi];
            a[lo]= a[lo] - a[hi];
            lo++;hi--;
        }
    }
}
