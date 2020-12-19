package com.pjzhong.leetcode;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * Given four lists A, B, C, D of integer values, compute how many tuples (i, j, k, l) there are
 * such that A[i] + B[j] + C[k] + D[l] is zero.
 *
 * To make problem a bit easier, all A, B, C, D have same length of N where 0 ≤ N ≤ 500. All
 * integers are in the range of -228 to 228 - 1 and the result is guaranteed to be at most 231 - 1.
 *
 * Example:
 *
 * <p>Input:</p>
 * <p>A = [ 1, 2]</p>
 * <p>B = [-2,-1]</p>
 * <p>C = [-1, 2]</p>
 * <p>D = [ 0, 2]</p>
 *
 * Output: 2
 *
 * <p>Explanation:</p>
 * <p>The two tuples are:</p>
 * <p>1. (0, 0, 0, 1) -> A[0] + B[0] + C[0] + D[1] = 1 + (-2) + (-1) + 2 = 0</p>
 * <p>2. (1, 1, 0, 0) -> A[1] + B[1] + C[0] + D[0] = 2 + (-1) + (-1) + 0 = 0</p>
 *
 * @link https://leetcode.com/problems/4sum-ii/
 * @since 2020年12月19日 19:49:45
 **/
public class Sum4II {

  @Test
  public void test() {
    int c = fourSumCount(new int[]{1, 2}, new int[]{-2, -1}, new int[]{-1, 2}, new int[]{0, 2});
    System.out.println(c);
  }

  public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
    int result = 0;
    Map<Integer, Integer> count = new HashMap<>();

    for (int c : C) {
      for (int d : D) {
        count.merge(c + d, 1, Integer::sum);
      }
    }

    for (int a : A) {
      for (int b : B) {
        result += count.getOrDefault(-(a + b), 0);
      }
    }
    return result;
  }

}
