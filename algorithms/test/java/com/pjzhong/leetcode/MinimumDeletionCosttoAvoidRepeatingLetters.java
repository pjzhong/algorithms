package com.pjzhong.leetcode;

import org.junit.Test;

/**
 * Given a string s and an array of integers cost where cost[i] is the cost of deleting the
 * character i in s.
 *
 * Return the minimum cost of deletions such that there are no two identical letters next to each
 * other.
 *
 * Notice that you will delete the chosen characters at the same time, in other words, after
 * deleting a character, the costs of deleting other characters will not change.
 *
 * @link https://leetcode.com/problems/minimum-deletion-cost-to-avoid-repeating-letters/
 **/
public class MinimumDeletionCosttoAvoidRepeatingLetters {

  @Test
  public void test() {
    String[] tests = {
        "abaac",
        "abc",
        "aabaa",
        "aaaaa",
        "aaaaabbbbb",
        "bbbaaa",
    };

    int[][] costs = {
        {1, 2, 3, 4, 5},
        {1, 2, 3},
        {1, 2, 3, 4, 1},
        {1, 2, 3, 4, 1},
        {1, 2, 3, 4, 1, 4, 4, 1, 2, 2},
        {4, 9, 3, 8, 8, 9}
    };

    for (int i = 0; i < tests.length; i++) {
      System.out.println(minCost(tests[i], costs[i]));
    }
  }

  public int minCost(String s, int[] cost) {
    int result = 0;
    int length = s.length();
    int maxIdx = 0, start = 0;
    for (int i = 1; i < length; i++) {
      if (s.charAt(i) == s.charAt(i - 1)) {
        if (cost[maxIdx] < cost[i]) {
          maxIdx = i;
        }
      } else {
        for (int j = start, size = i - 1; j <= size; j++) {
          result += j != maxIdx ? cost[j] : 0;
        }
        maxIdx = i;
        start = i;
      }
    }

    for (int i = start; i < length; i++) {
      result += i != maxIdx ? cost[i] : 0;
    }

    return result;
  }
}
