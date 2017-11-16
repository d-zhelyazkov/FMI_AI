package dzhelyazkov.travelling_salesman;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {


    private static final int GENES_COUNT = 20;

    private static final float REPLACE_PART = 0.5f;

    private static final float MUTATE_RATIO = 0.7f;


    public static void main(String[] args) {
        List<Node> nodes = Stream.generate(new NodesInLineSupplier()).limit(GENES_COUNT).collect(Collectors.toList());

        TSGeneticAlgorithmSolution solution = new TSGeneticAlgorithmSolution(REPLACE_PART, MUTATE_RATIO, nodes);
        solution.execute(10000);

    }

}
