package dsa.list;

public interface Queue<E> extends Collection<E> {

    void enqueue(E value);

    E dequeue();

    E front();
}
