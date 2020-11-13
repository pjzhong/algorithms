package com.pjzhong.dsa.graph;

import com.pjzhong.dsa.list.LinkedList;
import com.pjzhong.dsa.list.List;

public class Vertex<T> {

  private T data;
  private VertexStatus status = VertexStatus.UNDISCOVERED;
  private long discoverTime;
  private long finishedTime;
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

  public Iterable<Edge> outEdges() {
    return outEdge;
  }

  public Iterable<Edge> inEdges() {
    return inEdges;
  }

  public void addOutEdges(Edge edge) {
    outEdge.add(edge);
  }

  public void addInEdges(Edge edge) {
    inEdges.add(edge);
  }

  public boolean removeOutEdge(Edge edge) {
    edge.setFrom(null);
    return outEdge.remove(edge);
  }

  public boolean removeInEdge(Edge edge) {
    edge.setTo(null);
    return inEdges.remove(edge);
  }

  public VertexStatus getStatus() {
    return status;
  }

  public void setStatus(VertexStatus status) {
    this.status = status;
  }

  public long getDiscoverTime() {
    return discoverTime;
  }

  public void setDiscoverTime(long discoverTime) {
    this.discoverTime = discoverTime;
  }

  public long getFinishedTime() {
    return finishedTime;
  }

  public void setFinishedTime(long finishedTime) {
    this.finishedTime = finishedTime;
  }

  public T getData() {
    return data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Vertex<?> vertex = (Vertex<?>) o;

    if (data != null ? !data.equals(vertex.data) : vertex.data != null) {
      return false;
    }
    if (inEdges != null ? !inEdges.equals(vertex.inEdges) : vertex.inEdges != null) {
      return false;
    }
    return outEdge != null ? outEdge.equals(vertex.outEdge) : vertex.outEdge == null;

  }

  @Override
  public int hashCode() {
    int result = data != null ? data.hashCode() : 0;
    result = 31 * result + (inEdges != null ? inEdges.hashCode() : 0);
    result = 31 * result + (outEdge != null ? outEdge.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Vertex{");
    sb.append("data=").append(data);
    sb.append(", status=").append(status);
    sb.append('}');
    return sb.toString();
  }
}
