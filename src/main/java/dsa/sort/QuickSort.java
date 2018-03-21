package dsa.sort;

import java.util.Random;

public class QuickSort<E extends Comparable<E>> extends AbstractSort<E> {

    private Random random = new Random();

    @Override
    public E[] sort(E[] e, int lo, int hi) {
        return quickSort(e, lo, hi - 1);
    }

    private E[] quickSort(E[] e, int lo, int hi) {
        if(hi - lo < 2) { return e; }
        int mid = partition(e, lo, hi);
        quickSort(e, lo, mid);
        quickSort(e, mid + 1, hi);
        return e;
    }

    private int partition(E[] e, int lo, int hi) {
        swap(e, lo, lo + random.nextInt(hi - lo));
        E pivot = e[lo];
        while(lo < hi) {
            while((lo < hi) && lessEq(pivot, e[hi])) {
                hi--;
            }
            swap(e, hi, lo);

            while( (lo < hi) && lessEq(e[lo], pivot)) {
                lo++;
            }
            swap(e, lo, hi);
        }

        e[lo] = pivot;
        return lo;
    }
}
