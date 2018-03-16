package dsa.list;

import java.util.Iterator;

/**
 * for more info, please see the link below
 * https://www.geeksforgeeks.org/leftist-tree-leftist-heap/
 * */
public class PriorityQueueLeftHeap<E extends Comparable<E>> implements Queue<E> {

    private int size;
    private Node<E> root;

    private boolean hasLeft(Node<E> node) {
        return node != null && node.left != null;
    }

    private boolean hasRight(Node<E> node) {
        return node != null && node.right != null;
    }

    private boolean less(Comparable x, Comparable y) {
        return x.compareTo(y) < 0;
    }

    private void swapChild(Node<E> e) {
        Node<E> t = e.left;
        e.left = e.right;
        e.right = t;
    }

    private Node<E> merge(Node<E> a, Node<E> b) {
        if(a == null) { return b;}
        if(b == null) { return a;}

        return less(a.value, b.value) ? doMerge(b, a) : doMerge(a, b);
    }

    private Node<E> doMerge(Node<E> a, Node<E> b) {
        if(hasLeft(a)) {
            a.right = merge(a.right, b);
            if(a.left.npl < a.right.npl) {
                swapChild(a);
            }
            a.npl = a.right.npl + 1;
        } else {
            a.left = b;
        }

        return a;
    }


    public PriorityQueueLeftHeap() {

    }

    public PriorityQueueLeftHeap(E[] elements) {
        for(E e : elements) {
            enqueue(e);
        }
    }

    public PriorityQueueLeftHeap(PriorityQueueLeftHeap<E> queue) {
        this.root = queue.root;
        this.size = queue.size;
    }

    @Override
    public void enqueue(E value) {
        Node<E> n = new Node<>(value);
        root = merge(root, n);
        size++;
    }

    @Override
    public E dequeue() {
        E old = root.value;
        size--;
        root = merge(root.left, root.right);
        return old;
    }

    @Override
    public E front() {
        return root.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return !isEmpty();
            }

            @Override
            public E next() {
                return dequeue();
            }
        };
    }


    private static class Node<E> {
        E value;
        int npl = 0 ;//Null path length
        Node<E> left, right;

        public Node(E value) {
            this.value = value;
        }

        public Node(Node<E> n) {
            this.value = n.value;
            this.npl = n.npl;
            this.left = n.left;
            this.right = n.right;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("value=").append(value);
            sb.append('}');
            return sb.toString();
        }
    }
}
