package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * We have a string S of lowercase letters, and an integer array shifts.
 *
 * Call the shift of a letter, the next letter in the alphabet, (wrapping around so that 'z' becomes
 * 'a').
 *
 * For example, shift('a') = 'b', shift('t') = 'u', and shift('z') = 'a'.
 *
 * Now for each shifts[i] = x, we want to shift the first i+1 letters of S, x times.
 *
 * Return the final string after all such shifts to S are applied.
 *
 * Example 1:
 *
 * Input: S = "abc", shifts = [3,5,9] Output: "rpl" Explanation: We start with "abc". After shifting
 * the first 1 letters of S by 3, we have "dbc". After shifting the first 2 letters of S by 5, we
 * have "igc". After shifting the first 3 letters of S by 9, we have "rpl", the answer. Note:
 *
 * 1 <= S.length = shifts.length <= 20000 0 <= shifts[i] <= 10 ^ 9
 *
 * https://leetcode.com/problems/shifting-letters/description/
 */
public class ShiftingLetters {

  @Test
  public void test() {
    String[] S = {"abc", "z"};
    int[][] shifts = {{3, 5, 9}, {52}};

    for (int i = 0; i < S.length; i++) {
      System.out.println(shiftingLetters(S[i], shifts[i]));
    }
  }

  public String shiftingLetters(String S, int[] shifts) {
    char[] array = S.toCharArray();
    for (int i = shifts.length - 2; 0 <= i; i--) {
      shifts[i] = ((shifts[i] % 26) + (shifts[i + 1] % 26)) % 26;
    }

    for (int i = 0; i < array.length; i++) {
      int x = ((array[i] - 'a') + shifts[i]) % 26;
      array[i] = (char) ('a' + x);
    }

    return new String(array);
  }
}
