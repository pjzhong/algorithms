package dsa.graph;

import dsa.list.LinkedList;
import dsa.list.Queue;

import java.util.Iterator;

public class BreadthFirstSearch<V, E> {

    private Graph<V, E> graph;

    public BreadthFirstSearch(Graph<V, E> graph) {
        this.graph = graph;
    }

    public void search() {
        Iterator<Vertex<V>> iterator = graph.vertices();
        while(iterator.hasNext()) {
            Vertex<V> vertex = iterator.next();
            if(VertexStatus.UNDISCOVERED == vertex.getStatus()) {
                doSearch(vertex);
            }
        }
    }

    private void doSearch(Vertex<V> v) {
        Queue<Vertex<V>> Q = new LinkedList<>();
        v.setStatus(VertexStatus.DISCOVERED); Q.enqueue(v);
        while(!Q.isEmpty()) {
            Vertex<V> x = Q.dequeue();
            for(Edge e : x.outEdges()) {
                if(e.to().getStatus() == VertexStatus.UNDISCOVERED) {
                    e.to().setStatus(VertexStatus.DISCOVERED);
                    e.setType(EdgeType.TREE);
                    Q.enqueue(e.to());
                } else {
                    e.setType(EdgeType.CROSS);
                }
            }
            x.setStatus(VertexStatus.VISITED);
        }
    }
}
