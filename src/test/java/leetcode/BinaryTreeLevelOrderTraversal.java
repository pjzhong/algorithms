package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).

 For example:
 Given binary tree [3,9,20,null,null,15,7],
 3
 / \
 9  20
 /  \
 15   7
 return its level order traversal as:
 [
 [3],
 [9,20],
 [15,7]
 ]
 *
 * */
public class BinaryTreeLevelOrderTraversal {


    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        List<TreeNode> parent = new LinkedList<>(), child = new LinkedList<>(), tmp;
        if(root != null) {  parent.add(root); }

        for(;!parent.isEmpty();) {
            List<Integer> integers = new ArrayList<>();
            for(TreeNode node : parent) {
                integers.add(node.val);
                if(node.left != null) { child.add(node.left); }
                if(node.right != null) { child.add(node.right); }
            }
            tmp = parent;
            parent = child;
            tmp.clear();child = tmp;
            if(!integers.isEmpty()) { result.add(integers); }
        }

        return result;
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        List<TreeNode> parent = new LinkedList<>(), child = new LinkedList<>(), tmp;
        if(root != null) {  parent.add(root); }

        for(;!parent.isEmpty();) {
            List<Integer> integers = new LinkedList<>();
            for(TreeNode node : parent) {
                integers.add(node.val);
                if(node.left != null) { child.add(node.left); }
                if(node.right != null) { child.add(node.right); }
            }
            tmp = parent;
            parent = child;
            tmp.clear();child = tmp;
            if(!integers.isEmpty()) { result.addFirst(integers); }
        }

        return result;
    }
}
