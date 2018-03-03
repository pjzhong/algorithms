package dsa.graph;

import java.util.Iterator;

public interface Graph<V, E> {

    int V();

    int E();

    void insert(Vertex<V> vertex);

    void insert(Edge<E> edge);

    boolean remove(Edge<E> edge);

    boolean remove(Vertex<V> vertex);

    Iterator<Vertex<V>> vertices();

    Iterator<Edge<E>> edges();

    boolean areAdjacent(Vertex<V> v, Vertex<V> u);
}
