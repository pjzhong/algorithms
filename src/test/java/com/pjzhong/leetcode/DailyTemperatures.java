package com.pjzhong.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

/**
 * Given a list of daily temperatures T, return a list such that, for each day in the input, tells
 * you how many days you would have to wait until a warmer temperature. If there is no future day
 * for which this is possible, put 0 instead.
 *
 * For example, given the list of temperatures T = [73, 74, 75, 71, 69, 72, 76, 73], your output
 * should be [1, 1, 4, 2, 1, 1, 0, 0].
 *
 * Note: The length of temperatures will be in the range [1, 30000]. Each temperature will be an
 * integer in the range [30, 100]
 *
 * @link https://leetcode.com/problems/daily-temperatures/
 * @since 2020年02月09日 22:49:24
 **/
public class DailyTemperatures {

  @Test
  public void test() {

    List<int[]> tests = new ArrayList<>();
    tests.add(new int[]{73, 74, 75, 71, 69, 72, 76, 7});//官方用列

    //随机用例
    Random random = new Random();
    for (int i = 0; i <= 300; i++) {
      int[] t = new int[i];
      for (int j = 0; j < i; j++) {
        t[j] = random.nextInt(300) + 1;
      }
      tests.add(t);
    }
    for (int[] t : tests) {
      Assert.assertArrayEquals(bruteForce(t), dailyTemperatures(t));
    }
  }

  private int[] bruteForce(int[] T) {
    int[] result = new int[T.length];

    for (int i = 0; i < T.length; i++) {
      for (int j = i + 1; j < T.length; j++) {
        if (T[i] < T[j]) {
          result[i] = j - i;
          break;
        }
      }
    }

    return result;
  }

  //官方答案
  public int[] dailyTemperatures(int[] T) {
    int[] next = new int[T.length];
    LinkedList<Integer> stack = new LinkedList<>();
    for (int i = T.length - 1; 0 <= i; i--) {
      //保证栈里面的下标指向的数都比T[i]大
      //然后T[i] 就可以快速找到下一个比它大的值(的下标地址)
      while (0 < stack.size() && T[stack.peek()] <= T[i]) {
        stack.pop();
      }
      next[i] = stack.isEmpty() ? 0 : stack.peek() - i;
      stack.push(i);
    }

    return next;
  }

  public int[] dailyTemperaturesV1(int[] T) {
    int[] count = new int[301];
    int[] result = new int[T.length];
    for (int i = T.length - 1; 0 <= i; i--) {
      int t = T[i];
      count[t] = i;

      int idx = T.length;
      for (int j = t + 1; j < count.length; j++) {
        if (0 < count[j]) {
          idx = Math.min(idx, count[j]);
        }
      }

      result[i] = idx == T.length ? 0 : idx - i;
    }
    return result;
  }


  public int[] dailyTemperaturesV2(int[] T) {
    LinkedList<Integer>[] counts = new LinkedList[301];

    for (int i = T.length; 0 <= i; i--) {
      if (counts[T[i]] == null) {
        counts[T[i]] = new LinkedList<>();
      }

      counts[T[i]].add(i);
    }

    int[] result = new int[T.length];
    for (int i = 0; i < T.length; i++) {
      int idx = T.length;
      for (int j = T[i] + 1; j <= 300; j++) {
        LinkedList<Integer> idxs = counts[j];
        if (idxs != null && 0 < idxs.size()) {
          idx = Math.min(idxs.peekFirst(), idx);
        }
      }
      result[i] = idx == T.length ? 0 : idx - i;
      counts[T[i]].pollFirst();
    }

    return result;
  }

}
