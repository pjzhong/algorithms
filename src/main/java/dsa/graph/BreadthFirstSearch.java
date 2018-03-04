package dsa.graph;

import dsa.list.LinkedList;
import dsa.list.Queue;


public class BreadthFirstSearch<V, E> {

    private Graph<V, E> graph;

    public BreadthFirstSearch(Graph<V, E> graph) {
        this.graph = graph;
    }

    public void search() {
        for(Vertex<V> vertex : graph.vertices()) {
            if(VertexStatus.UNDISCOVERED == vertex.getStatus()) {
                doSearch(vertex);
            }
        }
    }

    private void doSearch(Vertex<V> v) {
        Queue<Vertex<V>> Q = new LinkedList<>();
        v.setStatus(VertexStatus.DISCOVERED);
        v.setDiscoverTime(System.currentTimeMillis());
        Q.enqueue(v);
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
            x.setFinishedTime(System.currentTimeMillis());
        }
    }
}
