package leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 Given a binary tree, imagine yourself standing on the right side of it,return the values of the
 nodes you can see ordered from top to bottom.

 Example:

 Input: [1,2,3,null,5,null,4]
 Output: [1, 3, 4]
 Explanation:

   1            <---
 /   \
 2     3         <---
 \     \
 5     4       <---

 https://leetcode.com/problems/binary-tree-right-side-view/description/
 * */
public class BinaryTreeRightSideView {

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        LinkedList<TreeNode> parent = new LinkedList<>();
        if(root != null) {  parent.add(root); }

        TreeNode node;
        for(;!parent.isEmpty();) {
            result.add(parent.getLast().val);
            int size = parent.size();
            for(int i = 0; i < size; i++) {
                node = parent.poll();
                if(node.left != null) { parent.add(node.left); }
                if(node.right != null) { parent.add(node.right); }
            }
        }

        return result;
    }
}
