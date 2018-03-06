package dsa.tree;


import dsa.list.LinkedList;
import dsa.list.Stack;

import java.util.function.Consumer;

public abstract class BinaryTree<E> {

    protected int size(Node<E> node) {
        return node == null ? 0 : node.size();
    }

    protected int height(Node<E> node) { return node == null ? -1 : node.height(); }

    protected boolean hasLeftChild(Node<E> node) {
        return node != null && node.getLeft() != null;
    }

    protected boolean hasRightChild(Node<E> node) {
        return node != null && node.getRight() != null;
    }

    private void visitAlongLeftBranch(Node<E> x, Consumer<E> consumer, Stack<Node<E>> S) {
        while(x != null) {
            consumer.accept(x.getValue());
            S.push(x.getRight());
            x = x.getLeft();
        }
    }

    public void PreOrderTraversal(Consumer<E> consumer) {
        Stack<Node<E>> nodes = new LinkedList<>();

        Node<E> e = getRoot();
        while(true) {
            visitAlongLeftBranch(e, consumer, nodes);
            if(nodes.isEmpty()) { break; }
            e = nodes.pop();
        }
    }

    private void goAlongLeftBranch(Node<E> x, Stack<Node<E>> S) {
        while(x != null) {
            S.push(x);
            x = x.getLeft();
        }
    }

    public void InOrderTraversal(Consumer<E> consumer) {
        Stack<Node<E>> nodes = new LinkedList<>();
        Node<E> x = getRoot();
        while(true) {
            goAlongLeftBranch(x, nodes);
            if(nodes.isEmpty()) { break; }
            x = nodes.pop();
            consumer.accept(x.getValue());
            x = x.getRight();
        }
    }

    /**
     * highest leaf visible from left
     * */
    private void gotoHLVFL(Stack<Node<E>> S) {
        Node<E> x ;
        while( (x = S.top()) != null) {
            if(hasLeftChild(x)) {
                if(hasRightChild(x)) {
                    S.push(x.getRight());
                }
                S.push(x.getLeft());
            } else {
                S.push(x.getRight());
            }
        }
        S.pop();
    }

    private void postOrderTraversal(Node<E> x, Consumer<E> consumer) {
        Stack<Node<E>> nodes = new LinkedList<>();
        if(x != null) { nodes.push(x); }

        while(!nodes.isEmpty()) {
            if(nodes.top() != x.getParent()) {//若栈顶非当前节点之父,则必为其右兄
                gotoHLVFL(nodes);//在以其右为根子树中，找到HLVFL
            }
            x = nodes.pop();
            consumer.accept(x.getValue());
        }
    }

    public void postOrderTraversal(Consumer<E> consumer) {
        postOrderTraversal(getRoot(), consumer);
    }

    public void levelTraversal(Consumer<E> consumer) {
        LinkedList<Node<E>> nodes = new LinkedList<>();
        if(getRoot() != null) {
            nodes.enqueue(getRoot());
        }

        Node<E> x;
        while(!nodes.isEmpty()) {
            x = nodes.dequeue();
            if(hasLeftChild(x)) { nodes.enqueue(x.getLeft()); }
            if(hasRightChild(x)) { nodes.enqueue(x.getRight()); }

            consumer.accept(x.getValue());
        }
    }

    public int size() {
        return size(getRoot());
    }

    public int height() {
        return height(getRoot());
    }

    public boolean isEmpty() {
        return getRoot() != null;
    }

    protected abstract Node<E> getRoot();

    protected static interface Node<E> {
        int size();

        int height();

        E getValue();

        Node<E> getParent();

        Node<E> getLeft();

        Node<E> getRight();
    }
}
