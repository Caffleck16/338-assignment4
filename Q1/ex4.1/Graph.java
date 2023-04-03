import java.util.*;

public class Graph {
    static class GraphNode {
        private String data;
        private int id;

        GraphNode(String data, int id) {
            this.data = data;
            this.id = id;
        }
        public int getId() {
            return this.id;
        }
        public String getData() {
            return this.data;
        }
    }
    static class Edge {
        private GraphNode node1;
        private GraphNode node2;
        private int weight;

        Edge(GraphNode node1, GraphNode node2, int weight) {
            this.node1 = node1;
            this.node2 = node2;
            this.weight = weight;
        }
        public GraphNode getHome() {
            return this.node1;
        }
        public GraphNode getDest() {
            return this.node2;
        }
        public int getWeight() {
            return this.weight;
        }
    }
    private int V = 1;
    private Map<GraphNode, LinkedList<Edge>> adjList = new HashMap<GraphNode, LinkedList<Edge>>();

    // Method a)
    public GraphNode addNode(String data) {
        GraphNode node = new GraphNode(data, this.V);
        this.adjList.put(node, null);
        this.V++;
        return node;
    }
    // Method b)
    public void removeNode(GraphNode node) {
        this.adjList.remove(node);
        this.V--;
    }
    // Method c)
    public void addEdge(GraphNode n1, GraphNode n2, int weight) {
        Edge e = new Edge(n1, n2, weight);
        Edge r = new Edge(n2, n1, weight);
        LinkedList<Edge> temp1 = adjList.get(n1);
        LinkedList<Edge> temp2 = adjList.get(n2);
        temp1.add(e);
        temp2.add(r);
        this.adjList.put(n1, temp1);
        this.adjList.put(n2, temp2);
    }
    // Method d)
    public void removeEdge(GraphNode n1, GraphNode n2) {
        LinkedList<Edge> temp1 = this.adjList.get(n1);
        for (Edge edge : temp1) {
            if (edge.getDest() == n2) {
                temp1.remove(edge);
            }
        }
        this.adjList.put(n1, temp1);
        LinkedList<Edge> temp2 = this.adjList.get(n2);
        for (Edge edge : temp2) {
            if (edge.getDest() == n1) {
                temp2.remove(edge);
            }
        }
        adjList.put(n2, temp2);
    }

    public int getNumVertices() {
        return V;
    }

}