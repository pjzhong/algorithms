package dsa.graphTest;

import java.io.BufferedInputStream;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 真二叉树重构(Proper Rebuild)
 Description
 In general, given the preorder traversal sequence and postorder traversal sequence of a binary tree, we cannot determine the binary tree.



 Figure 1

 In Figure 1 for example, although they are two different binary tree, their preorder traversal sequence and postorder traversal sequence are both of the same.

 But for one proper binary tree, in which each internal node has two sons, we can uniquely determine it through its given preorder traversal sequence and postorder traversal sequence.

 Label n nodes in one binary tree using the integers in [1, n], we would like to output the inorder traversal sequence of a binary tree through its preorder and postorder traversal sequence.

 Input
 The 1st line is an integer n, i.e., the number of nodes in one given binary tree,

 The 2nd and 3rd lines are the given preorder and postorder traversal sequence respectively.

 Output
 The inorder traversal sequence of the given binary tree in one line.

 Example
 Input

 5
 1 2 4 5 3
 4 5 2 3 1
 Output

 4 2 5 1 3
 Restrictions
 For 95% of the estimation, 1 <= n <= 1,000,00

 For 100% of the estimation, 1 <= n <= 4,000,000

 The input sequence is a permutation of {1,2...n}, corresponding to a legal binary tree.

 Time: 2 sec

 https://dsa.cs.tsinghua.edu.cn/oj/problem.shtml?id=1146
 * */
public class ProperBinaryTreeReBuild {

    private static int[] preOrder;
    private static int[] postOrder;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new BufferedInputStream(System.in, 1 << 20))){
            int size = scanner.nextInt();

            preOrder = new int[size + 1];
            postOrder = new int[size + 1];

            for(int i = 1; i <= size; i++) {
                preOrder[i] = scanner.nextInt();
            }

            for(int i = 1; i <= size; i++) {
                postOrder[i] = scanner.nextInt();
            }

            Node root = build(1, size, size);
            InOrderTraversal(root);
        }
    }

    /**
     * The code below is hard to understand, even for yourself.
     * @param preBegin the begin Index of preOrderArray, including the root. from left to right.
     * @param postBegin the begin Index of preOrderArray, including the root. from right to left.
     * */
    public static Node build(int preBegin, int postBegin, int nodes) {
        if( nodes <= 3) {
            Node root = new Node(preOrder[preBegin]);
            if(nodes > 1) {
                root.left = new Node(preOrder[preBegin + 1]);
                root.right = new Node(postOrder[postBegin - 1]);
            }

            return root;
        } else {
            Node root = new Node(preOrder[preBegin]);

            int left = preOrder[preBegin + 1], right = postOrder[postBegin - 1];

            int leftTreeBeginInPostOrderArray = postBegin - 1;
            while(postOrder[leftTreeBeginInPostOrderArray] != left) {
                leftTreeBeginInPostOrderArray--;
            }

            int rightTreeBeginInPreOrderArray = preBegin + 1;
            while(preOrder[rightTreeBeginInPreOrderArray] != right) {
                rightTreeBeginInPreOrderArray++;
            }

            int leftNodes = rightTreeBeginInPreOrderArray - (preBegin + 1);
            int rightNodes = (postBegin - 1) - leftTreeBeginInPostOrderArray;
            root.left = build(preBegin + 1, leftTreeBeginInPostOrderArray, leftNodes);
            root.right = build(rightTreeBeginInPreOrderArray, postBegin - 1, rightNodes);

            return root;
        }
    }

    private static void goAlongLeftBranch(Node x, LinkedList<Node> S) {
        while(x != null) {
            S.push(x);
            x = x.left;
        }
    }

    public static void InOrderTraversal(Node node) {
        LinkedList<Node> nodes = new LinkedList<>();
        Node x = node;
        while(true) {
            goAlongLeftBranch(x, nodes);
            if(nodes.isEmpty()) { break; }
            x = nodes.pop();
            System.out.format("%d ", x.id);
            x = x.right;
        }
    }

    private static class Node {
        int id;
        Node left;
        Node right;

        public Node(int id) {
            this.id = id;
        }

        public Node(Node right, Node left, int id) {
            this.right = right;
            this.left = left;
            this.id = id;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("id=").append(id);
            if(left != null) {
                sb.append(", left=").append(left);
            }
            if(right != null) {
                sb.append(", right=").append(right);
            }

            sb.append('}');
            return sb.toString();
        }
    }
}
