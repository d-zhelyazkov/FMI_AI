package dzhelyazkov.travelling_salesman;

import dzhelyazkov.genetic_algorithms.ChromosomeBuilder;

import java.util.Arrays;
import java.util.List;

public class RouteBuilder implements ChromosomeBuilder<Node, Route> {

    private List<Node> nodes;

    @Override
    public RouteBuilder setGenes(List<Node> nodes) {
        this.nodes = rearrangeNodes(nodes);
        return this;
    }

    @Override
    public Route build() {
        return new Route(nodes);
    }

    /**
     * Rearange the chromosome in order to set the node with the smallest ID as a starting node
     */
    private List<Node> rearrangeNodes(List<Node> nodes) {
        int smallestNodeIx = 0;
        int nodesCount = nodes.size();
        for (int i = 1; i < nodesCount; i++) {
            if (nodes.get(i).getID() < nodes.get(smallestNodeIx).getID()) {
                smallestNodeIx = i;
            }
        }

        if (smallestNodeIx == 0) {
            return nodes;
        }

        Node[] nodesArr = nodes.toArray(new Node[nodesCount]);
        Node genes[] = new Node[nodesCount];
        int rightCount = nodesCount - smallestNodeIx;
        int leftCount = nodesCount - rightCount;
        System.arraycopy(nodesArr, smallestNodeIx, genes, 0, rightCount);
        System.arraycopy(nodesArr, 0, genes, rightCount, leftCount);

        return Arrays.asList(genes);
    }

}
