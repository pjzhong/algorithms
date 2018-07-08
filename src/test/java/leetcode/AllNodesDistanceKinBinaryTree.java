package leetcode;

import java.util.*;

/**
 * We are given a binary tree (with root node root), a target node, and an integer value K.

 Return a list of the values of all nodes that have a distance K from the target node.
 The answer can be returned in any order.
 *
 *
 * https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/description/
 * */
public class AllNodesDistanceKinBinaryTree {

    LinkedList<TreeNode> path;
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        search(root, target, new LinkedList<>());
        List<Integer> result = new ArrayList<>();

        TreeNode pre = null;
        for(int i = path.size() - 1, k = K; 0 <= i && 0 <= k; --i,--k) {
            nodesInKDistance(path.getLast(), k, pre,  result);
            pre = path.removeLast();
        }

        return result;
    }

    private void search(TreeNode root, TreeNode target, LinkedList<TreeNode> treeNodes) {
        if(root == null || path != null) { return; }
        treeNodes.add(root);
        if(root.val == target.val) {
            path = new LinkedList<>(treeNodes);
        } else {
            search(root.left, target, treeNodes);
            search(root.right, target, treeNodes);
            treeNodes.removeLast();
        }
    }

    private void nodesInKDistance(TreeNode root, int k, TreeNode pre,  List<Integer> result) {
        if(root == null || root == pre) { return;}

        if(k == 0) {
            result.add(root.val);
        } else {
            nodesInKDistance(root.left, k - 1, pre, result);
            nodesInKDistance(root.right, k - 1, pre, result);
        }
    }
}
