package com.pjzhong.leetcode.contest94;

import com.pjzhong.leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class LeafSimilarTrees {


    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<TreeNode> one = leafLeft(root1, new ArrayList<>()), two = leafLeft(root2, new ArrayList<>());

        one.forEach(t -> System.out.print(t.val + " "));
        System.out.println();
        two.forEach(t -> System.out.print(t.val + " "));
        if(one.size() == two.size()) {
            int n = one.size();
            for(int i = 0; i < n; i++) {
                if(one.get(i).val != two.get(i).val) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    public List<TreeNode> leafLeft(TreeNode root, List<TreeNode> leaf ) {
        if(root == null) { return leaf; }

        if(isLeaf(root)) {
            leaf.add(root);
        } else {
            leafLeft(root.left, leaf);
            leafLeft(root.right, leaf);
        }

        return leaf;
    }

    public boolean isLeaf(TreeNode node) {
        return node != null && node.left == null && node.right == null;
    }
}
