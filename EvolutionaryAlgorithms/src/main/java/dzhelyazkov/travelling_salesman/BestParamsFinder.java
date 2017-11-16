package dzhelyazkov.travelling_salesman;

import dzhelyazkov.utils.Double;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class BestParamsFinder {

    private static final int REPEATS = 10;

    private static final int BEST_STATISTICS = 10;

    private static final int GENERATIONS = 1000000;

    @ParameterizedTest
    @ValueSource(ints = { 10, 20, 50, 100 })
    void findParams(int nodesCount) {
        double goalP = (nodesCount - 1) * 2 * Math.sqrt(2);
        System.out.printf("FINDING PARAMS FOR %d NODES.\nGoal perimeter: %f\n\n", nodesCount, goalP);
        List<Node> nodes = Stream.generate(new NodesInLineSupplier()).limit(nodesCount).collect(Collectors.toList());

        List<Statistic> statistics = new ArrayList<>(100);
        for (float renewRatio = 0.1f; renewRatio <= 0.5f; renewRatio += 0.1) {
            for (float mutateRatio = 0.1f; mutateRatio <= 0.5f; mutateRatio += 0.1) {

                System.out.printf("\n\nTest with: renew ratio: %.1f, mutate ratio: %.1f\n\n", renewRatio, mutateRatio);
                List<Statistic> statisticsPerTry = new ArrayList<>(REPEATS);
                for (int i = 0; i < REPEATS; i++) {
                    TSGeneticAlgorithmSolution solution =
                            new TSGeneticAlgorithmSolution(renewRatio, mutateRatio, nodes, goalP);
                    solution.execute(GENERATIONS);
                    statisticsPerTry.add(new Statistic(
                            renewRatio, mutateRatio, solution.getBestPerimeter(), solution.getGenerations()));
                }
                Statistic statistic = getAverageStatistic(statisticsPerTry);
                System.out.println(statistic);
                statistics.add(statistic);
            }
        }

        System.out.printf("\n\nBest statistics for %d nodes and goal P %f\n", nodes.size(), goalP);
        Collections.sort(statistics);
        statistics.stream().limit(BEST_STATISTICS).forEach(System.out::println);
    }

    @BeforeEach
    void beforeEach() {
        RoutesManager.EXCEPTIONS = 0;
        TSGeneticAlgorithmSolution.EXCEPTIONS = 0;
    }

    @AfterEach
    void afterAll() {

        if (RoutesManager.EXCEPTIONS != 0
                || TSGeneticAlgorithmSolution.EXCEPTIONS != 0) {
            String err = String.format("\nRoutesManager.EXCEPTIONS: %d\nTSGeneticAlgorithmSolution.EXCEPTIONS: %d\n",
                    RoutesManager.EXCEPTIONS, TSGeneticAlgorithmSolution.EXCEPTIONS);
            throw new RuntimeException(err);
        }
    }

    private Statistic getAverageStatistic(Collection<Statistic> statistics) {
        double pSum = 0;
        int genSum = 0;
        for (Statistic statistic : statistics) {
            pSum += statistic.perimeter;
            genSum += statistic.generations;
        }

        Statistic firstStat = statistics.iterator().next();
        int statsCount = statistics.size();
        return new Statistic(
                firstStat.renewRatio, firstStat.mutateRatio,
                pSum / statsCount, genSum / statsCount);
    }

    private List<Statistic> getBestStatistics(List<Statistic> statistics) {
        List<Statistic> result = new ArrayList<>(statistics.size());

        Collections.sort(statistics);
        Iterator<Statistic> iterator = statistics.iterator();
        Statistic best = iterator.next();
        result.add(best);
        while (iterator.hasNext()) {
            Statistic statistic = iterator.next();
            if (best.compareTo(statistic) == 0) {
                result.add(statistic);
            } else {
                break;
            }
        }

        return result;
    }

    private class Statistic implements Comparable<Statistic> {
        final double renewRatio;

        final double mutateRatio;

        final double perimeter;

        final int generations;

        Statistic(double renewRatio, double mutateRatio, double perimeter, int generations) {
            this.renewRatio = renewRatio;
            this.mutateRatio = mutateRatio;
            this.perimeter = perimeter;
            this.generations = generations;
        }

        @Override
        public int compareTo(Statistic o) {
            int pCompare = Double.compare(perimeter, o.perimeter);
            return (pCompare != 0) ? pCompare : Integer.compare(generations, o.generations);
        }

        @Override
        public String toString() {
            return String.format(
                    "Statistic{ renew = %.1f, mutate = %.1f, perim = %f, generations = %d}",
                    renewRatio, mutateRatio, perimeter, generations);
        }
    }
}
