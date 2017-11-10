package dzhelyazkov.evolutinary_algorithms.impl;

import dzhelyazkov.evolutinary_algorithms.CrossoverSelector;
import dzhelyazkov.evolutinary_algorithms.FitnessFunction;
import dzhelyazkov.evolutinary_algorithms.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Performs Fitness proportionate selection over a population.
 * Individuals having higher fitness values have better chance to get selected.
 */
public class RouletteWheelSelector<IndividualType extends Individual, FitnessType extends Number>
        implements CrossoverSelector<IndividualType> {

    private final FitnessFunction<FitnessType, IndividualType> fitnessFunction;

    private List<IndividualPortion> individualPortions;

    private double portionsSum;

    public RouletteWheelSelector(
            FitnessFunction<FitnessType, IndividualType> fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    @Override
    public void setPopulation(List<IndividualType> population) {
        individualPortions = new ArrayList<>(population.size() + 1);
        individualPortions.add(new NullIndividualProportion());
        individualPortions.addAll(population.stream()
                .map(individual ->
                        new IndividualPortion(fitnessFunction.getFitness(individual).doubleValue(), individual))
                .collect(Collectors.toList()));

        Collections.sort(individualPortions);

        portionsSum = 0;
        for (IndividualPortion individualPortions : this.individualPortions) {
            portionsSum += individualPortions.portion;
            individualPortions.portion = portionsSum;
        }

    }

    @Override
    public IndividualType select() {
        double randomPortion = new Random().nextDouble() * portionsSum;

        return binarySearch(randomPortion, 0, individualPortions.size() - 1);
    }

    private IndividualType binarySearch(double portion, int leftIx, int rightIx) {
        if (leftIx + 1 == rightIx) {
            return individualPortions.get(rightIx).individual;
        }

        int centerIx = (leftIx + rightIx) / 2;
        IndividualPortion centerIndividualPortion = this.individualPortions.get(centerIx);
        int comparisonResult = Double.compare(portion, centerIndividualPortion.portion);
        if (comparisonResult < 0) {
            return binarySearch(portion, leftIx, centerIx);
        } else if (comparisonResult > 0) {
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
            return Double.compare(portion, o.portion);
        }
    }

    private class NullIndividualProportion extends IndividualPortion {

        NullIndividualProportion() {
            super(0, null);
        }
    }
}
