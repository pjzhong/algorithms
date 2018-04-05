public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TreeNode{");
        sb.append("val=").append(val);
        sb.append('}');
        return sb.toString();
    }
}
