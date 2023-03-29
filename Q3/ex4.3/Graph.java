
import java.util.*;

public class Graph {
    private int V, E;
    public Edge[] edges;

    class Edge implements Comparable<Edge> {
        int src, dest, weight;

        public int compareTo(Edge edge) {
            return weight - edge.weight;
        }
    }

    public Graph(int v, int e) {
        V = v;
        E = e;
        edges = new Edge[E];
        for (int i = 0; i < E; i++) {
            edges[i] = new Edge();
        }
    }

    private int find(int[] parent, int i) {
        if (parent[i] == -1) {
            return i;
        }
        return find(parent, parent[i]);
    }

    private void union(int[] parent, int x, int y) {
        int xset = find(parent, x);
        int yset = find(parent, y);
        parent[xset] = yset;
    }

    public Graph mst() {
        Graph result = new Graph(V, V - 1);
        Arrays.sort(edges);

        int[] parent = new int[V];
        Arrays.fill(parent, -1);

        int i = 0, e = 0;
        while (e < V - 1 && i < E) {
            Edge edge = edges[i++];

            int x = find(parent, edge.src);
            int y = find(parent, edge.dest);

            if (x != y) {
                result.edges[e++] = edge;
                union(parent, x, y);
            }
        }
        return result;
    }
}
