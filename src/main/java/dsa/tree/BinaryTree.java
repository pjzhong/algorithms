package dsa.tree;


import dsa.list.LinkedList;
import dsa.list.Stack;

import java.util.function.Consumer;

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

    private void visitAlongLeftBranch(Node<E> x, Consumer<E> consumer, Stack<Node<E>> S) {
        while(x != null) {
            consumer.accept(x.data);
            S.push(x.right);
            x = x.left;
        }
    }

    public void PreOrderTraversal(Consumer<E> consumer) {
        Stack<Node<E>> nodes = new LinkedList<>();

        Node<E> e = root;
        while(true) {
            visitAlongLeftBranch(e, consumer, nodes);
            if(nodes.isEmpty()) { break; }
            e = nodes.pop();
        }
    }

    private void InOrderTraversalRecursive(Node<E> node, Consumer<E> consumer) {
        if(node == null) { return; }
        InOrderTraversalRecursive(node.left, consumer);
        consumer.accept(node.data);
        InOrderTraversalRecursive(node.right, consumer);
    }

    public void InOrderTraversal(Consumer<E> consumer) {

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
