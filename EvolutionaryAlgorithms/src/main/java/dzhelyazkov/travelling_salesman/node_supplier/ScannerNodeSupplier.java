package dzhelyazkov.travelling_salesman.node_supplier;

import dzhelyazkov.travelling_salesman.Node;
import dzhelyazkov.utils.Point2D;

import java.util.Scanner;
import java.util.function.Supplier;

public class ScannerNodeSupplier implements Supplier<Node> {
    private final Scanner scanner;

    public ScannerNodeSupplier(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Node get() {
        return new Node(scanner.nextInt(), new Point2D(scanner.nextDouble(), scanner.nextDouble()));
    }
}
