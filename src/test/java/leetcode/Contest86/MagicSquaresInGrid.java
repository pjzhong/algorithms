package leetcode.contest86;


import org.junit.Test;

/**
 A 3 x 3 magic square is a 3 x 3 grid filled with distinct numbers from 1 to 9 such that each row,
 column, and both diagonals all have the same sum.

 Given an N x N grid of integers, how many 3 x 3 "magic square" subgrids are there?  (Each subgrid is contiguous).



 Example 1:

 Input: [[4,3,8,4],
 [9,5,1,9],
 [2,7,6,2]]
 Output: 1
 Explanation:
 The following subgrid is a 3 x 3 magic square:
 438
 951
 276

 while this one is not:
 384
 519
 762

 In total, there is only one magic square inside the given grid.
 Note:

 1 <= grid.length = grid[0].length <= 10
 0 <= grid[i][j] <= 15

 https://leetcode.com/contest/weekly-contest-86/problems/magic-squares-in-grid/
 * */
public class MagicSquaresInGrid {

    @Test
    public void test() {
        int[][][] testCases = {
                {
                        {9,0,8,1,6},
                        {2,4,3,5,7},
                        {4,3,4,9,2},
                        {2,4,5,6,1},
                        {9,8,0,7,8}
                },
                {
                        {4,3,8,4},
                        {9,5,1,9},
                        {2,7,6,2}
                },
                {
                        {1,3,5},
                        {1,6,11},
                        {7,9,2},
                },
        };

        for(int[][] t : testCases) {
            System.out.println(numMagicSquaresInside(t));
        }
    }

    public int numMagicSquaresInside(int[][] grid) {
        int R = grid.length, C = grid[0].length;
        int result = 0;
        for(int r = 0; r + 2 < R; r++) {
            for(int c = 0; c + 2 < C; c++) {
                if(0 < grid[r][c] && grid[r][c] < 10) {
                    if(isMagic(grid, r, c)) { result++; }
                }
            }
        }

        return result;
    }

    private boolean isMagic(int[][] grid, int r, int c) {
        for(int i = 0; i <= 2;i++) {
            for(int j = 0; j <= 2;j++) {
                if(10 <= grid[r + i][c + j] || grid[r + i][c + j] <= 0) { return false;}
            }
        }

        int sum = grid[r][c] + grid[r][c + 1] + grid[r][c + 2];
        for(int i = 0; i <= 2; i++) {
            if(sum != grid[r][c + i] + grid[r + 1][c + i] + grid[r + 2][c + i] ||
               sum != grid[r + i][c] + grid[r + i][c + 1] + grid[r + i][c + 2]) {
                return false;
            }
        }


        return (grid[r][c] + grid[r + 1][c + 1] + grid[r + 2][c + 2] == sum) && (grid[r][c + 2] + grid[r + 1][c + 1] + grid[r + 2][c] == sum);
    }
}
