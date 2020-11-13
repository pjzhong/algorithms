package com.pjzhong.dsa.graphTest;

import com.pjzhong.dsa.graph.Edge;
import com.pjzhong.dsa.graph.Graph;
import com.pjzhong.dsa.graph.GraphAdjacentList;
import com.pjzhong.dsa.graph.Vertex;
import org.junit.Assert;
import org.junit.Test;

public class GraphTest {

    @Test
    public void vertexTest() {
        Graph<Integer, Integer> graph = new GraphAdjacentList<>();
        Vertex<Integer> vertexOne = new Vertex<Integer>(graph, 1);
        Vertex<Integer> vertexTwo = new Vertex<Integer>(graph, 2);

        Edge<String> fromOneToTwo = new Edge<>(graph, vertexOne, vertexTwo, "fromOneToTwo");
        Assert.assertEquals(vertexOne, fromOneToTwo.from());
        Assert.assertEquals(vertexTwo, fromOneToTwo.to());
        Assert.assertEquals(1, vertexOne.outDegrees());
        Assert.assertEquals(0, vertexOne.inDegrees());
        Assert.assertEquals(1, vertexTwo.inDegrees());
        Assert.assertEquals(0, vertexTwo.outDegrees());
    }

    @Test
    public void graphAdjacentTest() {
        Graph<Integer, Integer> graph = new GraphAdjacentList<>();
        Vertex<Integer> vertexOne = new Vertex<Integer>(graph, 1);
        Vertex<Integer> vertexTwo = new Vertex<Integer>(graph, 2);

        Edge<String> fromOneToTwo = new Edge<>(graph, vertexOne, vertexTwo, "fromOneToTwo");

        Assert.assertTrue(graph.areAdjacent(vertexOne, vertexTwo));
        Assert.assertFalse(graph.areAdjacent(vertexTwo, vertexOne));
    }

    @Test
    public void graphRemoveEdgeTest() {
        Graph<Integer, String> graph = new GraphAdjacentList<>();
        Vertex<Integer> vertexOne = new Vertex<Integer>(graph, 1);
        Vertex<Integer> vertexTwo = new Vertex<Integer>(graph, 2);

        Edge<String> fromOneToTwo = new Edge<>(graph, vertexOne, vertexTwo, "fromOneToTwo");
        Assert.assertEquals(1, vertexOne.outDegrees());
        Assert.assertEquals(1, vertexTwo.inDegrees());
        graph.remove(fromOneToTwo);

        Assert.assertFalse(graph.areAdjacent(vertexOne, vertexTwo));
        Assert.assertEquals(0, vertexOne.outDegrees());
        Assert.assertEquals(0, vertexOne.inDegrees());
        Assert.assertEquals(0, vertexTwo.outDegrees());
        Assert.assertEquals(0, vertexTwo.inDegrees());
    }

    @Test
    public void graphRemoveVertexTest() {
        Graph<Integer, String> graph = new GraphAdjacentList<>();
        Vertex<Integer> vertexOne = new Vertex<Integer>(graph, 1);
        Vertex<Integer> vertexTwo = new Vertex<Integer>(graph, 2);

        Edge<String> fromOneToTwo = new Edge<>(graph, vertexOne, vertexTwo, "fromOneToTwo");
        graph.remove(vertexTwo);

        Assert.assertFalse(graph.areAdjacent(vertexOne, vertexTwo));
        Assert.assertNotEquals(fromOneToTwo.to(), vertexTwo);
        Assert.assertNotEquals(fromOneToTwo.from(), vertexOne);
    }

}
