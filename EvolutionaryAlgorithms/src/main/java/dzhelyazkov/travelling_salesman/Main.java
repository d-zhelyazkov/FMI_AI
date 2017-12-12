package dzhelyazkov.travelling_salesman;

import dzhelyazkov.travelling_salesman.node_supplier.ScannerNodeSupplier;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dzhelyazkov.travelling_salesman.TSGeneticAlgorithmSolution.MIN_NODES_COUNT;

public class Main {

    public static void main(String[] args) {

        List<Node> nodes;
        double goalPerimeter;
        try (Scanner scanner = new Scanner(System.in)) {
            int nodesCount;
            do {
                System.out.printf("Number of nodes (min %d): ", MIN_NODES_COUNT);
                nodesCount = scanner.nextInt();
            } while (nodesCount < MIN_NODES_COUNT);

            System.out.printf("Input %d nodes in format 'id x y'\n", nodesCount);
            nodes = Stream.generate(new ScannerNodeSupplier(scanner))
                    .limit(nodesCount).distinct().collect(Collectors.toList());

            System.out.print("Desired perimeter: ");
            goalPerimeter = scanner.nextDouble();
        }

        TSGeneticAlgorithmSolution solution = new TSGeneticAlgorithmSolution(nodes, goalPerimeter);
        solution.execute();

    }

}
