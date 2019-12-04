package com.pjzhong.leetcode;


import java.util.LinkedList;

/**
 * You are given a perfect binary tree where all leaves are on the same level, and every parent has
 * two children. The binary tree has the following definition:
 *
 * Populate each next pointer to point to its next right node. If there is no next right node, the
 * next pointer should be set to NULL.
 *
 * Initially, all next pointers are set to NULL.
 *
 * @author ZJP
 * @link https://leetcode.com/problems/populating-next-right-pointers-in-each-node/
 * @since 2019年12月05日 00:01:56
 **/
public class PopulatingNextRightPointersinEachNode {

  public Node connect(Node root) {
    LinkedList<Node> nodes = new LinkedList<>();
    if (root != null) {
      nodes.add(root);
    }

    while (!nodes.isEmpty()) {
      int size = nodes.size();
      for (int i = 0; i < size; i++) {
        Node n = nodes.pollFirst();
        if (i + 1 < size) {
          n.next = nodes.peek();
        }
        if (n.left != null) {
          nodes.add(n.left);
        }
        if (n.right != null) {
          nodes.add(n.right);
        }
      }
    }

    return root;
  }

  /**
   * Solution from leetCooe
   *
   * @since 2019年12月05日 00:00:51
   */
  public Node connectWithNoExtraSpace(Node root) {
    if (root == null) {
      return null;
    }
    if (root.left != null) {
      root.left.next = root.right;
    }

    if (root.right != null) {
      if (root.next != null) {
        root.right.next = root.next.left;
      } else {
        root.right.next = null;
      }
    }
    connectWithNoExtraSpace(root.left);
    connectWithNoExtraSpace(root.right);
    return root;
  }
}

class Node {

  public int val;
  public Node left;
  public Node right;
  public Node next;

  public Node() {
  }

  public Node(int _val) {
    val = _val;
  }

  public Node(int _val, Node _left, Node _right, Node _next) {
    val = _val;
    left = _left;
    right = _right;
    next = _next;
  }
}
