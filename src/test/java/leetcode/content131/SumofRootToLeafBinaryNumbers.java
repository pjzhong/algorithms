package leetcode.content131;

import java.math.BigInteger;
import leetcode.TreeNode;
import org.junit.Test;

public class SumofRootToLeafBinaryNumbers {

  @Test
  public void test() {
    TreeNode root = new TreeNode(1);

    root.left = new TreeNode(0);
    root.left.left = new TreeNode(0);
    root.left.right = new TreeNode(1);

    root.right = new TreeNode(1);
    root.right.left = new TreeNode(0);
    root.right.right = new TreeNode(1);

    System.out.println(sumRootToLeaf(root));

    TreeNode root2 = new TreeNode(1);
    root2.left = new TreeNode(1);
    System.out.println(sumRootToLeaf(root2));

    TreeNode root3 = new TreeNode(1);
    System.out.println(sumRootToLeaf(root3));
  }

  private BigInteger zero = new BigInteger("0");

  public int sumRootToLeaf(TreeNode root) {
    if (root == null) {
      return 0;
    }

    BigInteger bigInt = sum(new StringBuilder(), root);
    return bigInt.mod(new BigInteger("1000000007", 10)).intValue();
  }

  private BigInteger sum(StringBuilder builder, TreeNode root) {
    if (root == null) {
      return zero;
    }

    int len = builder.length();
    BigInteger res;
    builder.append(root.val);
    if (root.left == null && root.right == null) {
      res = new BigInteger(builder.toString(), 2);
    } else {
      res = zero.add(sum(builder, root.left)).add(sum(builder, root.right));
    }
    builder.delete(len, builder.length());
    return res;
  }
}
