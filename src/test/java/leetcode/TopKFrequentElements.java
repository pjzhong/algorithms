package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import org.junit.Test;

public class TopKFrequentElements {

  @Test
  public void test() {
    int[][] tests = {
        {1, 1, 1, 2, 2, 3},
        {1}
    };

    int[] ks = {2, 1};
    for (int i = 0; i < ks.length; i++) {
      System.out.println(topKFrequent(tests[i], ks[i]));
    }
  }

  public List<Integer> topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> counts = new HashMap<>();
    for (int w : nums) {
      counts.merge(w, 1, Integer::sum);
    }
    PriorityQueue<Integer> queue = new PriorityQueue<>(
        (a, b) -> counts.get(b) - counts.get(a)
    );
    queue.addAll(counts.keySet());

    List<Integer> res = new ArrayList<>();
    for (int i = 0; i < k; i++) {
      res.add(queue.poll());
    }
    return res;
  }

}
