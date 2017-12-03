package dzhelyazkov.travelling_salesman;

import dzhelyazkov.genetic_algorithms.Chromosome;

import java.util.List;

public class Route implements Chromosome<Node> {

    private final List<Node> nodes;

    Route(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public List<Node> getGenes() {
        return nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Route route = (Route) o;

        return nodes.equals(route.nodes);
    }

    @Override
    public int hashCode() {
        return nodes.hashCode();
    }
}
