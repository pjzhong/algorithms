package concurrency.atomic_variable;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Insertion sample of no-blocking LinkedQueue
 * Michael-Scoot Non-blocking Queue Algorithm(Michael and Scoot, 1996)
 *
 * see the links below
 * http://cs.rochester.edu/research/synchronization/pseudocode/queues.html#nbq
 * */
public class LinkedQueue<E> {
    private static class Node<E> {
        private E item;
        final AtomicReference<Node<E>> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<>(next);
        }
    }

    private final AtomicReference<Node<E>> head;
    private final AtomicReference<Node<E>> tail;

    public LinkedQueue() {
        Node<E> sentinel = new Node<>(null, null);
        head = new AtomicReference<>(sentinel);
        tail = new AtomicReference<>(sentinel);
    }

    public boolean enqueue(E item) {
        Node<E> newNode = new Node<>(item, null);
        while(true) {
            Node<E> curTail = tail.get(), tailNext = curTail.next.get();
            if(curTail == tail.get()) {
                if(tailNext != null) {
                    //Queue in intermediate state, advance tail
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    //In quiescent state, try insertion new node
                    if(curTail.next.compareAndSet(null, newNode)) {
                        //Insertion succeeded, try advancing tail
                        tail.compareAndSet(curTail, newNode);
                        return true;
                    }
                }
            }
        }
    }

    //How can I verify the correctness
    public E dequeue() {
        while(true) {
            Node<E> curHead = head.get(), curTail = tail.get(), next = curHead.next.get();
            if(curHead == head.get()) {
                if(curHead == curTail) {
                    if(next == null) {
                        return null;
                    } else {
                        tail.compareAndSet(curTail, next);
                    }
                } else {
                    E item = next.item;
                    if(head.compareAndSet(curHead, next)) {
                        next.item = null;
                        return item;
                    }
                }
            }
        }
    }
}
