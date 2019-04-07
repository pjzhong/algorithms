package leetcode.failed;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * In the "100 game," two players take turns adding, to a running total, any integer from 1..10. The
 * player who first causes the running total to reach or exceed 100 wins.
 * <p>
 * What if we change the game so that players cannot re-use integers?
 * <p>
 * For example, two players might take turns drawing from a common pool of numbers of 1..15 without
 * replacement until they reach a total >= 100.
 * <p>
 * Given an integer maxChoosableInteger and another integer desiredTotal, determine if the first
 * player to move can force a win, assuming both players play optimally.
 * <p>
 * You can always assume that maxChoosableInteger will not be larger than 20 and desiredTotal will
 * not be larger than 300.
 * <p>
 * Example
 * <p>
 * Input: maxChoosableInteger = 10 desiredTotal = 11
 * <p>
 * Output: false
 * <p>
 * Explanation: No matter which integer the first player choose, the first player will lose. The
 * first player can choose an integer from 1 up to 10. If the first player choose 1, the second
 * player can only choose integers from 2 up to 10. The second player will win by choosing 10 and
 * get a total = 11, which is >= desiredTotal. Same with other integers chosen by the first player,
 * the second player will always win.
 *
 * @link https://leetcode.com/problems/can-i-win/description/
 */
public class CanIWin {

  @Test
  public void test() {
    int[][] testCases = {
        {5, 50},
        {17, 60},
        {10, 11},
        {10, 12},
        {15, 100},
        {20, 200},
        {20, 300},
    };

    for (int[] t : testCases) {
      System.out.println(canIWin(t[0], t[1]));
    }
  }

  private Map<Integer, Boolean> map;
  private boolean[] used;

  public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
    if (desiredTotal == 0) {
      return true;
    }

    int sum = IntStream.rangeClosed(1, maxChoosableInteger).sum();
    if (sum < desiredTotal) {
      return false;
    }

    map = new HashMap<>();
    used = new boolean[maxChoosableInteger + 1];
    return helper(desiredTotal);
  }

  /**
   * each player play optimally, so that meas you don't treat they as two separate players, you just
   * have to write a function to play each step optimally. And invoke this function repeatedly as
   * the two plays playing.
   */
  public boolean helper(int desiredTotal) {
    if (desiredTotal <= 0) {
      return false;
    }//The opponent(player1 or player2) reach or excesses the desiredTotal;
    int key = Arrays.hashCode(used);
    if (!map.containsKey(key)) {
      for (int i = 1; i < used.length; i++) {
        if (!used[i]) {
          used[i] = true;

          boolean opWin = !helper(desiredTotal - i);
          used[i] = false;
          if (opWin) {
            map.put(key, true);
            return true;
          }
        }
      }
      map.put(key, false);
    }

    return map.get(key);
  }
}
