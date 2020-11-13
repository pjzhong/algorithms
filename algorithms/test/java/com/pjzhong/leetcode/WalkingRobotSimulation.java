package com.pjzhong.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

/**
 * A robot on an infinite grid starts at point (0, 0) and faces north.  The robot can receive one of
 * three possible types of commands:
 *
 * -2: turn left 90 degrees -1: turn right 90 degrees 1 <= x <= 9: move forward x units Some of the
 * grid squares are obstacles.
 *
 * The i-th obstacle is at grid point (obstacles[i][0], obstacles[i][1])
 *
 * If the robot would try to move onto them, the robot stays on the previous grid square instead
 * (but still continues following the rest of the route.)
 *
 * Return the square of the maximum Euclidean distance that the robot will be from the origin.
 *
 *
 *
 * Example 1:
 *
 * Input: commands = [4,-1,3], obstacles = [] Output: 25 Explanation: robot will go to (3, 4)
 * Example 2:
 *
 * Input: commands = [4,-1,4,-2,4], obstacles = [[2,4]] Output: 65 Explanation: robot will be stuck
 * at (1, 4) before turning left and going to (1, 8)
 *
 *
 * Note:
 *
 * 0 <= commands.length <= 10000 0 <= obstacles.length <= 10000 -30000 <= obstacle[i][0] <= 30000
 * -30000 <= obstacle[i][1] <= 30000 The answer is guaranteed to be less than 2 ^ 31.
 *
 * @link https://leetcode.com/problems/walking-robot-simulation/description/
 *
 * The most annoying question because you didn't read the question carefully
 */
public class WalkingRobotSimulation {

  final int right = 0, down = 1, left = 2, up = 3;

  public int robotSim(int[] commands, int[][] obstacles) {
    Map<Integer, Set<Integer>> map = new HashMap<>(10000);
    for (int[] o : obstacles) {
      Set<Integer> s = map.get(o[0]);
      if (s == null) {
        map.put(o[0], s = new HashSet<>());
      }
      s.add(o[1]);
    }

    int[] point = {0, 0};
    int face = right, max = 0;
    for (int comm : commands) {
      switch (comm) {
        case -2:
          face = (face + 3) % 4;
          break;
        case -1:
          face = (face + 1) % 4;
          break;
        default: {
          move(face, comm, point, map);
          max = Math.max(max, (point[0] * point[0]) + (point[1] * point[1]));
        }
      }
    }

    return max;
  }

  public void move(int face, int comm, int[] point, Map<Integer, Set<Integer>> map) {
    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};

    int x = point[0], y = point[1];
    for (int i = 0; i < comm; i++) {
      int x_temp = x + dx[face], y_temp = y + dy[face];
      if (map.containsKey(x_temp) && map.get(x_temp).contains(y_temp)) {
        break;
      } else {
        x = x_temp;
        y = y_temp;
      }
    }
    point[0] = x;
    point[1] = y;
  }

  @Test
  public void test() {
    int[][] commands = {
        {4, -1, 3},
        {4, -1, 4, -2, 4,},
        {4, -1, 4, -2, 4, -1, 8},
        {4, -1, 4, -2, 4, -1, 8, -2, 4},
        {4, -1, 4, -2, 4, -1, 8, -2, 4, -2, 5},
    };

    int[][][] obstacles = {
        {},
        {{2, 4}},
        {},
        {},
        {},
    };

    for (int i = 0; i < commands.length; i++) {
      System.out.println(robotSim(commands[i], obstacles[i]));
    }
  }

}
