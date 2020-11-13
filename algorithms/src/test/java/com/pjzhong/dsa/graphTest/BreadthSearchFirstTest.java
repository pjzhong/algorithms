package com.pjzhong.dsa.graphTest;

import com.pjzhong.dsa.graph.*;
import org.junit.Before;
import org.junit.Test;

public class BreadthSearchFirstTest {

  Graph<Integer, String> graph = new GraphAdjacentList<>();

  @Before
  public void before() {
    Vertex<Integer> v1 = new Vertex<>(graph, 1);
    Vertex<Integer> v2 = new Vertex<>(graph, 2);
    Vertex<Integer> v3 = new Vertex<>(graph, 3);
    Vertex<Integer> v4 = new Vertex<>(graph, 4);
    Vertex<Integer> v5 = new Vertex<>(graph, 5);
    Vertex<Integer> v6 = new Vertex<>(graph, 6);
    Vertex<Integer> v7 = new Vertex<>(graph, 7);
    Vertex<Integer> v8 = new Vertex<>(graph, 8);
    Vertex<Integer> v9 = new Vertex<>(graph, 9);
    Vertex<Integer> v10 = new Vertex<>(graph, 10);

    new Edge<>(graph, v1, v2, "1 to 2");
    new Edge<>(graph, v1, v3, "1 to 3");
    new Edge<>(graph, v1, v4, "1 to 4");
    new Edge<>(graph, v2, v6, "2 to 6");
    new Edge<>(graph, v4, v5, "4 to 5");
    new Edge<>(graph, v3, v7, "3 to 7");
    new Edge<>(graph, v3, v8, "3 to 8");
    new Edge<>(graph, v2, v10, "2 to 10");
    new Edge<>(graph, v2, v9, "2 to 9");
    new Edge<>(graph, v5, v3, "5 to 3");
    new Edge<>(graph, v2, v3, "2 to 3");
    new Edge<>(graph, v9, v8, "9 to 8");
  }

  @Test
  public void BFSTest() {
    BreadthFirstSearch<Integer, String> search = new BreadthFirstSearch(graph);

    search.search();
  }
}
