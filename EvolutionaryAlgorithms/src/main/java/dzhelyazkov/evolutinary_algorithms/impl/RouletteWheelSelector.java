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
public class RouletteWheelSelector<IndividualType extends Individual> implements CrossoverSelector<IndividualType> {

    private final Function<IndividualType, Number> fitnessFunction;

    private List<IndividualPortion> individualPortions;

    private double portionsSum;

    public RouletteWheelSelector(Function<IndividualType, Number> fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    @Override
    public void setPopulation(List<IndividualType> population) {
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
    public IndividualType get() {
        double randomPortion = new Random().nextDouble() * portionsSum;

        return binarySearch(randomPortion, 0, individualPortions.size() - 1);
    }

    private IndividualType binarySearch(double portion, int leftIx, int rightIx) {
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

        IndividualType individual;

        IndividualPortion(double portion, IndividualType individual) {
            this.portion = portion;
            this.individual = individual;
        }

        @Override
        public int compareTo(IndividualPortion o) {
            return Double.compare(o.portion, portion);
        }
    }

    private class NullIndividualProportion extends IndividualPortion {

        NullIndividualProportion() {
            super(0, null);
        }
    }
}