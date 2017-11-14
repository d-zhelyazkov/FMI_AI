package dzhelyazkov.travelling_salesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class RandomRouteSupplier implements Supplier<Route> {

    private final List<Node> nodes;

    RandomRouteSupplier(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public Route get() {
        Collections.shuffle(nodes);
        return new Route(new ArrayList<>(nodes));
    }
}
