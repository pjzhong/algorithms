package com.pjzhong.leetcode.failed;


import org.junit.Test;

import java.util.*;

public class AdvantageShuffle {

  /**
   * Excellent solution
   *
   * @link https://com.pjzhong.leetcode.com/problems/advantage-shuffle/discuss/149822/JAVA-Greedy-6-lines-with-Explanation
   */
  public int[] advantageCount(int[] A, int[] B) {
    Arrays.sort(A);
    int n = A.length;
    int[] res = new int[n];
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
    for (int i = 0; i < n; i++) {
      pq.add(new int[]{B[i], i});
    }

    int lo = 0, hi = n - 1;
    while (!pq.isEmpty()) {
      int[] cur = pq.poll();
      int idx = cur[1], val = cur[0];
      if (val < A[hi]) {//Is the biggest Val in A can satisfy the biggest val in  ?
        res[idx] = A[hi--];
      } else {
        res[idx] = A[lo++];
      }
    }
    return res;
  }

  /**
   * Wrong and complicated answer
   */
  public int[] advantageCountMy(int[] A, int[] B) {
    TreeMap<Integer, Integer> map = new TreeMap<>();
    for (int i : A) {
      int c = map.getOrDefault(i, 0);
      map.put(i, c + 1);
    }

    int[] result = new int[A.length];
    for (int i = 0; i < B.length; i++) {
      Map.Entry<Integer, Integer> entry = map.ceilingEntry(B[i]);
      if (entry != null) {
        Map.Entry<Integer, Integer> remove = null;
        if (entry.getKey() == B[i]) {
          remove = entry;
          map.remove(entry.getKey());
          entry = map.ceilingEntry(B[i]);
        }
        if (entry != null && B[i] < entry.getKey()) {
          result[i] = entry.getKey();
          if (entry.getValue() - 1 <= 0) {
            map.remove(entry.getKey());
          } else {
            map.put(entry.getKey(), entry.getValue() - 1);
          }
        }

        if (remove != null) {
          map.put(remove.getKey(), remove.getValue());
        }
      }
    }

    int index = 0;
    while (!map.isEmpty()) {
      Map.Entry<Integer, Integer> e = map.firstEntry();
      for (int i = 0; i < e.getValue(); index++) {
        if (result[index] == 0) {
          result[index] = e.getKey();
          i++;
        }
      }
      map.remove(e.getKey());
    }

    return result;
  }

  @Test
  public void test() {
    int[][][] testCases = {
        {{2, 7, 11, 15}, {1, 10, 4, 11}},
        {{12, 24, 8, 32}, {13, 25, 32, 11}},
        {{2, 0, 4, 1, 2}, {1, 3, 0, 0, 2}},
    };

    for (int[][] t : testCases) {
      System.out.println(Arrays.toString(advantageCount(t[0], t[1])));
    }
  }
}
