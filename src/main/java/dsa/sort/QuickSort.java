package dsa.sort;

import java.util.Random;

public class QuickSort<E extends Comparable<E>> extends AbstractSort<E> {

    private Random random = new Random();

    @Override
    public E[] sort(E[] e, int lo, int hi) {
        return quickSort(e, lo, hi - 1);
    }

    private E[] quickSort(E[] e, int lo, int hi) {//[lo, hi]
        if(hi - lo < 1) { return e; }
        int mid = partition(e, lo, hi);
        quickSort(e, lo, mid);
        quickSort(e, mid + 1, hi);
        return e;
    }

    /***
     * this implementation is more simpler than the previous
     * in this version, @param e would  be separate into four parts
     * e = [lo] + L(lo, mi] + G(mi, k) + U[k, hi]
     * L < pivot <= G
     * for more info, see the link below
     * http://www.xuetangx.com/courses/course-v1:TsinghuaX+30240184_2X+sp/courseware/c15aad6e2dac4250934ea61d71deafd9/ab3cca35d795409f9693edd6cf25f192/
     */
    private int partition(E[] e, int lo, int hi) {//[lo, hi]
        swap(e, lo, lo + random.nextInt(hi - lo + 1));
        E pivot = e[lo];
        int mi = lo;
        for(int k = lo + 1; k <= hi; ++k) {
            if(less(e[k], pivot)) {
                swap(e, ++mi, k);
            }
        }
        swap(e, lo, mi);
        return mi;
    }
}
