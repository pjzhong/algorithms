package dsa.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

public class ArrayList<E>  extends AbstractList<E> {

    E add(int index, E value) {
        expand();
        for(int i = size; i > index; i--) {
            elements[i] = elements[i -1];
        }
        elements[index] = value;size++;
        return value;
    }

    private int remove(int lo, int hi) {
        if(lo == hi) { return 0; }
        while(hi < this.size) { this.elements[lo++] = this.elements[hi++];}
        size = lo;shrink();
        return hi - lo;
    }

    private void expand() {
        if(this.size < this.capacity) { return; }

        this.capacity = Math.max(this.capacity, DEFAULT_CAPACITY);

        Object[] oldElements = this.elements; this.elements = new Object[this.capacity <<= 1];
        for(int i = 0, size = oldElements.length; i < size; i++) {
            this.elements[i] = oldElements[i];
        }
    }

    private void shrink() {
        if(this.capacity < this.DEFAULT_CAPACITY) { return; }
        if( (this.size << 2) > this.capacity ) { return; }

        Object[] oldElements = this.elements; this.elements =  new Object[this.capacity >>= 1];
        for(int i = 0; i < this.size; i++ ) {
            this.elements[i] = oldElements[i];
        }
    }


    private E elementIndex(int index) {
        if(0 > index || index > size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return (E) elements[index];
    }


    @Override
    public void add(E value) {  add(size, value); }

    public int deduplicate() {
        int oldSize = this.size;
        for(int i = 1; i < this.size;) {
            if(find(elementIndex(i), 0, i) < 0) {
                i++;
            } else {
                remove(i);
            }
        }

        return oldSize - size;
    }

    public E replace(int index, E value) {
        E old = elementIndex(index);
        elements[index] = value;
        return old;
    }

    @Override
    public E remove(int index) {
        E old = elementIndex(index);
        remove(index, index + 1);
        return old;
    }

    @Override
    public boolean remove(E value) {
        boolean result = false;
        for(int i = 0; i < size; i++) {
            if(elementIndex(i).equals(value)) {
                remove(i, i + 1);
                result = true;
            }
        }
        return result;
    }

    @Override
    public E set(int index, E value) {
        E old = elementIndex(index);
        elements[index] = value;
        return old;
    }

    @Override
    public void clear() {
        for(int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
        shrink();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public E[] toArray(E[] a) {
        if(a.length < size) {
            return (E[])Arrays.copyOf(elements, size, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, size);
        return a;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    //Constructors
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        elements =  new Object[capacity];
    }

    public ArrayList(E[] elements, int lo, int hi) {
        copyFrom(elements, lo, hi);
    }

    private void copyFrom(E[] elements, int lo, int hi) {
        this.elements =  new Object[this.capacity = 2 * (hi - lo)];
        size = 0;
        while(lo < hi) {
            this.elements[size++] = elements[lo++];
        }
    }

    //read only
    public int find(E value, int lo, int hi) {//input-sensitive
        while ( (lo < hi--) && (!value.equals(elements[hi])) );
        return hi;//hi < lo means didn't find the target
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
    public int indexOf(Object o) {
        for(int i = 0; i < size; i++) {
            if(elementIndex(i).equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for(int i = size - 1; i >= 0; i--) {
            if(elementIndex(i).equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public E get(int index) {
        return elementIndex(index);
    }

    @Override
    public Iterator<E> iterator() {
        return new ListItr(0);
    }

    private class Itr implements Iterator<E> {
         int cursor;
         int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() {
            int i = cursor;
            cursor = i + 1;

            return elementIndex(lastRet = i);
        }
    }

    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }


        @Override
        public boolean hasPrevious() {
            return cursor >= 0;
        }

        @Override
        public E previous() {
            int i = cursor - 1;
            cursor = i;
            return elementIndex(lastRet = i);
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            ArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }

        @Override
        public void set(E e) {
            ArrayList.this.set(lastRet, e);
        }

        @Override
        public void add(E e) {
            int i = cursor;
            ArrayList.this.add(i, e);
            cursor = i + 1;
            lastRet = -1;
        }
    }

    private Object[] elements;
    private int size = 0;
    private int capacity = 0;

    private static final int DEFAULT_CAPACITY = 16;
}
