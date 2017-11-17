package dzhelyazkov.travelling_salesman;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TSGeneticAlgorithmSolutionTest {

    @ParameterizedTest
    @ValueSource(ints = { 10, 20, 50, 100 })
    void test(int nodesCount) {
        System.out.printf("Testing Traveler salesman GA solution with %d nodes.\n\n", nodesCount);

        List<Node> nodes = Stream.generate(new NodesInLineSupplier()).limit(nodesCount).collect(Collectors.toList());
        double goalP = NodesInLineSupplier.getGoalPerimeter(nodesCount);

        TSGeneticAlgorithmSolution solution = new TSGeneticAlgorithmSolution(nodes, goalP);
        solution.execute();

        double bestPerimeter = solution.getBestPerimeter();
        Assertions.assertTrue(solution.isGoalAchieved(),
                String.format("Expected %f, but got %f", goalP, bestPerimeter));
    }

}