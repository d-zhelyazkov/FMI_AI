package dzhelyazkov.travelling_salesman;

import dzhelyazkov.genetic_algorithms.Chromosome;

import java.util.List;

public class Route implements Chromosome<Node> {

    private final List<Node> nodes;

    public Route(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public List<Node> getGenes() {
        return nodes;
    }
}
