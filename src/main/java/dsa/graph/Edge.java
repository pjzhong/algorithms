package dsa.graph;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Edge<T> {
    private T data;
    private Vertex out;
    private Vertex in;

    public Edge(Graph G, Vertex u, Vertex w, T data) {
        this.data = data;
        G.insert(this);
        this.out = u;
        this.in = w;
        u.addOutEdges(this);
        w.addInEdges(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge<?> edge = (Edge<?>) o;

        if (data != null ? !data.equals(edge.data) : edge.data != null) return false;
        if (out != null ? !out.equals(edge.out) : edge.out != null) return false;
        return in != null ? in.equals(edge.in) : edge.in == null;

    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (out != null ? out.hashCode() : 0);
        result = 31 * result + (in != null ? in.hashCode() : 0);
        return result;
    }
}
