package dsa.tree;


import dsa.list.LinkedList;
import dsa.list.Stack;

public class BinaryTree<E> {

    private Node<E> root;

    private int size(Node<E> node) {
        return node == null ? 0 : node.size;
    }

    private int height(Node<E> node) {
        return node == null ? node.height : -1;
    }

    private int updateHeight(Node<E> node) {
        return node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private void heightUp(Node<E> node) {
        while(node != null) {
            updateHeight(node);
            node = node.parent;
        }
    }

    private void PreOrderTraverseRecursive(Node<E> node) {
        if(node == null) { return; }
        System.out.println(node.data);
        PreOrderTraverseRecursive(node.left);
        PreOrderTraverseRecursive(node.right);
    }

    private void PreOrderTraverseIterative(Node<E> node) {
        LinkedList<Node<E>> nodes = new LinkedList<>();
        if(node != null) {
            nodes.push(node);
        }

        Node<E> x = null;
        while(!nodes.isEmpty()) {
            x = nodes.pop();
            System.out.println(x.data);
            if(node.right != null) { nodes.push(node.right); }//First in Last out
            if(node.left != null) { nodes.push(node.left); }// Last in First Out
        }
    }

    public int size() {
        return size(root);
    }

    public int height() {
        return height(root);
    }

    public boolean isEmpty() {
        return root != null;
    }

    private static class Node<E> {
        int size, height;
        E data;
        Node<E> parent,left, right;

        public Node(E data, Node<E> parent) {
            this.data = data;
            this.parent = parent;
        }
    }
}
