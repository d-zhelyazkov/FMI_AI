package dzhelyazkov.travelling_salesman;

import dzhelyazkov.genetic_algorithms.ChromosomeBuilder;

import java.util.List;

public class RouteBuilder implements ChromosomeBuilder<Node, Route> {

    private List<Node> nodes;

    @Override
    public RouteBuilder setGenes(List<Node> nodes) {
        this.nodes = nodes;
        return this;
    }

    @Override
    public Route build() {
        return new Route(nodes);
    }
}
