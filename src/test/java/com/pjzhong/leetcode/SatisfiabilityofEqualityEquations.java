package com.pjzhong.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javafx.util.Pair;
import org.junit.Test;

/**
 * Given an array equations of strings that represent relationships between variables, each string
 * equations[i] has length 4 and takes one of two different forms: "a==b" or "a!=b".  Here, a and b
 * are lowercase letters (not necessarily different) that represent one-letter variable names.
 *
 * Return true if and only if it is possible to assign integers to variable names so as to satisfy
 * all the given equations.
 *
 *
 *
 * Example 1:
 *
 * <p>Input: ["a==b","b!=a"]</p>
 * <p>Output: false </p>
 * Explanation: If we assign say, a = 1 and b = 1, then the first equation is satisfied, but not the
 * second.  There is no way to assign the variables to satisfy both equations.
 *
 * @author ZJP
 * @since 2020年05月13日 15:56:13
 **/
public class SatisfiabilityofEqualityEquations {

  @Test
  public void test() {
    String[][] tests = {
        {"e!=c", "b!=b", "b!=a", "e==d"},
        {"a==a", "a!=a"},
        {"a==b", "c==d", "a!=d", "e==f", "g==h", "e!=h", "a!=e", "c!=g", "a!=g"},
        {"c==c", "f!=a", "f==b", "b==c"},
        {"a==b", "b==a"},
        {"a==b", "b!=c", "c==a"},
        {"a==b", "b==c", "c==d", "e!=a"},
        {"a!=a"},
        {"c==c", "b==d", "x!=z"},
        {"a==b", "b==c", "a==c"},
        {"a==b", "b!=a"},
    };

    for (String[] test : tests) {
      System.out.println(equationsPossible(test));
    }
  }

  public boolean equationsPossible(String[] equations) {
    Map<Character, Node> graphs = new TreeMap<>();

    for (String s : equations) {
      Node left = graphs.computeIfAbsent(s.charAt(0), Node::new);
      Node right = graphs.computeIfAbsent(s.charAt(3), Node::new);
      boolean equals = s.substring(1, 3).equals("==");
      left.edges.computeIfAbsent(equals, k -> new ArrayList<>()).add(right);
      right.edges.computeIfAbsent(equals, k -> new ArrayList<>()).add(left);
    }

    int assign = 1;
    Set<Node> paths = new HashSet<>(graphs.size());
    for (boolean equals : Arrays.asList(true, false)) {
      paths.clear();
      for (Node n : graphs.values()) {
        if (paths.add(n)) {
          if (n.num == 0) {
            n.num = assign++;
          }

          assign = travel(n, assign, equals, paths);
          if (assign < 0) {
            return false;
          }
        }
      }
    }
    return true;
  }

  private int travel(Node n, int num, boolean equals, Set<Node> paths) {
    for (Node node : n.edges.getOrDefault(equals, Collections.emptyList())) {
      if (node.num == 0) {
        node.num = equals ? n.num : ++num;
      }

      boolean failed = equals != (n.num == node.num);
      if (failed) {
        return -1;
      }
      if (paths.add(node)) {
        num = travel(node, num, equals, paths);
      }
    }
    return num;
  }

  @Test
  public void test2() {
    Map<Integer, Pair<Integer, Long>> values = new HashMap<>();
    values.entrySet().stream()
        .collect(
            Collectors.toMap(Map.Entry::getKey, V -> V.getValue().getKey(), (x, y) -> x,
                LinkedHashMap::new));
  }

  public static class Node implements Comparable<Node> {

    public char name;
    public int num;
    public Map<Boolean, List<Node>> edges;

    public Node(char name) {
      this.name = name;
      this.edges = new TreeMap<>();
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Node node = (Node) o;
      return name == node.name;
    }

    @Override
    public int hashCode() {
      return Objects.hash(name);
    }

    @Override
    public int compareTo(Node o) {
      return Integer.compare(name, o.name);
    }

    @Override
    public String toString() {
      return "Node{" +
          "name=" + name +
          ", num=" + num +
          '}';
    }
  }
}
