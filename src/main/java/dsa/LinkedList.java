package dsa;

/**
 * Created by PJ_Z on 2/15/2018.
 */
public class LinkedList<E> implements List<E> {

    private int size = 0;
    private Node<E> header = null, trailer = null;

    private Node<E> node(int index) {
        if(index < (size >> 1)) {
            Node<E> node  = header.next;
            for(int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<E> node = trailer.prev;
            for(int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    void linkBefore(E e, Node<E> succ) {
        final Node<E> previous = succ.prev;
        final Node<E> newNode = new Node<E>(previous, e, succ);
        succ.prev = newNode;
        if(previous == null) {
            header = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    E unLink(Node<E> node) {
        final E element = node.item;
        final Node<E> previous = node.prev;
        final Node<E> next = node.next;

        if(previous == null) {
            header = next;
        } else {
            previous.next = next;
            node.prev = null;
        }

        if(next == null) {
            trailer = previous;
        } else {
            next.prev = previous;
            node.next = null;
        }

        node.item = null;
        size--;
        return element;
    }


    public LinkedList() {}

    public void addFirst(E value) {

    }

    public void addLast(E value) {

    }

    public E removeFirst() {
        return null;
    }

    public E removeLast() {
        return null;
    }

    public E getFirst() {
        return null;
    }

    public E getLast() {
        return null;
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
    public int indexOf(Object value) {
        int index = 0;
        for(Node<E> x = header; x != null; x = x.next) {
            if(x.item.equals(value)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        int index = size;
        for(Node<E> x = trailer; x != null; x = x.prev) {
            index--;
            if(x.item.equals(value)) {
                return index;
            }
        }

        return -1;
    }

    @Override
    public E get(int index) {
        return node(index).item;
    }

    @Override
    public E remove(int index) {
        return unLink(node(index));
    }

    @Override
    public boolean remove(E value) {
        for(Node<E> x = header; x != null; x = x.next) {
            if(x.item.equals(value)) {
                unLink(x);
                return true;
            }
        }

        return false;
    }

    @Override
    public E set(int index, E value) {
        return null;
    }

    @Override
    public void clear() {
        for(Node<E> x = header; x != null;) {
            Node<E> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        header = trailer = null;
        size = 0;
    }

    public static class Node<T> {
        Node<T> prev;
        T item;
        Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
