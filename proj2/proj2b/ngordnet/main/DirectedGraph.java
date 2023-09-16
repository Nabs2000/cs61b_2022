package ngordnet.main;

import java.util.ArrayList;
import java.util.HashMap;

public class DirectedGraph {

    public HashMap<Integer, ArrayList<Integer>> mainGraph;

    public DirectedGraph() {
        mainGraph = new HashMap<>();
    }

    public ArrayList<Integer> get(int vertex) {
        return mainGraph.get(vertex);
    }

    public boolean hasVertex(int vertex) {
        return mainGraph.containsKey(vertex);
    }

    public void addVertex(int vertex) {
        if (!hasVertex(vertex)) {
            mainGraph.put(vertex, new ArrayList<>());
        }
    }

    public void addEdge(int vertex, int connectedVertex) {
        if (!hasVertex(vertex)) {
            addVertex(vertex);
        }
        if (!hasVertex(connectedVertex)) {
            addVertex(connectedVertex);
        }
        mainGraph.get(vertex).add(connectedVertex);
    }

    public void printGraph() {
        System.out.println(mainGraph);
    }
}
