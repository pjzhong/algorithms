package dsa.graph;

import dsa.list.LinkedList;
import dsa.list.List;
import lombok.Getter;

import java.util.Iterator;

public class Vertex<T> {
    @Getter private T data;
    private List<Edge> inEdges;
    private List<Edge> outEdge;

    public Vertex(Graph G, T data) {
        this.data = data;
        G.insert(this);
        inEdges = new LinkedList<>();
        outEdge = new LinkedList<>();
    }

    public Vertex(T data) {
        this.data = data;
    }

    public int outDegrees() {
        return outEdge.size();
    }

    public int inDegrees() {
        return inEdges.size();
    }

    public Iterator<Edge> outEdges() {
        return outEdge.listIterator();
    }

    public Iterator<Edge> inEdges() {
        return inEdges.listIterator();
    }

    public void addOutEdges(Edge edge) {
        outEdge.add(edge);
    }

    public void addInEdges(Edge edge) {
        inEdges.add(edge);
    }

    public boolean removeOutEdge(Edge edge) {
        edge.setOut(null);
        return outEdge.remove(edge);
    }

    public boolean removeInEdge(Edge edge) {
        edge.setIn(null);
        return inEdges.remove(edge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex<?> vertex = (Vertex<?>) o;

        if (data != null ? !data.equals(vertex.data) : vertex.data != null) return false;
        if (inEdges != null ? !inEdges.equals(vertex.inEdges) : vertex.inEdges != null) return false;
        return outEdge != null ? outEdge.equals(vertex.outEdge) : vertex.outEdge == null;

    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (inEdges != null ? inEdges.hashCode() : 0);
        result = 31 * result + (outEdge != null ? outEdge.hashCode() : 0);
        return result;
    }
}
