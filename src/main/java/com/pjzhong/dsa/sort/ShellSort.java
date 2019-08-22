package com.pjzhong.dsa.sort;

public class ShellSort<E extends Comparable<E>> extends AbstractSort<E> {

    @Override
    public E[] sort(E[] e, int lo, int hi) {
        int h = 1;
        while(h < (hi / 3)) { h = 3 * h + 1; }

        while(1 <= h) {
            for(int i = h, index = -1; i < hi; i++) {
                E key = e[i];
                for(index = i; h <= index && less(key, e[index - h]); index -= h) {
                    e[index] = e[index - h];
                }
                e[index] = key;
            }
            h /= 3;
        }
        return e;
    }
}
