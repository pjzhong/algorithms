package dsa;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

public class ArrayList<T>  implements List<T> {

    T add(int index, T value) {
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


    private T elementIndex(int index) {
        if(0 > index || index > size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return (T) elements[index];
    }


    @Override
    public void add(T value) {  add(size, value); }

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

    public T replace(int index, T value) {
        T old = elementIndex(index);
        elements[index] = value;
        return old;
    }

    @Override
    public T remove(int index) {
        T old = elementIndex(index);
        remove(index, index + 1);
        return old;
    }

    @Override
    public boolean remove(T value) {
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
    public T set(int index, T value) {
        T old = elementIndex(index);
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
    public T[] toArray(T[] a) {
        if(a.length < size) {
            return (T[])Arrays.copyOf(elements, size, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, size);
        return a;
    }

    @Override
    public ListIterator<T> listIterator() {
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

    public ArrayList(T[] elements, int lo, int hi) {
        copyFrom(elements, lo, hi);
    }

    private void copyFrom(T[] elements, int lo, int hi) {
        this.elements =  new Object[this.capacity = 2 * (hi - lo)];
        size = 0;
        while(lo < hi) {
            this.elements[size++] = elements[lo++];
        }
    }

    //read only
    public int find(T value, int lo, int hi) {//input-sensitive
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
    public T get(int index) {
        return elementIndex(index);
    }

    @Override
    public Iterator<T> iterator() {
        return new ListItr(0);
    }

    private class Itr implements Iterator<T> {
         int cursor;
         int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            int i = cursor;
            cursor = i + 1;

            return elementIndex(lastRet = i);
        }
    }

    private class ListItr extends Itr implements ListIterator<T> {
        ListItr(int index) {
            super();
            cursor = index;
        }


        @Override
        public boolean hasPrevious() {
            return cursor >= 0;
        }

        @Override
        public T previous() {
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
        public void set(T t) {
            ArrayList.this.set(lastRet, t);
        }

        @Override
        public void add(T t) {
            int i = cursor;
            ArrayList.this.add(i, t);
            cursor = i + 1;
            lastRet = -1;
        }
    }

    private Object[] elements;
    private int size = 0;
    private int capacity = 0;

    private static final int DEFAULT_CAPACITY = 16;
}
