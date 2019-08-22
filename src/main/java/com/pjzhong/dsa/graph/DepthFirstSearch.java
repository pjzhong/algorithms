package com.pjzhong.dsa.graph;

public class DepthFirstSearch<V, E> {

  private Graph<V, E> graph;

  public DepthFirstSearch(Graph<V, E> graph) {
    this.graph = graph;
  }

  public void search() {
    for (Vertex<V> vertex : graph.vertices()) {
      if (vertex.getStatus() == VertexStatus.UNDISCOVERED) {
        doSearch(vertex);
      }
    }
  }

  private void doSearch(Vertex<V> vertex) {
    vertex.setStatus(VertexStatus.DISCOVERED);
    vertex.setDiscoverTime(System.currentTimeMillis());
    for (Edge edge : vertex.outEdges()) {
      Vertex<V> to = edge.to();
      switch (to.getStatus()) {
        case UNDISCOVERED: {
          edge.setType(EdgeType.TREE);
          doSearch(to);
        }
        break;
        case DISCOVERED: {
          edge.setType(EdgeType.BACKWARD);
        }
        break;
        default: {//to已访问(VISITED, 有向图)， 则视承袭关系分为前向边还是跨边
          //1.vertex先与to被发现，并且vertex到to不止一条路径
          //2.vertex
          edge.setType(
              vertex.getDiscoverTime() < to.getDiscoverTime() ? EdgeType.FORWARD : EdgeType.CROSS);
        }
      }
    }

    vertex.setStatus(VertexStatus.VISITED);
    vertex.setFinishedTime(System.currentTimeMillis());
  }
}
