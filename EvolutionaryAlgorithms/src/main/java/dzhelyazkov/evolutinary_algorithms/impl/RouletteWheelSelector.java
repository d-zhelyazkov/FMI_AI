package dzhelyazkov.evolutinary_algorithms.impl;

import dzhelyazkov.evolutinary_algorithms.CrossoverSelector;
import dzhelyazkov.evolutinary_algorithms.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Performs Fitness proportionate selection over a population.
 * Individuals having higher fitness values have better chance to get selected.
 */
public class RouletteWheelSelector implements CrossoverSelector {

    private final Function<Individual, Number> fitnessFunction;

    private List<IndividualPortion> individualPortions;

    private double portionsSum;

    public RouletteWheelSelector(Function<Individual, Number> fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    @Override
    public void setPopulation(List<Individual> population) {
        individualPortions = new ArrayList<>(population.size() + 1);
        individualPortions.addAll(population.stream()
                .map(individual ->
                        new IndividualPortion(fitnessFunction.apply(individual).doubleValue(), individual))
                .collect(Collectors.toList()));
        individualPortions.add(new NullIndividualProportion());

        Collections.sort(individualPortions);

        portionsSum = 0;
        ListIterator<IndividualPortion> iterator = individualPortions.listIterator(individualPortions.size());
        while (iterator.hasPrevious()) {
            IndividualPortion individualPortions = iterator.previous();
            portionsSum += individualPortions.portion;
            individualPortions.portion = portionsSum;
        }

    }

    @Override
    public Individual select() {
        double randomPortion = new Random().nextDouble() * portionsSum;

        return binarySearch(randomPortion, 0, individualPortions.size() - 1);
    }

    private Individual binarySearch(double portion, int leftIx, int rightIx) {
        if (leftIx + 1 == rightIx) {
            return individualPortions.get(leftIx).individual;
        }

        int centerIx = (leftIx + rightIx) / 2;
        IndividualPortion centerIndividualPortion = this.individualPortions.get(centerIx);
        int comparisonResult = Double.compare(portion, centerIndividualPortion.portion);
        if (comparisonResult > 0) {
            return binarySearch(portion, leftIx, centerIx);
        } else if (comparisonResult < 0) {
            return binarySearch(portion, centerIx, rightIx);
        } else {
            return centerIndividualPortion.individual;
        }
    }

    private class IndividualPortion implements Comparable<IndividualPortion> {

        double portion;

        Individual individual;

        IndividualPortion(double portion, Individual individual) {
            this.portion = portion;
            this.individual = individual;
        }

        @Override
        public int compareTo(IndividualPortion o) {
            return Double.compare(portion, o.portion) * -1;
        }
    }

    private class NullIndividualProportion extends IndividualPortion {

        NullIndividualProportion() {
            super(0, null);
        }
    }
}
