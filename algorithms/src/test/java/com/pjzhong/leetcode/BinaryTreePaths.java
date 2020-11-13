package com.pjzhong.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 Given a binary tree, return all root-to-leaf paths.

 For example, given the following binary tree:

    1
 /   \
 2     3
 \
 5
 All root-to-leaf paths are:

 ["1->2->5", "1->3"]

 https://leetcode.com/problems/binary-tree-paths/description/
 * */
public class BinaryTreePaths {

    public List<String> binaryTreePaths(TreeNode root) {
        return paths(root, new StringBuilder(), new LinkedList<>());
    }

    public List<String> paths(TreeNode root, StringBuilder sb, List<String> result) {
        if(root == null) { return result;}

        int prevIndex = sb.length();
        if(root.left == null && root.right == null) {
            sb.append(root.val);
            result.add(sb.toString());
        } else {
            sb.append(root.val).append("->");
            paths(root.left, sb, result);
            paths(root.right, sb, result);
        }


        sb.delete(prevIndex, sb.length());
        return result;
    }
}
