package dzhelyazkov.travelling_salesman;

import dzhelyazkov.evolutinary_algorithms.EvolutionaryAlgorithm;
import dzhelyazkov.evolutinary_algorithms.impl.RouletteWheelSelector;
import dzhelyazkov.genetic_algorithms.impl.CycleCrossoverOperator;
import dzhelyazkov.genetic_algorithms.impl.SwapMutationOperator;
import dzhelyazkov.utils.Double;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TSGeneticAlgorithmSolution {

    static int EXCEPTIONS = 0;

    private static final int GEN_PRINT = 1000;

    private static final int GEN_EQUAL_P = 10000;

    private static final int POPULATION_SIZE = 100;

    private final RoutesManager routesManager;

    private final List<Route> population;

    private int generations = 0;

    private double goalPerimeter = 0;

    TSGeneticAlgorithmSolution(float renewRatio, float mutateRatio, List<Node> nodes, double goalPerimeter) {
        this(renewRatio, mutateRatio, nodes);
        this.goalPerimeter = goalPerimeter;

    }

    TSGeneticAlgorithmSolution(float renewRatio, float mutateRatio, List<Node> nodes) {
        population = Stream.generate(new RandomRouteSupplier(nodes)).limit(POPULATION_SIZE)
                .collect(Collectors.toList());

        RoutesFitnessRegister routesRegister = new RoutesFitnessRegister();
        routesManager = new RoutesManager(
                renewRatio, mutateRatio,
                routesRegister,
                (o1, o2) -> Double.compare(o2.doubleValue(), o1.doubleValue()),
                new RouletteWheelSelector<>(routesRegister),
                new CycleCrossoverOperator<>(new RouteBuilder()),
                new SwapMutationOperator<>((int) (nodes.size() * mutateRatio))
        );
    }

    void execute(int generations) {
        EvolutionaryAlgorithm<Route> evolutionaryAlgorithm = new EvolutionaryAlgorithm<>(routesManager);

        int gensWithEqualPs = 0;

        for (int i = 0; i < generations; i++) {
            evolutionaryAlgorithm.evolve(population);
            this.generations++;

            if ((this.generations % GEN_PRINT) == 0) {
                printPopulationStatistics();
            }

            gensWithEqualPs = (Double.compare(getBestPerimeter(), getWorstPerimeter()) == 0) ? gensWithEqualPs + 1 : 0;

            if (routesManager.isPopulationEvolvedEnough(population)
                    || (Double.compare(getBestPerimeter(), goalPerimeter) != 1)
                    || (Double.compare(gensWithEqualPs, GEN_EQUAL_P) == 0)) {
                System.out.println("Execution terminated.");
                break;
            }
        }

        printPopulationStatistics();
        printBestRoute();
    }

    private double getWorstPerimeter() {

        RoutesFitnessRegister fitnessRegister = routesManager.getFitnessRegister();
        int lastUnchangedRouteIX = routesManager.getLastUnchangedIX(population);
        try {
            return fitnessRegister.getPerimeter(population.get(lastUnchangedRouteIX));
        } catch (NullPointerException e) {
            //TODO fixme
            e.printStackTrace();
            EXCEPTIONS++;
            return java.lang.Double.MAX_VALUE;
        }
    }

    double getBestPerimeter() {

        Route bestRoute = population.get(0);
        return routesManager.getFitnessRegister().getPerimeter(bestRoute);
    }

    private void printBestRoute() {
        double perimeter = getBestPerimeter();
        Object[] nodeIDs = population.get(0).getGenes().stream().map(Node::getID).toArray();
        System.out.printf("GEN: %d: Best route [Perimeter : %f, Route: %s]\n"
                , generations, perimeter, Arrays.toString(nodeIDs));
    }

    private void printPopulationStatistics() {
        System.out.printf("GEN: %d: min P: %f, max P: %f\n", generations, getBestPerimeter(), getWorstPerimeter());
    }

    int getGenerations() {
        return generations;
    }
}
