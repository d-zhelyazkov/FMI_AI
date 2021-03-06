package dzhelyazkov.travelling_salesman.node_supplier;

import dzhelyazkov.travelling_salesman.Node;
import dzhelyazkov.utils.Point2D;

import java.util.function.Supplier;

public class NodesInLineSupplier implements Supplier<Node> {

    private int nodeID = 0;

    @Override
    public Node get() {
        Node node = new Node(nodeID, new Point2D(nodeID, nodeID));
        nodeID++;
        return node;
    }

    public static double getGoalPerimeter(int nodesCount) {
        return (nodesCount - 1) * 2 * Math.sqrt(2);
    }
}
