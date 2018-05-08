import org.junit.Test;

import java.util.Arrays;

/**
 Write a program to solve a Sudoku puzzle by filling the empty cells.

 A sudoku solution must satisfy all of the following rules:

 Each of the digits 1-9 must occur exactly once in each row.
 Each of the digits 1-9 must occur exactly once in each column.
 Each of the the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.

 https://leetcode.com/problems/sudoku-solver/description/
 * */
public class SudokuSolver {

    private final int MAX = 9;
    private boolean solving = true;
    private boolean[][] ROW = new boolean[9][9];
    private boolean[][] COL = new boolean[9][9];
    private boolean[][] GRID = new boolean[9][9];

    @Test
    public void test() {
        char[][][] testCases = {
                {
                        {'5','3','.','.','7','.','.','.','.'},
                        {'6','.','.','1','9','5','.','.','.'},
                        {'.','9','8','.','.','.','.','6','.'},
                        {'8','.','.','.','6','.','.','.','3'},
                        {'4','.','.','8','.','3','.','.','1'},
                        {'7','.','.','.','2','.','.','.','6'},
                        {'.','6','.','.','.','.','2','8','.'},
                        {'.','.','.','4','1','9','.','.','5'},
                        {'.','.','.','.','8','.','.','7','9'},
                }
        };

        for(char[][] board : testCases) {
            solveSudoku(board);
            for(char[] b : board) {
                System.out.println(Arrays.toString(b));
            }
            System.out.println();
        }
    }

    public void solveSudoku(char[][] board) {
        for(int i = 0; i < MAX; i++) {
            for(int j = 0; j < MAX; j++) {
                if(Character.isDigit(board[i][j])) {
                    int index =  (board[i][j] - '0') - 1;
                    ROW[i][index] = true;
                    COL[j][index] = true;
                    GRID[grid(i, j)][index] = true;
                }
            }
        }

        solve(board, 0, 0);
    }

    private void solve(char[][] board, int row, int col) {
        if(row == MAX) { solving = false;}
        else if(col == MAX) { solve(board, row + 1, 0);}
        else if(board[row][col] != '.') { solve(board, row, col + 1);}
        else {
            int g = grid(row, col);
            for (int i = 0; i < MAX; i++) {
                if (!ROW[row][i] && !COL[col][i] && !GRID[g][i]) {
                    ROW[row][i] = COL[col][i] = GRID[g][i] = true;
                    board[row][col] = (char) ('0' + (i + 1));
                    solve(board, row, col + 1);
                    if (solving) {
                        board[row][col] = '.';
                        ROW[row][i] = COL[col][i] = GRID[g][i] = false;
                    } else { break; }
                }
            }
        }
    }

    private int grid(int row, int col) {
        return (row / 3 )  * 3 + (col / 3);
    }
}
