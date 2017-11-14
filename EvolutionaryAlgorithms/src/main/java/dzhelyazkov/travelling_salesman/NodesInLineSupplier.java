package dzhelyazkov.travelling_salesman;

import dzhelyazkov.utils.Point2D;

import java.util.function.Supplier;

public class NodesInLineSupplier implements Supplier<Node> {

    private static int nodeID = 0;

    @Override
    public Node get() {
        Node node = new Node(nodeID, new Point2D(nodeID, nodeID));
        nodeID++;
        return node;
    }
}
