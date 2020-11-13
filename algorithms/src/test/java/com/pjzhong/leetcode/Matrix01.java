package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.*;

/**
 Given a matrix consists of 0 and 1, find the distance of the nearest 0 for each cell.

 The distance between two adjacent cells is 1.
 Example 1:
 Input:

 0 0 0
 0 1 0
 0 0 0
 Output:
 0 0 0
 0 1 0
 0 0 0
 Example 2:
 Input:

 0 0 0
 0 1 0
 1 1 1
 Output:
 0 0 0
 0 1 0
 1 2 1
 Note:
 The number of elements of the given matrix will not exceed 10,000.
 There are at least one 0 in the given matrix.
 The cells are adjacent in only four directions: up, down, left and right.

 https://leetcode.com/problems/01-matrix/description/
 * */
public class Matrix01 {

    @Test
    public void test() {
        int[][][] matixs = {
                {
                        {0,1,1,0,0},
                        {0,1,1,0,0},
                        {0,1,0,0,1},
                        {1,1,1,1,0},
                        {1,0,0,1,0},
                },
                {
                        {0,0,0},
                        {0,1,0},
                        {0,0,0},
                },
                {
                        {0,0,0},
                        {0,1,0},
                        {1,1,1},
                },
        };

        for(int[][] matrix : matixs) {
            for(int[] i:  updateMatrix(matrix)) {
                System.out.println(Arrays.toString(i));
            }
            System.out.println();
        }
    }

    private int R, C, SLAT;
    private Queue<Step> queue = new LinkedList<>();
    private Set<Integer> set = new HashSet<>();

    public int[][] updateMatrix(int[][] matrix) {
        R = matrix.length; C = matrix[0].length;SLAT = Math.max(R, C);

        for(int r = 0; r < R; r++) {
            for(int c = 0; c < C; c++) {
                if(matrix[r][c] != 0) {
                    matrix[r][c] = min(matrix, r, c);
                }
            }
        }

        return matrix;
    }


    private int  min(int[][] matrix, int r, int c) {
        queue.clear();set.clear();
        Step s = new Step(r, c, 0);
        queue.add(s);set.add(s.hash);

        Step step;
        while(!queue.isEmpty()) {
            step = queue.poll();
            if(matrix[step.r][step.c] == 0) { return step.step; }
            step.up();
            step.down();
            step.forward();
            step.backward();
        }
        return matrix[r][c];
    }

    private class Step {
        int r, c, step, hash;

        public Step(int r, int c, int step) {
            this.r = r;
            this.c = c;
            this.step = step;
            this.hash = r * SLAT + c;
        }

        private void forward() {
            if(c + 1 < C) {
                go(new Step(r, c + 1, step + 1));
            }
        }

        private void backward() {
            if(0 <= c - 1) {
                go(new Step(r, c - 1, step + 1));
            }
        }

        private void down() {
            if(r + 1 < R) {
                go(new Step(r + 1, c, step + 1));
            }
        }

        private void up() {
            if(0 <= r - 1) {
                go(new Step(r - 1, c, step + 1));
            }
        }

        private void go(Step step) {
            if(set.add(step.hash)) {
                queue.add(step);
            }
        }
    }

}
