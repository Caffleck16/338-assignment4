public class KruskalAlgorithm {
    public static void main(String[] args) {
        int V = 4, E = 5;
        Graph graph = new Graph(V, E);
        graph.edges[0].src = 0;
        graph.edges[0].dest = 1;
        graph.edges[0].weight = 1;

        graph.edges[1].src = 0;
        graph.edges[1].dest = 2;
        graph.edges[1].weight = 20;

        graph.edges[2].src = 0;
        graph.edges[2].dest = 3;
        graph.edges[2].weight = 5;

        graph.edges[3].src = 1;
        graph.edges[3].dest = 3;
        graph.edges[3].weight = 15;

        graph.edges[4].src = 2;
        graph.edges[4].dest = 3;
        graph.edges[4].weight = 4;

        Graph result = graph.mst();
        for (Graph.Edge edge : result.edges) {
            System.out.println(edge.src + " - " + edge.dest + ": " + edge.weight);
        }
    }
}
