package dsa.graphTest;

import java.io.BufferedInputStream;
import java.util.LinkedList;
import java.util.Scanner;

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
