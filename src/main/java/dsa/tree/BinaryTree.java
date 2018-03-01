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

    private boolean hasLeftChild(Node<E> node) {
        return node != null && node.left != null;
    }

    private boolean hasRightChild(Node<E> node) {
        return node != null && node.right != null;
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

    private void goAlongLeftBranch(Node<E> x, Stack<Node<E>> S) {
        while(x != null) {
            S.push(x);
            x = x.left;
        }
    }

    public void InOrderTraversal(Consumer<E> consumer) {
        Stack<Node<E>> nodes = new LinkedList<>();
        Node<E> x = root;
        while(true) {
            goAlongLeftBranch(x, nodes);
            if(nodes.isEmpty()) { break; }
            x = nodes.pop();
            consumer.accept(x.data);
            x = x.right;
        }
    }

    private void PostOrderTraversalRecursive(Node<E> node, Consumer<E> consumer) {
        if(node == null) { return; }
        PostOrderTraversalRecursive(node.left, consumer);
        PostOrderTraversalRecursive(node.right, consumer);
        consumer.accept(node.data);
    }

    /**
     * highest leaf visible from left
     * */
    private void gotoHLVFL(Stack<Node<E>> S) {
        Node<E> x ;
        while( (x = S.top()) != null) {
            if(hasLeftChild(x)) {
                if(hasRightChild(x)) {
                    S.push(x.right);
                }
                S.push(x.left);
            } else {
                S.push(x.right);
            }
        }
        S.pop();
    }

    private void PostOrderTraversal(Node<E> x, Consumer<E> consumer) {
        Stack<Node<E>> nodes = new LinkedList<>();
        if(x != null) { nodes.push(x); }

        while(!nodes.isEmpty()) {
            if(nodes.top() != x.parent) {//若栈顶非当前节点之父,则必为其右兄
                gotoHLVFL(nodes);//在以其右为根子树中，找到HLVFL
            }
            x = nodes.pop();
            consumer.accept(x.data);
        }
    }

    public void PostOrderTraversal(Consumer<E> consumer) {
        PostOrderTraversal(root, consumer);
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
