import java.util.LinkedList;
import java.util.List;

/**
 You need to find the largest value in each row of a binary tree.

 Example:
 Input:

 1
 / \
 3   2
 / \   \
 5   3   9

 Output: [1, 3, 9]

 https://leetcode.com/problems/find-largest-value-in-each-tree-row/description/
 * */
public class FindLargestValueinEachTreeRow {

    public List<Integer> largestValues(TreeNode root) {
        List<Integer> result = new LinkedList<>();

        LinkedList<TreeNode> parent = new LinkedList<>(), child = new LinkedList<>();
        if(root != null) { parent.push(root); }
        int max = Integer.MIN_VALUE;

        while(!parent.isEmpty()) {

            for(TreeNode node: parent) {
                if(node.left != null) { child.push(node.left); }
                if(node.right != null) { child.push(node.right);}
                if(max < node.val) { max = node.val;}
            }

            result.add(max); max = Integer.MIN_VALUE;
            LinkedList<TreeNode> temp = parent;
            parent = child;
            child = temp;child.clear();
        }

        return result;
    }
}
