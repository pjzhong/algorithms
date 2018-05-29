import java.util.LinkedList;
import java.util.Queue;

/**
 Given a binary tree, find the leftmost value in the last row of the tree.

 Example 1:
 Input:

    2
  / \
 1   3

 Output:
 1
 Example 2:
 Input:

      1
    / \
   2   3
 /   / \
 4   5   6
 /
 7

 Output:
 7
 Note: You may assume the tree (i.e., the given root node) is not NULL.

 https://leetcode.com/problems/find-bottom-left-tree-value/description/
 * */
public class FindBottomLeftTreeValue {

    public int findBottomLeftValue(TreeNode root) {
        Queue<TreeNode> nodes = new LinkedList<>();
        nodes.add(root);

        TreeNode node;
        int leftMost = -1, size;
        while(!nodes.isEmpty()) {
            size = nodes.size();leftMost = nodes.peek().val;
            for(int i = 0; i < size; i++) {
                node = nodes.poll();
                if(node.left != null) { nodes.add(node.left);}
                if(node.right != null) { nodes.add(node.right);}
            }
        }

        return leftMost;
    }
}
