package dsa;

import java.util.Arrays;
import java.util.Iterator;

public class Vector_OrderExArray<T extends Comparable<T>> implements Vector<T>, Iterable<T> {
    /*** ordered vector */

    public int search(T value) {
        return search(value, 0, size);
    }

    public int search(T value, int lo, int hi) {
        return binarySearch(value, lo, hi);
    }

    private int binarySearch(T value, int lo, int hi) {
        while (lo < hi) {
            int mid = (lo + hi) >>> 1, compareResult = value.compareTo(elementIndex(mid));
            if(compareResult < 0 ) {
                hi = mid;
            } else {
                lo = mid + 1;
            }//[lo, mi)或(mi, hi)
        }//出口时 A[lo = hi] 为大于e的最小元素

        return --lo;//lo - 1既不大于value的元素的最大index
    }

    private int fibonacciSearch(T value, int lo, int hi) {
        Fibonacci fib = new Fibonacci(hi - lo);
        while(lo < hi) {
            while(hi - lo < fib.get()) {
                fib.prev();
            }

            int mid = lo + fib.get() - 1, compare = value.compareTo(elementIndex(mid));
            if(compare < 0) {
                hi = mid;
            } else if(0 < compare) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    public int uniquify() {
        sort();
        int i = 0, j = 0;
        while(++j < size) {
            if( !elementIndex(i).equals(elementIndex(j)) ) {
                elements[++i] = elements[j];
            }
        }
        size = ++i;shrink();
        return j - i; //how duplicated elements have been deleted;
    }

    public void sort() {
        //Arrays.sort(elements, 0, size);
        mergeSort(0, size);
    }

    private void bubbleSort(int lo, int hi) {
        boolean sorted = false;
        for(int i = 0, size = hi - lo; i < size && (sorted = !sorted); i++) {
            for(int n = (lo + 1), length = hi - i; n < length; n++) {
                if(elementIndex(n - 1).compareTo(elementIndex(n)) > 0) {
                    Object temp = elements[n];
                    elements[n] = elements[n - 1];
                    elements[n - 1] = temp;
                    sorted = false;
                }
            }
        }
    }

    private void mergeSort(int lo, int hi) {
        if(hi - lo < 2) {
            return;
        }

        int mid = (lo + hi) >> 1;
        mergeSort(lo, mid);
        mergeSort(mid, hi);
        merge(lo, mid, hi);
    }

    //C++移植版
    private void merge(int lo, int mid, int hi) {
        Object leftElements[] = new Object[mid - lo];
        for(int i = lo; i < mid; i++) { leftElements[i - lo] = elementIndex(i); }

        for(int left = 0, right = mid, index = lo; (left + lo) < mid ;) {
            if(right < hi) {
                if(elementIndex(right).compareTo((T)leftElements[left]) < 0) {
                    this.elements[index++] = elementIndex(right++);
                }
            }

            if( (hi <= right) || ((T)leftElements[left]).compareTo(elementIndex(right)) <= 0) {
                this.elements[index++] = leftElements[left++];
            }
        }
    }
    /*** ordered vector end */

    public T insert(T value) { return insert(size, value); }

    public T insert(int index, T value) {
        expand();
        for(int i = size; i > index; i--) {
            elements[i] = elements[i -1];
        }
        elements[index] = value;size++;
        return value;
    }

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

    //Constructors
    public Vector_OrderExArray() {
        this(DEFAULT_CAPACITY);
    }

    public Vector_OrderExArray(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        elements =  new Object[capacity];
    }

    public Vector_OrderExArray(Vector_OrderExArray<T> vectorExArray) {
        this(vectorExArray, 0, vectorExArray.size);
    }

    public Vector_OrderExArray(T[] elements, int lo, int hi) {
        copyFrom(elements, lo, hi);
    }

    public Vector_OrderExArray(Vector_OrderExArray<T> vectorExArray, int lo, int hi) {
        copyFrom((T[])vectorExArray.elements, lo, hi);
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

    public T get(int index) {
        return elementIndex(index);
    }

    private static <T extends Comparable<T>> boolean less(T one, T two) {
        return compareResult(one, two) < 0;
    }

    private static <T extends Comparable<T>> int compareResult(T one, T two ) {
        return one.compareTo(two);
    }


    private T elementIndex(int index) {
        if(index < 0 || size < index) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return (T) elements[index];
    }

    private Object[] elements;
    private int size = 0;
    private int capacity = 0;

    private static final int DEFAULT_CAPACITY = 16;

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                return elementIndex(index++);
            }
        };
    }

}
