package dsa.list;

public interface Queue<E> extends Collection<E> {

    void enqueue(E value);

    /**
    * remove the head element from this queue, the order of element
    * base on the implementation,
    * */
    E dequeue();

    /**
     * get the head element from this queue, the order of element
     * base on the implementation,
     * */
    E front();
}
