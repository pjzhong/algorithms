package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon.
 The dungeon consists of M x N rooms laid out in a 2D grid. Our valiant knight (K) was initially positioned in
 the top-left room and must fight his way through the dungeon to rescue the princess.

 The knight has an initial health point represented by a positive integer. If at any point his health point drops to
 0 or below, he dies immediately.

 Some of the rooms are guarded by demons, so the knight loses health (negative integers) upon entering these rooms;
 other rooms are either empty (0's) or contain magic orbs that increase the knight's health (positive integers).

 In order to reach the princess as quickly as possible, the knight decides to move only rightward or downward in each
 step.



 Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.

 For example, given the dungeon below, the initial health of the knight must be at least 7 if he follows the optimal
 path RIGHT-> RIGHT -> DOWN -> DOWN.

 -2 (K)	-3	3
 -5	   -10	1
 10	   30	-5 (P)


 Note:

 The knight's health has no upper bound.
 Any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room where the
 princess is imprisoned.

 https://leetcode.com/problems/dungeon-game/description/
 * */
public class DungeonGame {

    @Test
    public void test() {
        int[][][] testCases = {
                {{0,-5},{0,0}},
                {{0,10},{30,40}},
                {{0,0,0},{1,1,-1}},
                {{1,-2,3},{2,-2,-2}},
                {
                        {1, -3, 3},
                        {0,-2, 0},
                        {-3,-3,-3},
                },
                {
                        {3,0, -3},
                        {-3,-2, -2},
                        {3,1,-3},
                },
                {
                        {-9,-10, -7},
                        {-5,-8, -14},
                        {-1,-32,-3},
                },
                {
                        {-2,-3,3},
                        {-5,-10,1},
                        {10,30,-5},
                },
                {
                        {2,-3,3},
                        {5,10,1},
                        {10,30,-5},
                }
        };

        for(int[][] t : testCases) {
            System.out.println(calculateMinimumHP(t));
        }
    }

    //Time limit exceeded, but correct
    public int calculateMinimumHPBFS(int[][] dungeon) {
        final int ROW = dungeon.length, COL = dungeon[0].length;
        Queue<Step> steps = new LinkedList<>();
        steps.add(doStep(new Step(), dungeon));

        Step step;
        int min = Integer.MAX_VALUE;
        while(!steps.isEmpty()) {
            step = steps.poll();
            if (step.r == ROW - 1 && step.c == COL - 1) {
                min = Math.min(min, step.init);
            } else {
                if (step.r + 1 < ROW) { steps.add(doStep(new Step(step, step.r + 1, step.c), dungeon)); }
                if (step.c + 1 < ROW) { steps.add(doStep(new Step(step, step.r, step.c + 1), dungeon)); }
            }
        }

        return min;
    }

    private Step doStep(Step step, int[][] dungeon) {
        return doStep(step, dungeon[step.r][step.c]);
    }

    private Step doStep(Step step, int cost) {
        step.upper += cost;
        if(step.upper < 0) {
            step.init += Math.abs(step.upper);
            step.upper = 0;
        }

        return step;
    }

    public static class Step {
        int r = 0, c = 0;
        int init = 1;
        int upper = 0;

        public Step() {}

        public Step(int init, int upper) {
            this.init = init;
            this.upper = upper;
        }

        public Step(Step step, int row, int col) {
            this.r = row;this.c = col;
            this.init = step.init;
            this.upper = step.upper;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("init=").append(init);
            sb.append(", upper=").append(upper);
            sb.append('}');
            return sb.toString();
        }
    }

    //always miss something to solve this question by yourself.
    //What is the missing?
    //bottom up solution, optimize from https://leetcode.com/problems/dungeon-game/discuss/132489/Java-Bottom-up-DP-with-Detailed-Explanations
    //Why not try to solve this using recursive and memorization?
    public int calculateMinimumHP(int[][] dungeon) {
        final int ROW = dungeon.length - 1, COL = dungeon[0].length - 1;
        int[] dp = new int[COL + 1];

        dp[COL] = dungeon[ROW][COL] <= 0 ? -dungeon[ROW ][COL] + 1 : 1;
        //This algorithm use a reverse way to record threats or power-ups
        for(int r = ROW; 0 <= r; r--) {
            for(int c = COL; 0 <= c; c--) {
                if(r != ROW && c != COL) {
                    dp[c] = Math.max(1, Math.min(dp[c], dp[c + 1]) - dungeon[r][c]);
                }  else if(r != ROW) {
                    dp[c] = Math.max(1, dp[c] - dungeon[r][c]);
                }  else if(c != COL) {//initialization
                    dp[c] = Math.max(1, dp[c + 1] - dungeon[r][c]);
                }
            }
        }

        return dp[0];
    }


}
