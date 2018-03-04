package dsa.list;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedList<E> extends AbstractList<E> implements List<E>, Queue<E>, Stack<E> {

    private int size = 0;
    private Node<E> header = null, trailer = null;

    private Node<E> node(int index) {
        if(index < (size >> 1)) {
            Node<E> node  = header;
            for(int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<E> node = trailer;
            for(int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    void linkLast(E e) {
        final Node<E> l = trailer;
        final Node<E> newNode = new Node<E>(l, e, null);
        trailer = newNode;
        if(l == null) {
            header = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    void linkFirst(E e) {
        final Node<E> h = header;
        final Node<E> newNode = new Node<E>(null, e, h);
        header = newNode;
        if(h == null) {
            trailer = newNode;
        } else {
            h.prev = newNode;
        }
        size++;
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

    E unlinkFirst(Node<E> first) {
        final E element = first.item;
        final Node<E> next = first.next;

        first.item = null;
        first.next = null;
        header = next;
        if(next == null) {
            trailer = null;
        } else {
            next.prev = null;
        }
        size--;
        return element;
    }

    E unlinkLast(Node<E> last) {
        final E element = last.item;
        final Node<E> previous = last.prev;

        last.item = null;
        last.prev = null;
        trailer = previous;
        if(previous == null) {
            header = null;
        } else {
            previous.next = null;
        }
        size--;
        return element;
    }


    public LinkedList() {}

    public void addFirst(E value) {
        linkFirst(value);
    }

    public void addLast(E value) {
       linkLast(value);
    }

    public E removeFirst() {
       return header == null ? null : unlinkFirst(header);
    }

    public E removeLast() {
        return  trailer == null ? null :unlinkLast(trailer);
    }

    public E getFirst() {
        return header.item;
    }

    public E getLast() {
        return trailer.item;
    }

    @Override
    public void enqueue(E value) {
        addLast(value);
    }

    @Override
    public E dequeue() {
        return removeFirst();
    }

    @Override
    public E front() {
        return getFirst();
    }

    @Override
    public void push(E value) {
        addFirst(value);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public E top() {
        return getFirst();
    }

    @Override
    public void add(E value) {
        linkLast(value);
    }

    @Override
    public int size() {
        return size;
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
        Node<E> node = node(index);
        E old = node.item;
        node.item = value;
        return old;
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

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[size];

        int index = 0;
        for(Node<E> x = header; x != null; x = x.next) {
            objects[index++] = x.item;
        }
        return objects;
    }

    @Override
    public E[] toArray(E[] a) {
        if(a.length < size) {
            a = (E[]) Array.newInstance(a.getClass().getComponentType(), size);
        }

        int i = 0;
        Object[] result = a;
        for(Node<E> x = header; x != null; x = x.next) {
            result[i++] = x.item;
        }

        return a;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }

    private class ListItr implements ListIterator<E> {
        private Node<E> lastReturned;
        private Node<E> next;
        private int nextIndex;

        ListItr(int index) {
            next = (index == size) ? null : node(index);
            nextIndex = index;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public E next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public E previous() {
            if(!hasPrevious()) {
                throw new NoSuchElementException();
            }

            lastReturned = next = ( next == null) ? trailer : next.prev;
            nextIndex--;
            return lastReturned.item;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            if(lastReturned == null) {
                throw new IllegalStateException();
            }

            Node<E> lastNext = lastReturned.next;
            unLink(lastReturned);
            if(next == lastReturned) {
                next = lastNext;
            } else {
                nextIndex--;
            }
            lastReturned = null;
        }

        @Override
        public void set(E e) {
            if(lastReturned == null) {
                throw new IllegalStateException();
            }
            lastReturned.item = e;
        }

        @Override
        public void add(E e) {
            if(next == null) {
                linkLast(e);
            } else {
                linkBefore(e, next);
            }
            nextIndex++;
        }
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
