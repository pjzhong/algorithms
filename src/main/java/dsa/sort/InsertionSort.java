package dsa.sort;


public class InsertionSort<E extends Comparable<E>> extends AbstractSort<E> {

    @Override
    public E[] sort(E[] e, int lo, int hi) {
        for(int outer = lo, i = -1; outer < hi; outer++) {
            E key = e[outer];
            for(i = outer - 1; lo <= i && less(key, e[i]); i-- ) {
                e[i + 1] = e[i];
            }
            e[i + 1] = key;
        }
        return e;
    }
}
