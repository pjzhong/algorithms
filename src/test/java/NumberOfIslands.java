/**
 Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water
 and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid
 are all surrounded by water.

 Example 1:

 Input:
 11110
 11010
 11000
 00000

 Output: 1
 Example 2:

 Input:
 11000
 11000
 00100
 00011

 Output: 3

 https://leetcode.com/problems/number-of-islands/description/
 * */
public class NumberOfIslands {

    public int numIslands(char[][] grid) {
        if(grid == null || grid.length == 0) { return 0;}
        int R = grid.length, C = grid[0].length;

        int count = 0;
        for(int row = 0; row < R; row++) {
            for(int col = 0; col < C; col++) {
                if(grid[row][col] == '1') {
                    count++;
                    marked(grid, row, col, R, C);
                }
            }
        }

        return count;
    }

    public void marked(char[][] grid, int row, int col, int R, int C) {
        if(0 <= row && row < R && 0 <= col && col < C ) {
            if(grid[row][col] == '1') {
                grid[row][col] = '0';

                marked(grid, row - 1, col, R, C);
                marked(grid, row + 1, col, R, C);
                marked(grid, row, col - 1, R, C);
                marked(grid, row, col + 1, R, C);
            }
        }
    }
}
