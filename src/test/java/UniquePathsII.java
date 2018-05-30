import org.junit.Test;

import java.util.Arrays;

/**
 A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

 The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right
 corner of the grid (marked 'Finish' in the diagram below).

 Now consider if some obstacles are added to the grids. How many unique paths would there be?



 An obstacle and empty space is marked as 1 and 0 respectively in the grid.

 Note: m and n will be at most 100.

 Example 1:

 Input:
 [
 [0,0,0],
 [0,1,0],
 [0,0,0]
 ]
 Output: 2
 Explanation:
 There is one obstacle in the middle of the 3x3 grid above.
 There are two ways to reach the bottom-right corner:
 1. Right -> Right -> Down -> Down
 2. Down -> Down -> Right -> Right

 https://leetcode.com/problems/unique-paths-ii/description/
 * */
public class UniquePathsII {

    @Test
    public void test() {
        int[][][] testCases = {
                {{1}},
                {{0},{1}},
                {{0},{0}},
                {{0,0},{1,0}},
                {{0,1},{0,0}},
                {
                        {1,0,0},
                        {0,0,0},
                        {0,0,0},
                },
                {
                        {0,0,0},
                        {0,0,0},
                        {0,0,1},
                },
                {
                        {0,0,0},
                        {1,0,0},
                        {1,0,0},
                },
                {
                        {0,0,0},
                        {1,1,1},
                        {0,0,0},
                },
                {
                        {0,0,0},
                        {0,0,1},
                        {0,0,0},
                },
                {
                        {0,0,0},
                        {0,1,0},
                        {0,0,0},
                },
                {
                        {0,1,0},
                        {1,0,0},
                        {0,0,0},
                },
                {
                        {0,0,0},
                        {0,0,0},
                        {1,0,0},
                },
                {
                        {0,0,0},
                        {0,0,0},
                        {0,0,0},
                        {0,0,0},
                        {0,0,0},
                        {0,0,0},
                        {0,1,0},
                },
        };

        for(int[][] t : testCases) {
            for(int[] ints : t) { System.out.println(Arrays.toString(ints));}
            System.out.println(uniquePathsWithObstacles(t));
        }
    }

    //Please, get it right first
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid == null || obstacleGrid.length == 0) { return 0;}
        final int ROW = obstacleGrid.length, COL = obstacleGrid[0].length;
        if(obstacleGrid[ROW - 1][COL - 1] == 1 || obstacleGrid[0][0] == 1) { return 0;}

        int[][] dp = new int[ROW][COL];dp[0][0] = 1;
        for(int r = 0; r < ROW; r++) {
            for(int c = 0; c < COL; c++) {
                if(obstacleGrid[r][c] == 0) {
                    if(0 < r && 0 < c) {
                        dp[r][c] = dp[r - 1][c] + dp[r][c - 1];
                    } else if(0 < r) {
                        dp[r][c] = dp[r - 1][c];
                    } else if(0 < c) {
                        dp[r][c] = dp[r][c - 1];
                    }
                }
            }
        }

        return dp[ROW - 1][COL - 1];
    }
}
