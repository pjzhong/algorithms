package leetcode;

import java.util.Arrays;
import org.junit.Test;

/**
 * You are given an n x n 2D matrix representing an image.
 *
 * Rotate the image by 90 degrees (clockwise).
 *
 * Note:
 *
 * You have to rotate the image in-place, which means you have to modify the input 2D matrix
 * directly. DO NOT allocate another 2D matrix and do the rotation.
 **/
public class RotateImage {

  @Test
  public void test() {
    int[][][] tests = {
        {
            {1, 2},
            {3, 4}
        },
        {
            {5, 1, 9, 11, 123},
            {2, 4, 8, 10, 45},
            {3, 3, 6, 7, 123},
            {5, 14, 12, 16, 9},
            {9, 10, 56, 58, 13}
        },
        {
            {5, 1, 9, 11},
            {2, 4, 8, 10},
            {3, 3, 6, 7},
            {5, 14, 12, 16}
        },
        {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
        },

    };

    for (int[][] t : tests) {
      rotate(t);
      for (int[] r : t) {
        System.out.println(Arrays.toString(r));
      }
    }
  }

  /**
   * rotate it from outside to inside
   *
   * @since 2019年06月30日 16:23:53
   */
  public void rotate(int[][] matrix) {
    int SIZE = matrix.length - 1, END = matrix.length / 2;
    for (int start = 0; start <= END; start++) {
      int row = start, col = start;
      for (int down = start; down < SIZE - start; ) {
        int toCol = SIZE - row;
        int copy = matrix[col][toCol];
        matrix[col][toCol] = matrix[down][start];
        matrix[down][start] = copy;

        if (col == down && toCol == start) {
          down++;
          row = down;
          col = start;
        } else {
          row = col;
          col = toCol;
        }
      }
    }
  }
}
