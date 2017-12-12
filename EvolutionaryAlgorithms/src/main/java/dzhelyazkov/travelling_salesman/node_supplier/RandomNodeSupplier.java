package dzhelyazkov.travelling_salesman.node_supplier;

import dzhelyazkov.travelling_salesman.Node;
import dzhelyazkov.utils.Point2D;

import java.util.Random;
import java.util.function.Supplier;

public class RandomNodeSupplier implements Supplier<Node> {

    private static int MAX_COORD_VALUE = 100;

    private static int COORD_RANGE = MAX_COORD_VALUE * 2;

    private static int nodeID = 0;

    @Override
    public Node get() {
        return new Node(nodeID++, new Point2D(getRandomCoordinate(), getRandomCoordinate()));
    }

    private int getRandomCoordinate() {
        return new Random().nextInt(COORD_RANGE) - MAX_COORD_VALUE;
    }
}
