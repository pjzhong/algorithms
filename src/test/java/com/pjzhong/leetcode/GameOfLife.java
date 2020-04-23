package com.pjzhong.leetcode;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * According to the Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular
 * automaton devised by the British mathematician John Horton Conway in 1970."
 *
 * Given a board with m by n cells, each cell has an initial state live (1) or dead (0). Each cell
 * interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four
 * rules (taken from the above Wikipedia article):
 *
 * <p>Any live cell with fewer than two live neighbors dies, as if caused by under-population.</p>
 * <p>Any live cell with two or three live neighbors lives on to the next generation.</p>
 * <p> Any live cell with more than three live neighbors dies, as if by over-population.</p>
 * <p>Any dead cell with exactly three live neighbors becomes a live cell, as if by
 * reproduction.</p>
 *
 * <p>Write a function to compute the next state (after one update) of the board given its current
 * state. The next state is created by applying the above rules simultaneously to every cell in the
 * current state, where births and deaths occur simultaneously.</p>
 *
 * @link https://leetcode.com/problems/game-of-life/
 * @since 2020年04月23日 00:08:38
 **/
public class GameOfLife {

  @Test
  public void test() {
    int[][][] tests = {
        {
            {0, 1, 0},
            {0, 0, 1},
            {1, 1, 1},
            {0, 0, 0}
        },
        {
            {0, 1, 0, 1, 0, 1, 0, 0, 1},
            {0, 0, 1, 0, 0, 0, 1, 0, 0},
            {1, 1, 0, 0, 1, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0, 1, 0, 0, 1},
            {0, 0, 0, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 1, 0, 1, 1},
            {0, 1, 0, 0, 0, 0, 1, 0, 0}
        }
    };

    for (int[][] game : tests) {
      gameOfLife(game);

      for (int[] g : game) {
        System.out.println(Arrays.toString(g));
      }

      System.out.println();
    }
  }

  @Test
  public void ranDom() throws InterruptedException {
    int ROW = 20, COL = 70;
    int[][] game = new int[ROW][COL];

    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int i = 0; i < ROW; i++) {
      for (int j = 0; j < COL; j++) {
        game[i][j] = random.nextInt(0, 2);
      }
    }

    for (int i = 0, size = ROW * COL; i < size; i++) {
      gameOfLife(game);

      for (int[] g : game) {
        for (int c : g) {
          if (c == 1) {
            System.out.format("\033[31m%s ", c);
          } else {
            System.out.format("\033[30m%s ", c);
          }
        }
        System.out.println();
      }

      System.out.println();

      TimeUnit.SECONDS.sleep(1);
    }
  }

  public void gameOfLife(int[][] board) {
    int ROW = board.length, COL = board[0].length;
    for (int i = 0; i < ROW; i++) {
      for (int j = 0; j < COL; j++) {
        int live = count(i, j, ROW, COL, board);
        int cel = board[i][j];
        if (cel == 0 && live == 3) {
          board[i][j] = 2;
        } else if (cel == 1 && (live < 2 || 3 < live)) {
          board[i][j] = -1;
        }
      }
    }

    for (int i = 0; i < ROW; i++) {
      for (int j = 0; j < COL; j++) {
        int cel = board[i][j];
        if (cel == 2) {
          board[i][j] = 1;
        } else if (cel == -1) {
          board[i][j] = 0;
        }
      }
    }
  }

  private int count(int r, int c, int ROW, int COL, int[][] board) {
    int live = 0;
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        int nr = r + i, nc = c + j;
        if (0 <= nr && 0 <= nc
            && nr < ROW && nc < COL
            && (nr != r || nc != c)
            && (board[nr][nc] == 1 || board[nr][nc] == -1)) {
          live++;
        }
      }
    }

    return live;
  }
}
