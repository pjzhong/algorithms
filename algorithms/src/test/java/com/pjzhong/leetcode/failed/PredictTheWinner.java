package com.pjzhong.leetcode.failed;


import org.junit.Test;

/**
 * Given an array of scores that are non-negative integers. Player 1 picks one of the numbers from
 * either end of the array followed by the player 2 and then player 1 and so on. Each time a player
 * picks a number, that number will not be available for the next player. This continues until all
 * the scores have been chosen. The player with the maximum score wins.
 *
 * Given an array of scores, predict whether player 1 is the winner. You can assume each player
 * plays to maximize his score.
 *
 * Example 1:
 *
 * <p>Input: [1, 5, 2]</p>
 * <p>Output: False</p>
 * <p>Explanation: Initially, player 1 can choose between 1 and 2.</p>
 * <p>If he chooses 2 (or 1), then player 2 can choose from 1 (or 2) and 5. If player 2 chooses 5,
 * then player 1 will be left with 1 (or 2).</p>
 * <p>So, final score of player 1 is 1 + 2 = 3, and player 2 is 5.</p>
 * <p>Hence, player 1 will never be the winner and you need to return False.</p>
 *
 * @link https://leetcode.com/problems/predict-the-winner/
 * @since 2021年01月17日 09:57:33
 **/
public class PredictTheWinner {

  @Test
  public void test() {
    int[][] tests = {
        {1, 5, 2},
        {1, 5, 233, 7},
    };

    for (int[] t : tests) {
      System.out.println(PredictTheWinner(t));
    }
  }


  public boolean PredictTheWinner(int[] nums) {
    //玩家1先走，结果大于等于0就是玩加1赢
    return winner(nums, 0, nums.length - 1) >= 0;
  }

  //可以简化至2选1来看，然后各玩家选择对自己最有利的结果
  public int winner(int[] nums, int s, int e) {
    if (s == e) {
      return nums[s];
    }
    int a = nums[s] - winner(nums, s + 1, e);
    int b = nums[e] - winner(nums, s, e - 1);
    return Math.max(a, b);
  }


  //可以简化至2选1来看，然后各玩家选择对自己最有利的结果
  public int winner(int[] nums, int s, int e, int turn, Integer[][] memo) {
    if (s == e) {
      return turn * nums[s];
    }
    if (memo[s][e] != null) {
      return memo[s][e];
    }
    int a = turn * nums[s] + winner(nums, s + 1, e, -turn, memo);
    int b = turn * nums[e] + winner(nums, s, e - 1, -turn, memo);
    memo[s][e] = turn * Math.max(turn * a, turn * b);
    return memo[s][e];
  }

}
