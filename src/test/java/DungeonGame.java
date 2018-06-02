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

    //Dynamic programming, wrong answer
    public int calculateMinimumHP(int[][] dungeon) {
        final int ROW = dungeon.length, COL = dungeon[0].length;
        Step[] steps = new Step[COL]; doStep(steps[0] = new Step(), dungeon[0][0]);
        for(int i = 1; i < COL; i++) {
            doStep(steps[i] = new Step(steps[i - 1].init, steps[i - 1].upper), dungeon[0][i]);
        }

        for(int r = 1; r < ROW; r++) {
            for(int c = 0; c < COL; c++) {
                if(0 < c && steps[c - 1].init <= steps[c].init ) {
                    steps[c].upper = (steps[c - 1].init == steps[c].init) ?
                            Math.max(steps[c - 1].upper, steps[c].upper) : steps[c - 1].upper;
                    steps[c].init  = steps[c - 1].init;
                }
                doStep(steps[c], dungeon[r][c]);
            }
        }
        return steps[COL - 1].init;
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
}
