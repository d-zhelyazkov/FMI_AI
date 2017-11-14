package dzhelyazkov.travelling_salesman;

import dzhelyazkov.evolutinary_algorithms.EvolutionaryAlgorithm;
import dzhelyazkov.evolutinary_algorithms.impl.RouletteWheelSelector;
import dzhelyazkov.genetic_algorithms.impl.CycleCrossoverOperator;
import dzhelyazkov.genetic_algorithms.impl.SwapMutationOperator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final int POPULATION_SIZE = 100;

    private static final int GENES_COUNT = 20;

    private static final float REPLACE_PART = 0.5f;

    private static final float MUTATE_RATIO = 0.7f;

    private static final int GENERATIONS = 1000000;

    public static void main(String[] args) {
        List<Node> nodes = Stream.generate(new NodesInLineSupplier()).limit(GENES_COUNT).collect(Collectors.toList());
        List<Route> routes = Stream.generate(new RandomRouteSupplier(nodes)).limit(POPULATION_SIZE)
                .collect(Collectors.toList());

        RoutesFitnessRegister routesRegister = new RoutesFitnessRegister();
        RoutesManager routesManager = new RoutesManager(
                REPLACE_PART, MUTATE_RATIO,
                routesRegister,
                (o1, o2) -> Double.compare(o2.doubleValue(), o1.doubleValue()),
                new RouletteWheelSelector<>(routesRegister),
                new CycleCrossoverOperator<>(new RouteBuilder()),
                new SwapMutationOperator<>((int) (GENES_COUNT * MUTATE_RATIO))
        );

        EvolutionaryAlgorithm<Route> evolutionaryAlgorithm = new EvolutionaryAlgorithm<>(routesManager);
        for (int i = 0; i < GENERATIONS; i++) {
            evolutionaryAlgorithm.evolve(routes);

            double minPerimeter = routesRegister.getPerimeter(routes.get(0));
            double maxPerimeter = routesRegister.getPerimeter(routes.get((int) (POPULATION_SIZE * REPLACE_PART - 1)));

            System.out.printf("Gen: %d, Min: %f, Max %f\n", i, minPerimeter, maxPerimeter);
        }

        System.out.printf(Arrays.toString(routes.get(0).getGenes().stream().map(Node::getID).toArray()));
    }

}
