package com.pjzhong.leetcode;

import org.junit.Test;

import java.util.*;

/**
 * On a 2x3 board, there are 5 tiles represented by the integers 1 through 5, and an empty square
 * represented by 0.
 *
 * A move consists of choosing 0 and a 4-directionally adjacent number and swapping it.
 *
 * The state of the board is solved if and only if the board is [[1,2,3],[4,5,0]].
 *
 * Given a puzzle board, return the least number of moves required so that the state of the board is
 * solved. If it is impossible for the state of the board to be solved, return -1.
 *
 * Examples:
 *
 * Input: board = [[1,2,3],[4,0,5]] Output: 1 Explanation: Swap the 0 and the 5 in one move. Input:
 * board = [[1,2,3],[5,4,0]] Output: -1 Explanation: No number of moves will make the board solved.
 * Input: board = [[4,1,2],[5,0,3]] Output: 5 Explanation: 5 is the smallest number of moves that
 * solves the board. An example path: After move 0: [[4,1,2],[5,0,3]] After move 1:
 * [[4,1,2],[0,5,3]] After move 2: [[0,1,2],[4,5,3]] After move 3: [[1,0,2],[4,5,3]] After move 4:
 * [[1,2,0],[4,5,3]] After move 5: [[1,2,3],[4,5,0]] Input: board = [[3,2,4],[1,5,0]] Output: 14
 * Note:
 *
 * board will be a 2 x 3 array as described above. board[i][j] will be a permutation of [0, 1, 2, 3,
 * 4, 5].
 *
 * https://leetcode.com/problems/sliding-puzzle/description/
 */
public class SlidingPuzzle {

  @Test
  public void test() {
    int[][][] testCases = {
        {{1, 2, 3}, {4, 5, 0}},
        {{1, 2, 3}, {4, 0, 5}},
        {{4, 1, 2}, {5, 0, 3}},
        {{1, 2, 3}, {5, 4, 0}},
        {{3, 2, 4}, {1, 5, 0}},
        {{0, 5, 4}, {3, 2, 1}}
    };

    for (int[][] b : testCases) {
      System.out.println(slidingPuzzle(b));
    }
  }

  private static Puzzle result = new Puzzle(new int[][]{{1, 2, 3}, {4, 5, 0}}, 0);

  public int slidingPuzzle(int[][] board) {
    LinkedList<Puzzle> queue = new LinkedList<>();
    Set<Integer> hashCodes = new HashSet<>();

    Puzzle start = new Puzzle(board, 0);
    if (start.hash() == result.hash()) {
      return 0;
    } else {
      hashCodes.add(start.hash());
    }

    queue.add(start);
    int[][] dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    while (!queue.isEmpty()) {
      Puzzle p = queue.removeFirst();
      for (int[] d : dir) {
        Puzzle r = p.move(p.row + d[0], p.col + d[1]);
        if (r != null && hashCodes.add(r.hash())) {
          if (result.hash() == r.hash()) {
            return r.count;
          } else {
            queue.addLast(r);
          }
        }
      }
    }

    return -1;
  }

  private static class Puzzle {

    int[][] board;
    int row, col, count;
    private Integer hash = null;

    public Puzzle(int[][] board, int row, int col, int count) {
      this.board = board;
      this.row = row;
      this.col = col;
      this.count = count;
    }

    public Puzzle(int[][] board, int move) {
      this.board = board;
      this.count = move;
      for (int r = 0; r < board.length; r++) {
        for (int c = 0; c < board[r].length; c++) {
          if (board[r][c] == 0) {
            row = r;
            col = c;
          }
        }
      }
    }

    private Puzzle move(int r, int c) {
      if (0 <= r && r < 2 && 0 <= c & c < 3) {
        return doMove(r, c);
      }
      return null;
    }

    private Puzzle doMove(int r, int c) {
      int[][] b = new int[2][3];
      for (int i = 0; i < b.length; i++) {
        for (int j = 0; j < b[i].length; j++) {
          b[i][j] = this.board[i][j];
        }
      }

      b[row][col] += b[r][c];
      b[r][c] = b[row][col] - b[r][c];
      b[row][col] = b[row][col] - b[r][c];

      return new Puzzle(b, r, c, count + 1);
    }

    public int hash() {
      if (hash == null) {
        hash = 1;
        for (int[] b : board) {
          for (int n : b) {
            //Copy from Arrays.hashCode(int[]), this method can hash evenly. try to use permutation  to test it/
            hash = 31 * hash + n;
          }
        }
      }

      return hash;
    }
  }
}
