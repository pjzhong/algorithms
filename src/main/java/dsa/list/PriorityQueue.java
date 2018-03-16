package dsa.list;

import java.util.Arrays;
import java.util.Iterator;


/**
 * For more info, see the links below
 *
 * https://algs4.cs.princeton.edu/24pq/
 * http://www.xuetangx.com/courses/course-v1:TsinghuaX+30240184_2X+sp/courseware/4be1bdb834a645f685ff471da93b7db6/fbbb6eecf0c7424eba7d7362462f85d8/
 * */
public class PriorityQueue<E extends Comparable<E>> implements Queue<E> {

    private static final int DEFAULT_CAPACITY = 16;

    private Comparable[] elements;
    private int capacity = DEFAULT_CAPACITY;
    private int size = 0;

    private boolean less(Comparable x, Comparable y) {
        return x.compareTo(y) < 0;
    }

    private void exchange(int x, int y) {
        Comparable t = elements[x];
        elements[x] = elements[y];
        elements[y] = t;
    }

    private int parent(int i) {
        return (i - 1) >> 1;
    }

    private int leftChild(int i) {
        return 1 + (i << 1);
    }

    private int rightChild(int i) {
        return (1 + i ) << 1;
    }

    private void percolateUp(int child) {
        int parent = parent(child);
        while(parent >= 0 && less(elements[parent], elements[child])) {
            exchange(parent, child);
            child = parent;
            parent = parent(child);
        }
    }

    private int maxChild(int i, int size) {
        int right = rightChild(i), left = leftChild(i);

        return right < size ?
                (less(elements[right], elements[left]) ? left : right) :
                (left < size) ? left : i;
    }

    private void percolateDown(int parent, int size) {
        int child;
        while(parent != (child = maxChild(parent, size)) && less(elements[parent], elements[child])) {
            exchange(parent, child);
            parent = child;
        }
    }

    private void expand() {
        if(this.size < this.capacity) { return; }

        this.capacity = Math.max(this.capacity, DEFAULT_CAPACITY);

        Comparable[] oldElements = this.elements; this.elements = new Comparable[this.capacity <<= 1];
        for(int i = 0, size = oldElements.length; i < size; i++) {
            this.elements[i] = oldElements[i];
        }
    }

    private void shrink() {
        if(this.capacity < this.DEFAULT_CAPACITY) { return; }
        if(this.capacity  <  (this.size << 2)) { return; }

        Comparable[] oldElements = this.elements; this.elements =  new Comparable[this.capacity >>= 1];
        for(int i = 0; i < this.size; i++ ) {
            this.elements[i] = oldElements[i];
        }
    }

    private void heapify() {
        for(int i = (size / 2) - 1; i >= 0; i--) {
            percolateDown(i, size);
        }
    }

    public PriorityQueue(int capacity) {
        elements = new Comparable[capacity];
        this.capacity = capacity;
        size = 0;
    }

    public PriorityQueue() {
        this(DEFAULT_CAPACITY);
    }

    public PriorityQueue(E[] elements) {
        this.elements = Arrays.copyOf(elements, elements.length);
        capacity = elements.length;
        size = elements.length;
        heapify();
    }


    /**
     * Add a new element to this priority queue
     * */
    @Override
    public void enqueue(E value) {
        expand();
        elements[size++] = value;
        percolateUp(size - 1);
    }

    /**
     * remove the largest element in this priorityQueue
     * */
    @Override
    public E dequeue() {
        E max = (E) elements[0];
        elements[0] = elements[--size];
        elements[size] = null;
        percolateDown(0, size);
        shrink();
        return max;
    }

    /**
     * return the largest element in this priorityQueue
     * */
    @Override
    public E front() {
        return (E)elements[0];
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
        for(int i = 0; i < size; i++) {
            elements[i] = null;
        }
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
}
