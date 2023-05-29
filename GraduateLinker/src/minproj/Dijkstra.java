package minproj;

//package com.jwetherell.algorithms.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

//import com.jwetherell.algorithms.data_structures.Graph;

/**
 * Dijkstra's shortest path. Only works on non-negative path weights. Returns a
 * tuple of total cost of shortest path and the path.
 * <p>
 * Worst case: O(|E| + |V| log |V|)
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm">Dijkstra's Algorithm (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Dijkstra {

    private Dijkstra() { }

    public static Map<Graph.Vertex<String>, Graph.CostPathPair<String>> getShortestPaths(Graph<String> graph, Graph.Vertex<String> start) {
        final Map<Graph.Vertex<String>, List<Graph.Edge<String>>> paths = new HashMap<Graph.Vertex<String>, List<Graph.Edge<String>>>();
        final Map<Graph.Vertex<String>, Graph.CostVertexPair<String>> costs = new HashMap<Graph.Vertex<String>, Graph.CostVertexPair<String>>();

        getShortestPath(graph, start, null, paths, costs);

        final Map<Graph.Vertex<String>, Graph.CostPathPair<String>> map = new HashMap<Graph.Vertex<String>, Graph.CostPathPair<String>>();
        for (Graph.CostVertexPair<String> pair : costs.values()) {
            int cost = pair.getCost();
            Graph.Vertex<String> vertex = pair.getVertex();
            List<Graph.Edge<String>> path = paths.get(vertex);
            map.put(vertex, new Graph.CostPathPair<String>(cost, path));
        }
        return map;
    }

    public static Graph.CostPathPair<String> getShortestPath(Graph<String> graph, Graph.Vertex<String> start, Graph.Vertex<String> end) {
        if (graph == null)
            throw (new NullPointerException("Graph must be non-NULL."));

        // Dijkstra's algorithm only works on positive cost graphs
        final boolean hasNegativeEdge = checkForNegativeEdges(graph.getVertices());
        if (hasNegativeEdge)
            throw (new IllegalArgumentException("Negative cost Edges are not allowed."));

        final Map<Graph.Vertex<String>, List<Graph.Edge<String>>> paths = new HashMap<Graph.Vertex<String>, List<Graph.Edge<String>>>();
        final Map<Graph.Vertex<String>, Graph.CostVertexPair<String>> costs = new HashMap<Graph.Vertex<String>, Graph.CostVertexPair<String>>();
        return getShortestPath(graph, start, end, paths, costs);
    }

    private static Graph.CostPathPair<String> getShortestPath(Graph<String> graph, 
                                                              Graph.Vertex<String> start, Graph.Vertex<String> end,
                                                              Map<Graph.Vertex<String>, List<Graph.Edge<String>>> paths,
                                                              Map<Graph.Vertex<String>, Graph.CostVertexPair<String>> costs) {
        if (graph == null)
            throw (new NullPointerException("Graph must be non-NULL."));
        if (start == null)
            throw (new NullPointerException("start must be non-NULL."));

        // Dijkstra's algorithm only works on positive cost graphs
        boolean hasNegativeEdge = checkForNegativeEdges(graph.getVertices());
        if (hasNegativeEdge)
            throw (new IllegalArgumentException("Negative cost Edges are not allowed."));

        for (Graph.Vertex<String> v : graph.getVertices())
            paths.put(v, new ArrayList<Graph.Edge<String>>());

        for (Graph.Vertex<String> v : graph.getVertices()) {
            if (v.equals(start))
                costs.put(v, new Graph.CostVertexPair<String>(0, v));
            else
                costs.put(v, new Graph.CostVertexPair<String>(Integer.MAX_VALUE, v));
        }

        final Queue<Graph.CostVertexPair<String>> unvisited = new PriorityQueue<Graph.CostVertexPair<String>>();
        unvisited.add(costs.get(start));

        while (!unvisited.isEmpty()) {
            final Graph.CostVertexPair<String> pair = unvisited.remove();
            final Graph.Vertex<String> vertex = pair.getVertex();

            // Compute costs from current vertex to all reachable vertices which haven't been visited
            for (Graph.Edge<String> e : vertex.getEdges()) {
                final Graph.CostVertexPair<String> toPair = costs.get(e.getToVertex()); // O(1)
                final Graph.CostVertexPair<String> lowestCostToThisVertex = costs.get(vertex); // O(1)
                final int cost = lowestCostToThisVertex.getCost() + e.getCost();
                if (toPair.getCost() == Integer.MAX_VALUE) {
                    // Haven't seen this vertex yet

                    // Need to remove the pair and re-insert, so the priority queue keeps it's invariants
                    unvisited.remove(toPair); // O(n)
                    toPair.setCost(cost);
                    unvisited.add(toPair); // O(log n)

                    // Update the paths
                    List<Graph.Edge<String>> set = paths.get(e.getToVertex()); // O(log n)
                    set.addAll(paths.get(e.getFromVertex())); // O(log n)
                    set.add(e);
                } else if (cost < toPair.getCost()) {
                    // Found a shorter path to a reachable vertex

                    // Need to remove the pair and re-insert, so the priority queue keeps it's invariants
                    unvisited.remove(toPair); // O(n)
                    toPair.setCost(cost);
                    unvisited.add(toPair); // O(log n)

                    // Update the paths
                    List<Graph.Edge<String>> set = paths.get(e.getToVertex()); // O(log n)
                    set.clear();
                    set.addAll(paths.get(e.getFromVertex())); // O(log n)
                    set.add(e);
                }
            }

            // Termination conditions
            if (end != null && vertex.equals(end)) {
                // We are looking for shortest path to a specific vertex, we found it.
                break;
            }
        }

        if (end != null) {
            final Graph.CostVertexPair<String> pair = costs.get(end);
            final List<Graph.Edge<String>> set = paths.get(end);
            return (new Graph.CostPathPair<String>(pair.getCost(), set));
        }
        return null;
    }

    private static boolean checkForNegativeEdges(Collection<Graph.Vertex<String>> vertices) {
    	for (Graph.Vertex<String> v : vertices) {
            for (Graph.Edge<String> e : v.getEdges()) {
            	if (e.getCost() < 0)
                    return true;
            }
        }
        return false;
    }
}