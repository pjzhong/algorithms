package dsa;

@SuppressWarnings("unchcked")
public class Vector_ExArray<T>  implements Vector<T> {


    public T insert(T value) { return insert(size, value); }

    @Override
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
    public Vector_ExArray() {
        this(DEFAULT_CAPACITY);
    }

    public Vector_ExArray(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        elements =  new Object[capacity];
    }

    public Vector_ExArray(Vector_ExArray<T> vectorExArray) {
        this(vectorExArray, 0, vectorExArray.size);
    }

    public Vector_ExArray(T[] elements, int lo, int hi) {
        copyFrom(elements, lo, hi);
    }

    public Vector_ExArray(Vector_ExArray<T> vectorExArray, int lo, int hi) {
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




    private T elementIndex(int index) {
        if(0 > index || index > size) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return (T) elements[index];
    }

    private Object[] elements;
    private int size = 0;
    private int capacity = 0;

    private static final int DEFAULT_CAPACITY = 16;
}
