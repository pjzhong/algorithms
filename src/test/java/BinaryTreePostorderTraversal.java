import java.util.LinkedList;
import java.util.List;

/**
 * Given a binary tree, Given a binary tree, return the postorder traversal of its nodes' values.
 * For example:
 * Given binary tree [1,null,2,3],
 * 1
 * \
 *  2
 *  /
 *  3
 *  return [3,2,1].
 *
 *  Note: Recursive solution is trivial, could you do it iteratively?
 *
 *
 *  https://leetcode.com/problems/binary-tree-preorder-traversal/description/
 * */
public class BinaryTreePostorderTraversal {

    public List<Integer> postOrderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();

        if(root != null) { stack.push(root);}
        TreeNode lastVisited = null;
        while(!stack.isEmpty()) {
            if(lastVisited == null || !isChild(stack.peek(), lastVisited)) {
                gotoHVFL(stack);
            }
            lastVisited = stack.pop();
            result.add(lastVisited.val);
        }

        return result;
    }

    /**
     * Highest visible leaf from left
     * */
    private void gotoHVFL(LinkedList<TreeNode> stack) {
        TreeNode n;
        while((n = stack.peek()) != null) {
            if(n.left != null) {
                if(n.right != null) {
                    stack.push(n.right);
                }
                stack.push(n.left);
            } else {
                stack.push(n.right);
            }
        }

        if(!stack.isEmpty()) { stack.pop(); }
    }

    private boolean isChild(TreeNode parent, TreeNode child) {
        return (parent != null) && (parent.left == child || parent.right == child);
    }
}

