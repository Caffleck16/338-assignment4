package Q1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Map<String, GraphNode> nodes;

    public Graph() {
        nodes = new HashMap<>();
    }

    public GraphNode addNode(String data) {
        GraphNode node = new GraphNode(data);
        nodes.put(data, node);
        return node;
    }

    public void removeNode(GraphNode node) {
        nodes.remove(node.getData());
        for (GraphNode neighbor : node.getNeighbors()) {
            neighbor.removeNeighbor(node);
        }
    }

    public void addEdge(GraphNode n1, GraphNode n2, int weight) {
        n1.addNeighbor(n2, weight);
        n2.addNeighbor(n1, weight);
    }

    public void removeEdge(GraphNode n1, GraphNode n2) {
        n1.removeNeighbor(n2);
        n2.removeNeighbor(n1);
    }

    public static Graph importFromFile(String fileName) {
        Graph graph = new Graph();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            if (line == null || !line.equals("strict graph G {")) {
                return null;
            }
            
            while ((line = reader.readLine()) != null) {
                if (line.equals("}")) {
                    return graph;
                }
                
                String[] parts = line.split("--");
                if (parts.length != 2) {
                    return null;
                }
                
                String n1Data = parts[0].trim();
                String n2Data = parts[1].trim();
                
                int weight = 1;
                int weightIndex = line.indexOf("weight=");
                if (weightIndex != -1) {
                    int endWeightIndex = line.indexOf(';', weightIndex);
                    String weightString = line.substring(weightIndex + 7, endWeightIndex).trim();
                    try {
                        weight = Integer.parseInt(weightString);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
                
                GraphNode n1 = graph.addNode(n1Data);
                GraphNode n2 = graph.addNode(n2Data);
                graph.addEdge(n1, n2, weight);
            }
            
            return null;
        } catch (IOException e) {
            return null;
        }
    }
    
}

class GraphNode {
    private String data;
    private Map<GraphNode, Integer> neighbors;

    public GraphNode(String data) {
        this.data = data;
        neighbors = new HashMap<>();
    }

    public String getData() {
        return data;
    }

    public void addNeighbor(GraphNode neighbor, int weight) {
        neighbors.put(neighbor, weight);
    }

    public void removeNeighbor(GraphNode neighbor) {
        neighbors.remove(neighbor);
    }

    public Set<GraphNode> getNeighbors() {
        return neighbors.keySet();
    }

    public int getWeight(GraphNode neighbor) {
        Integer weight = neighbors.get(neighbor);
        if (weight == null) {
            return 1; // default weight is 1
        }
        return weight;
    }
}

