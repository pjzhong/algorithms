package com.pjzhong.leetcode;

import org.junit.Test;

public class TheLongestCommonSequenceTest {

  @Test
  public void test() {
    String[] aCases = {"computer", "didacticA", "tsinghua", "asdfffff", "program", "asdf",
        "JEQTKVWQLINVKZOC"};
    String[] bCases = {"cute", "advantA", "sina", "asdffff", "algorithm", "ghjk",
        "EOXXBIIMOHBOXHAR"};

    for (int i = 0; i < aCases.length; i++) {
      longestCommonSequence(aCases[i], bCases[i]);
    }
  }

  public void longestCommonSequence(String a, String b) {
    int[][] dp = new int[a.length() + 1][b.length() + 1];
    System.out.println("recursive:" + longestCommonRecursive(a, b, 0, 0, dp));
    System.out.println("dp:" + longestCommonDp(a, b));
  }

  private int longestCommonRecursive(String aStr, String bStr, int aIdx, int bIdx, int[][] dp) {
    if (aIdx >= aStr.length() || bIdx >= bStr.length()) {
      return 0;
    }

    if (dp[aIdx + 1][bIdx + 1] != 0) {
      return dp[aIdx + 1][bIdx + 1];
    }

    if (aStr.charAt(aIdx) == bStr.charAt(bIdx)) {
      dp[aIdx + 1][bIdx + 1] = 1 + longestCommonRecursive(aStr, bStr, aIdx + 1, bIdx + 1, dp);
      return dp[aIdx + 1][bIdx + 1];
    } else {
      dp[aIdx + 1][bIdx + 1] = Math.max(
          longestCommonRecursive(aStr, bStr, aIdx + 1, bIdx, dp),
          longestCommonRecursive(aStr, bStr, aIdx, bIdx + 1, dp)
      );
      return dp[aIdx + 1][bIdx + 1];
    }
  }

  private int longestCommonDp(String aStr, String bStr) {
    int[][] dp = new int[aStr.length() + 1][bStr.length() + 1];

    for (int aIdx = 1, aSize = aStr.length(); aIdx <= aSize; aIdx++) {
      for (int bIdx = 1, bSize = bStr.length(); bIdx <= bSize; bIdx++) {
        if (aStr.charAt(aIdx - 1) == bStr.charAt(bIdx - 1)) {
          dp[aIdx][bIdx] = dp[aIdx - 1][bIdx - 1] + 1;
        } else {
          dp[aIdx][bIdx] = Math.max(dp[aIdx - 1][bIdx], dp[aIdx][bIdx - 1]);
        }
      }
    }

    return dp[aStr.length()][bStr.length()];
  }

}
