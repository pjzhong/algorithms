package dsa.graph;

import dsa.list.ArrayList;
import dsa.list.List;

import java.util.Iterator;

public class GraphAdjacentList<V, E> implements Graph<V, E> {

    private List<Vertex<V>> vertices = new ArrayList<>();
    private List<Edge<E>> edges = new ArrayList<>();

    @Override
    public int V() {
        return vertices.size();
    }

    @Override
    public int E() {
        return edges.size();
    }

    @Override
    public void insert(Vertex<V> vertex) {
        vertices.add(vertex);
    }

    @Override
    public void insert(Edge<E> edge) {
        edges.add(edge);
    }

    @Override
    public Iterator<Vertex<V>> vertices() {
        return vertices.listIterator();
    }

    @Override
    public Iterator<Edge<E>> edges() {
        return edges.listIterator();
    }

    @Override
    public boolean areAdjacent(Vertex<V> v, Vertex<V>  u) {
        return (null != edgeFromTo(v, u));
    }

    public Edge edgeFromTo(Vertex<V> u, Vertex<V> v) {
        for(Iterator<Edge> it = u.outEdges(); it.hasNext();) {
            Edge e = it.next();
            if(v.equals(e.getIn())) {
                return e;
            }
        }

        return null;
    }

    @Override
    public boolean remove(Edge e) {
        e.getOut().removeOutEdge(e);
        e.getIn().removeInEdge(e);
        return edges.remove(e);
    }


    @Override
    public boolean remove(Vertex<V> vertex) {
        Iterator<Edge> out = vertex.outEdges();
        while(out.hasNext()) { remove(out.next()); }

        Iterator<Edge> in = vertex.inEdges();
        while(in.hasNext()) { remove(in.next()); }

        return vertices.remove(vertex);
    }
}
