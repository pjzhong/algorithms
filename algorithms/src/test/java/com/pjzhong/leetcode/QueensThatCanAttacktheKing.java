package com.pjzhong.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;


/**
 * On an 8x8 chessboard, there can be multiple Black Queens and one White King.
 *
 * Given an array of integer coordinates queens that represents the positions of the Black Queens,
 * and a pair of coordinates king that represent the position of the White King, return the
 * coordinates of all the queens (in any order) that can attack the King.
 *
 * @link https://leetcode.com/problems/queens-that-can-attack-the-king/
 **/
public class QueensThatCanAttacktheKing {

  @Test
  public void test() {
    int[][][] queens = {
        {{5, 6}, {7, 7}, {2, 1}, {0, 7}, {1, 6}, {5, 1}, {3, 7}, {0, 3}, {4, 0}, {1, 2}, {6, 3},
            {5, 0}, {0, 4}, {2, 2}, {1, 1}, {6, 4}, {5, 4}, {0, 0}, {2, 6}, {4, 5}, {5, 2}, {1, 4},
            {7, 5}, {2, 3}, {0, 5}, {4, 2}, {1, 0}, {2, 7}, {0, 1}, {4, 6}, {6, 1}, {0, 6}, {4, 3},
            {1, 7}},
        {{0, 0}, {1, 1}, {2, 2}, {3, 4}, {3, 5}, {4, 4}, {4, 5}},
        {{0, 1}, {1, 0}, {4, 0}, {0, 4}, {3, 3}, {2, 4}},
    };
    int[][] kings = {{3, 4}, {3, 3}, {0, 0}};

    for (int i = 0; i < kings.length; i++) {
      System.out.println(queensAttacktheKing(queens[i], kings[i]));
    }
  }

  public List<List<Integer>> queensAttacktheKing(int[][] queens, int[] king) {
    Map<Integer, int[]> qs = new HashMap<>(queens.length);
    for (int[] q : queens) {
      int key = q[0] * 8 + q[1];
      qs.put(key, q);
    }

    List<List<Integer>> res = new ArrayList<>();
    List<int[]> directions = Arrays.asList(new int[]{1, 0},
        new int[]{-1, 0},
        new int[]{0, 1},
        new int[]{0, -1},
        new int[]{1, -1},
        new int[]{-1, -1},
        new int[]{-1, 1},
        new int[]{1, 1});

    for (int[] d : directions) {
      res.addAll(search(king, d[0], d[1], qs));
    }

    return res;
  }

  private List<List<Integer>> search(int[] king, int rplus, int cplus, Map<Integer, int[]> queens) {
    for (int r = king[0], c = king[1]; 0 <= r && r < 8 && 0 <= c && c < 8; r += rplus, c += cplus) {
      int target = r * 8 + c;
      int[] q = queens.get(target);
      if (q != null) {
        return Collections.singletonList(Arrays.asList(q[0], q[1]));
      }
    }

    return Collections.emptyList();
  }
}
