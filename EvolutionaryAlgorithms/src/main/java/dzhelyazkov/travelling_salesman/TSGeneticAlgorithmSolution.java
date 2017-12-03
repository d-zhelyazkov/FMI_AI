package dzhelyazkov.travelling_salesman;

import dzhelyazkov.evolutinary_algorithms.EvolutionaryAlgorithm;
import dzhelyazkov.evolutinary_algorithms.impl.RouletteWheelSelector;
import dzhelyazkov.genetic_algorithms.impl.CycleCrossoverOperator;
import dzhelyazkov.genetic_algorithms.impl.SwapMutationOperator;
import dzhelyazkov.utils.Double;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TSGeneticAlgorithmSolution {

    public static final int MIN_NODES_COUNT = 7;

    private static final int GEN_PRINT = 100;

    private static final int GEN_LOCEXTR_CHECK = 10000;

    private static final int POPULATION_SIZE = 100;

    private static final int GENERATIONS = Integer.MAX_VALUE;

    private static final float RENEW_RATIO = 0.75f;

    private static final float MUTATE_RATIO = 0.50f;

    private final RoutesManager routesManager;

    private final List<Route> population;

    private double goalPerimeter = 0;

    private int generations = 0;

    private boolean localExtreme = false;

    TSGeneticAlgorithmSolution(List<Node> nodes, double goalP) {
        this(RENEW_RATIO, MUTATE_RATIO, nodes, goalP);
    }

    TSGeneticAlgorithmSolution(float renewRatio, float mutateRatio, List<Node> nodes, double goalPerimeter) {
        this(renewRatio, mutateRatio, nodes);
        this.goalPerimeter = goalPerimeter;

    }

    TSGeneticAlgorithmSolution(float renewRatio, float mutateRatio, List<Node> nodes) {
        int nodesCount = nodes.size();
        population = Stream.generate(new RandomRouteSupplier(nodes)).distinct().limit(POPULATION_SIZE)
                .collect(Collectors.toList());

        RoutesFitnessRegister routesRegister = new RoutesFitnessRegister();
        routesManager = new RoutesManager(
                renewRatio, mutateRatio,
                routesRegister,
                (f1, f2) -> Double.compare(f2.doubleValue(), f1.doubleValue()),
                new RouletteWheelSelector<>(routesRegister),
                new CycleCrossoverOperator<>(new RouteBuilder()),
                new SwapMutationOperator((int) (nodesCount * mutateRatio))
        );
    }

    void execute() {
        execute(GENERATIONS);
    }

    void execute(int generations) {
        localExtreme = false;

        EvolutionaryAlgorithm<Route> evolutionaryAlgorithm = new EvolutionaryAlgorithm<>(routesManager);

        evolutionaryAlgorithm.evolve(population);
        this.generations++;

        PopulationSnapshot snapshot = new PopulationSnapshot();

        for (int i = 1; i < generations; i++) {
            evolutionaryAlgorithm.evolve(population);
            this.generations++;

            if ((i % GEN_PRINT) == 0) {
                printPopulationStatistics();
            }

            if (isGoalAchieved()) {
                System.out.println("Goal accomplished!.");
                break;
            }

            if ((i % GEN_LOCEXTR_CHECK) == 0) {
                PopulationSnapshot newSnapshot = new PopulationSnapshot();
                if (newSnapshot.equals(snapshot)) {
                    localExtreme = true;
                    System.out.println("Execution terminated -> Local extreme.");
                    break;
                }

                snapshot = newSnapshot;
            }
        }

        printPopulationStatistics();
        printBestRoute();
    }

    private double getWorstPerimeter() {

        RoutesFitnessRegister fitnessRegister = routesManager.getFitnessRegister();
        int lastUnchangedRouteIX = routesManager.getLastUnchangedIX(population);
        return fitnessRegister.getPerimeter(population.get(lastUnchangedRouteIX));
    }

    double getBestPerimeter() {

        Route bestRoute = population.get(0);
        return routesManager.getFitnessRegister().getPerimeter(bestRoute);
    }

    private void printBestRoute() {
        double perimeter = getBestPerimeter();
        System.out.printf("GEN: %d: Best route [Perimeter : %f, Route: %s]\n"
                , generations, perimeter, population.get(0));
    }

    private void printPopulationStatistics() {
        System.out.printf("GEN: %d: min P: %f, max P: %f\n", generations, getBestPerimeter(), getWorstPerimeter());
    }

    int getGenerations() {
        return generations;
    }

    boolean isInLocalExtreme() {
        return localExtreme;
    }

    boolean isGoalAchieved() {
        return (Double.compare(getBestPerimeter(), goalPerimeter) != 1);
    }

    private class PopulationSnapshot {
        final double bestPerimeter = getBestPerimeter();

        final double worstPerimeter = getWorstPerimeter();

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            PopulationSnapshot that = (PopulationSnapshot) o;

            return Double.equal(that.bestPerimeter, bestPerimeter)
                    && Double.equal(that.worstPerimeter, worstPerimeter);
        }
    }
}
