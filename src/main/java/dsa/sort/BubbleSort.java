package dsa.sort;

public class BubbleSort<E extends Comparable<E>> extends AbstractSort<E> {

    @Override
    public E[] sort(E[] e, int lo, int hi) {
        boolean sorted = false;
        for(int i = 0, size = hi - lo; i < size && (sorted = !sorted); i++) {
            for(int n = (lo + 1), length = hi - i; n < length; n++) {
                if(less(e[n], e[n - 1])) {
                    swap(e, n, n - 1);
                    sorted = false;
                }
            }
        }

        return e;
    }
}

