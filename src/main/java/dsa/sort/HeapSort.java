package dsa.sort;

import dsa.list.PriorityQueueLeftHeap;
import dsa.list.Queue;

public class HeapSort<E extends Comparable<E>> extends AbstractSort<E> {
    @Override
    public E[] sort(E[] e, int lo, int hi) {
        Queue<E>  queue = new PriorityQueueLeftHeap<>(e);
        for(int i = --hi; i >= 0; --i) {
            e[i] = queue.dequeue();
        }
        return e;
    }
}
