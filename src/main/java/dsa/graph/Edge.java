package dsa.graph;

import lombok.Getter;
import lombok.Setter;


@Setter
public class Edge<T> {
    @Getter private T data;
    @Getter private EdgeType type = EdgeType.UNKNOWN;
    private Vertex from;
    private Vertex to;

    public Edge(Graph G, Vertex from, Vertex to, T data) {
        this.data = data;
        G.insert(this);
        this.from = from;
        this.to = to;
        from.addOutEdges(this);
        to.addInEdges(this);
    }

    public Vertex to() {
        return to;
    }

    public Vertex from() {
        return from;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge<?> edge = (Edge<?>) o;

        if (data != null ? !data.equals(edge.data) : edge.data != null) return false;
        if (from != null ? !from.equals(edge.from) : edge.from != null) return false;
        return to != null ? to.equals(edge.to) : edge.to == null;

    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Edge{");
        sb.append("to=").append(to);
        sb.append(", from=").append(from);
        sb.append(", type=").append(type);
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
