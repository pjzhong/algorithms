package com.pjzhong.leetcode;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class SatisfiabilityofEqualityEquations {

  public boolean equationsPossible(String[] equations) {
    Map<Character, Node> graphs = new TreeMap<>();

    for (String s : equations) {
      Node left = graphs.computeIfAbsent(s.charAt(0), Node::new);
      Node right = graphs.computeIfAbsent(s.charAt(3), Node::new);

      left.edges().put(right, s.substring(1, 3).equals("=="));
    }

    int assign = 1;
    for (Node n : graphs.values()) {
      if (n.num() == null) {
        n.setNum(assign);
      }
    }

    return false;
  }

  public static class Node implements Comparable<Node> {

    private char name;
    private Integer num;
    private Map<Node, Boolean> edges;
    private boolean fresh;

    public Node(char name) {
      this.name = name;
      this.edges = new TreeMap<>();
      this.fresh = true;
    }

    public Integer num() {
      return num;
    }

    public Node setNum(Integer num) {
      this.num = num;
      return this;
    }

    public char name() {
      return name;
    }

    public Map<Node, Boolean> edges() {
      return edges;
    }

    public Node setEdges(
        Map<Node, Boolean> edges) {
      this.edges = edges;
      return this;
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
  }
}
