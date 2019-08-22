package com.pjzhong.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In a directed graph, we start at some node and every turn, walk along a directed edge of the graph.  If we reach a
 * node that is terminal (that is, it has no outgoing directed edges), we stop.
 * <p>
 * Now, say our starting node is eventually safe if and only if we must eventually walk to a terminal node.  More
 * specifically, there exists a natural number K so that for any choice of where to walk, we must have stopped at a
 * terminal node in less than K steps.
 * <p>
 * Which nodes are eventually safe?  Return them as an array in sorted order.
 * <p>
 * The directed graph has N nodes with labels 0, 1, ..., N-1, where N is the length of graph.  The graph is given in
 * the following form: graph[i] is a list of labels j such that (i, j) is a directed edge of the graph.
 * <p>
 * Example:
 * Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
 * Output: [2,4,5,6]
 * Here is a diagram of the above graph.
 * <p>
 * Note:
 * <p>
 * graph will have length at most 10000.
 * The number of edges in the graph will not exceed 32000.
 * Each graph[i] will be a sorted list of different integers, chosen within the range [0, graph.length - 1].
 *
 * @link https://com.pjzhong.leetcode.com/problems/find-eventual-safe-states/description/
 */
public class FindEventualSafeStates {

    public boolean[] marked;
    public Map<Integer, Boolean> cache;

    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<Integer> result = new ArrayList<>();

        marked = new boolean[graph.length];
        cache = new HashMap<>(graph.length);
        for (int i = 0; i < graph.length; i++) {
            boolean t = dfs(i, graph);
            if (t) {
                result.add(i);
            }
        }

        return result;
    }

    public boolean dfs(int start, int[][] graph) {
        if (cache.containsKey(start)) {
            return cache.get(start);
        }

        if (!marked[start]) {
            marked[start] = true;
            int[] neighbours = graph[start];

            boolean result = true;
            for (int i : neighbours) {
                result = dfs(i, graph);
                if (!result) {
                    break;
                }
            }
            marked[start] = false;
            cache.put(start, result);
            return result;
        }

        return false;
    }
}
