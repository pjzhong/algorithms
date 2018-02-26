package dsa.list;

public interface Queue<T> {

    void enqueue(T value);

    T dequeue();

    T front();
}
