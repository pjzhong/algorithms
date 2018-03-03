package dsa.graph;

public interface Graph<V, E> {

    int V();

    int E();

    void insert(Vertex<V> vertex);

    void insert(Edge<E> edge);

    boolean remove(Edge<E> edge);

    boolean remove(Vertex<V> vertex);

    Iterable<Vertex<V>> vertices();

    Iterable<Edge<E>> edges();

    boolean areAdjacent(Vertex<V> v, Vertex<V> u);
}
