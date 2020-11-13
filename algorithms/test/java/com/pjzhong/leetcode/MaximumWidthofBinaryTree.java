package com.pjzhong.leetcode;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.TreeMap;
import org.junit.Test;

/**
 * Given a binary tree, write a function to get the maximum width of the given tree. The width of a
 * tree is the maximum width among all levels. The binary tree has the same structure as a full
 * binary tree, but some nodes are null.
 *
 * The width of one level is defined as the length between the end-nodes (the leftmost and right
 * most non-null nodes in the level, where the null nodes between the end-nodes are also counted
 * into the length calculation.
 *
 * @author ZJP
 * @link https://leetcode.com/problems/maximum-width-of-binary-tree/
 * @since 2019年11月28日 12:02:53
 **/
public class MaximumWidthofBinaryTree {

  @Test
  public void test() {
    for (TreeNode root : createTrees()) {
      assertEquals(widthOfBinaryTree(root), widthOfBinaryTreeWithArray(root));
      assertEquals(widthOfBinaryTree(root), widthOfBinaryTreeWithQueue(root));
    }
  }

  public int widthOfBinaryTreeWithQueue(TreeNode root) {
    Queue<TreeNode> nodes = new LinkedList<>();
    Queue<Integer> indexes = new LinkedList<>();
    nodes.add(root);
    indexes.add(1);

    int max = Integer.MIN_VALUE;
    while (!nodes.isEmpty()) {
      int size = nodes.size();
      int start = indexes.peek(), end = -1;
      for (int i = 0; i < size; i++) {
        TreeNode node = nodes.remove();
        end = indexes.remove();
        if (node.left != null) {
          nodes.add(node.left);
          indexes.add(end * 2);
        }

        if (node.right != null) {
          nodes.add(node.right);
          indexes.add(end * 2 + 1);
        }
      }

      max = Math.max(max, end - start + 1);
    }

    return max;
  }

  public int widthOfBinaryTree(TreeNode root) {
    TreeMap<Integer, TreeNode> parent = new TreeMap<>();
    TreeMap<Integer, TreeNode> child = new TreeMap<>();
    parent.put(1, root);

    int max = Integer.MIN_VALUE;
    while (!parent.isEmpty()) {
      for (Entry<Integer, TreeNode> e : parent.entrySet()) {
        TreeNode n = e.getValue();
        if (n.left != null) {
          child.put(e.getKey() * 2, n.left);
        }
        if (n.right != null) {
          child.put(e.getKey() * 2 + 1, n.right);
        }
      }

      max = Math.max(max, parent.lastKey() - parent.firstKey() + 1);

      TreeMap<Integer, TreeNode> temp = parent;
      parent = child;
      child = temp;
      child.clear();
    }

    return max;
  }

  public int widthOfBinaryTreeWithArray(TreeNode root) {
    List<TreeNode> parent = new ArrayList<>();
    List<TreeNode> child = new ArrayList<>();
    parent.add(root);

    int max = Integer.MIN_VALUE;
    boolean hasNext = true;
    while (!parent.isEmpty() && hasNext) {
      hasNext = false;
      int firstIdx = -1, lastIdx = -1;
      for (int i = 0, size = parent.size(); i < size; i++) {
        TreeNode n = parent.get(i);
        if (n != null) {
          child.add(n.left);
          child.add(n.right);
          if (firstIdx < 0) {
            firstIdx = i;
          }
          lastIdx = i;
          hasNext = true;
        } else {
          child.add(null);
          child.add(null);
        }
      }
      List<TreeNode> temp = parent;
      parent = child;
      temp.clear();
      child = temp;

      max = Math.max(max, lastIdx - firstIdx + 1);
    }

    return max;
  }

  private List<TreeNode> createTrees() {
    List<TreeNode> res = new ArrayList<>();

    TreeNode one = new TreeNode(1);
    one.left = new TreeNode(3);
    one.left.left = new TreeNode(5);
    one.left.right = new TreeNode(3);
    one.right = new TreeNode(2);
    one.right.right = new TreeNode(9);
    res.add(one);

    TreeNode two = new TreeNode(1);
    two.left = new TreeNode(3);
    two.left.left = new TreeNode(5);
    two.left.right = new TreeNode(3);
    res.add(two);

    TreeNode three = new TreeNode(1);
    three.left = new TreeNode(3);
    three.left.left = new TreeNode(5);
    three.right = new TreeNode(2);
    res.add(three);

    TreeNode four = new TreeNode(1);
    four.left = new TreeNode(3);
    four.left.left = new TreeNode(5);
    four.left.left.left = new TreeNode(6);
    four.right = new TreeNode(2);
    four.right.right = new TreeNode(9);
    four.right.right.right = new TreeNode(7);
    res.add(four);

    return res;
  }
}
