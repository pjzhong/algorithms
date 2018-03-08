package dsa.tree;


import dsa.list.LinkedList;
import dsa.list.Stack;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

public abstract class BinaryTree<Key, Value> {

    protected int size(Node node) {
        return node == null ? 0 : node.getSize();
    }

    protected int height(Node node) { return node == null ? -1 : node.getHeight(); }

    protected boolean hasLeftChild(Node node) {
        return node != null && node.getLeft() != null;
    }

    protected boolean hasRightChild(Node node) {
        return node != null && node.getRight() != null;
    }

    private void visitAlongLeftBranch(Node x, Consumer consumer, Stack<Node> S) {
        while(x != null) {
            consumer.accept(x.getValue());
            S.push(x.getRight());
            x = x.getLeft();
        }
    }

    public void preOrderTraversal(Consumer consumer) {
        Stack<Node> nodes = new LinkedList<>();

        Node e = getRoot();
        while(true) {
            visitAlongLeftBranch(e, consumer, nodes);
            if(nodes.isEmpty()) { break; }
            e = nodes.pop();
        }
    }

    private void goAlongLeftBranch(Node x, Stack<Node> S) {
        while(x != null) {
            S.push(x);
            x = x.getLeft();
        }
    }

    public void inOrderTraversal(Consumer consumer) {
        Stack<Node> nodes = new LinkedList<>();
        Node x = getRoot();
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
    private void gotoHLVFL(Stack<Node> S) {
        Node x ;
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

    private void postOrderTraversal(Node x, Consumer consumer) {
        Stack<Node> nodes = new LinkedList<>();
        if(x != null) { nodes.push(x); }

        while(!nodes.isEmpty()) {
            if(nodes.top() != x.getParent()) {//若栈顶非当前节点之父,则必为其右兄
                gotoHLVFL(nodes);//在以其右为根子树中，找到HLVFL
            }
            x = nodes.pop();
            consumer.accept(x.getValue());
        }
    }

    public void postOrderTraversal(Consumer consumer) {
        postOrderTraversal(getRoot(), consumer);
    }

    public void levelTraversal(Consumer consumer) {
        LinkedList<Node> nodes = new LinkedList<>();
        if(getRoot() != null) {
            nodes.enqueue(getRoot());
        }

        Node x;
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

    protected abstract Node getRoot();

    @Getter
    @Setter
    protected class Node {
        Key key;
        Value value;
        Node parent, left, right;
        int size = 1;
        int height = 0;

        public Node(Node parent, Key key, Value value) {
            this.parent = parent;
            this.key = key;
            this.value = value;
        }


        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("key=").append(key);
            sb.append(", value=").append(value);
            sb.append(", size=").append(size);
            sb.append(", height=").append(height);
            if(left != null) { sb.append(", left=").append(left.key); }
            if(right != null) { sb.append(", right=").append(right.key); }
            sb.append('}');
            return sb.toString();
        }
    }
}
