import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    static class GraphNode {
        private String data;

        GraphNode(String data) {
            this.data = data;
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
    private Map<GraphNode, LinkedList<Edge>> adjList = new HashMap<GraphNode, LinkedList<Edge>>();

    // Method a)
    public GraphNode addNode(String data) {
        GraphNode node = new GraphNode(data);
        this.adjList.put(node, null);
        return node;
    }
    // Method b)
    public void removeNode(GraphNode node) {
        this.adjList.remove(node);
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
    // Method e)
    public static Graph importFromFile(String file) throws IOException {
        Graph t = new Graph();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        LinkedList<GraphNode> nodeList;
        String line = reader.readLine();
        if (!line.contains("strict")) {
            System.out.println("Graph is not undirected");
            reader.close();
            return null;
        }
        while (!(line.contains("}"))) {
            line = reader.readLine();
            String homeNode = "";
            String destNode = "";
            String weightString = "";
            int weight = 0;
            if (line.contains("node")) {

               if (line.contains("weight")) {

                int dashIndex = line.indexOf("--");
                homeNode = line.substring(0, dashIndex).trim();
                
                int bracketIndex = line.indexOf("[");
                destNode = line.substring(dashIndex + 2, bracketIndex).trim();

                int weightIndex = line.indexOf("weight=");
                weightString = line.substring(weightIndex + 7, line.length() - 2);
                weight = Integer.parseInt(weightString);

               } else {

                int dashIndex = line.indexOf("--");
                homeNode = line.substring(0, dashIndex).trim();
                
                int semiIndex = line.indexOf(";");
                destNode = line.substring(dashIndex + 2, semiIndex).trim();

                weight = 1;
               }
               GraphNode n1 = new GraphNode(homeNode);
               GraphNode n2 = new GraphNode(destNode);
               t.addEdge(n1, n2, weight);
            }
            
        } 
        reader.close();

        return null;
    } 

}