package dsa.tree;

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

    private void updateHeightAbove(Node<E> node) {
        while(node != null) {
            updateHeight(node);
            node = node.parent;
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
