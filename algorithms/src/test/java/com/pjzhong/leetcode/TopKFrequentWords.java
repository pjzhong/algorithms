package com.pjzhong.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import org.junit.Test;

public class TopKFrequentWords {

  @Test
  public void test() {
    String[][] tests = {
        {"i", "love", "com/pjzhong/leetcode", "i", "love", "coding"},
        {"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"}
    };

    int[] ks = {2, 4};
    for (int i = 0; i < ks.length; i++) {
      System.out.println(topKFrequent(tests[i], ks[i]));
    }
  }

  public List<String> topKFrequent(String[] words, int k) {
    Map<String, Integer> counts = new HashMap<>();
    for (String w : words) {
      counts.merge(w, 1, Integer::sum);
    }
    PriorityQueue<String> queue = new PriorityQueue<>(
        (a, b) -> {
          int cmp = counts.get(b) - counts.get(a);
          return cmp == 0 ? a.compareTo(b) : cmp;
        });
    queue.addAll(counts.keySet());

    List<String> res = new ArrayList<>();
    for (int i = 0; i < k; i++) {
      res.add(queue.poll());
    }
    return res;
  }

}
