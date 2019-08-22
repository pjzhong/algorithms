package com.pjzhong.dsa.sort;

public class SelectionSort<E extends Comparable<E>> extends AbstractSort<E> {

    @Override
    public E[] sort(E[] e, int lo, int hi) {
        for(int outer = lo; outer < hi; outer++) {
            int min = outer;
            for(int i = outer + 1; i < hi; i++) {
                if(less(e[i], e[min])) {
                    min = i;
                }
            }

            swap(e, outer, min);
        }

        return e;
    }
}
