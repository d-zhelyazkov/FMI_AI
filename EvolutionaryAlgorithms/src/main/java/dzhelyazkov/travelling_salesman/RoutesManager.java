package dzhelyazkov.travelling_salesman;

import dzhelyazkov.evolutinary_algorithms.CrossoverOperator;
import dzhelyazkov.evolutinary_algorithms.CrossoverSelector;
import dzhelyazkov.evolutinary_algorithms.MutationOperator;
import dzhelyazkov.evolutinary_algorithms.PopulationManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoutesManager implements PopulationManager<Route> {

    private final float replaceRatio;

    private final float mutateRatio;

    private final Function<Route, Number> fitnessFunction;

    private final Comparator<Number> fitnessComparator;

    private final CrossoverSelector<Route> crossoverSelector;

    private final CrossoverOperator<Route> crossoverOperator;

    private final MutationOperator<Route> mutationOperator;

    RoutesManager(
            float replaceRatio, float mutateRatio,
            Function<Route, Number> fitnessFunction,
            Comparator<Number> fitnessComparator,
            CrossoverSelector<Route> crossoverSelector,
            CrossoverOperator<Route> crossoverOperator,
            MutationOperator<Route> mutationOperator) {

        this.replaceRatio = replaceRatio;
        this.mutateRatio = mutateRatio;
        this.fitnessFunction = fitnessFunction;
        this.fitnessComparator = fitnessComparator;
        this.crossoverSelector = crossoverSelector;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
    }

    @Override
    public List<Route> createOffspring(List<Route> population) {
        int populationSize = population.size();
        int offspringSize = (int) (populationSize * replaceRatio);
        List<Route> offspring = new ArrayList<>(offspringSize);

        crossoverSelector.setPopulation(population);
        while (offspring.size() < offspringSize) {
            List<Route> parents = Stream.generate(crossoverSelector).distinct().limit(2).collect(Collectors.toList());
            Collection<Route> localOffspring = crossoverOperator.createOffspring(parents);
            offspring.addAll(localOffspring);
        }
        offspringSize = offspring.size();

        int mutatedRoutesCount = (int) (offspringSize * mutateRatio);
        int[] mutatedRoutesIXes =
                new Random().ints(0, offspringSize).distinct().limit(mutatedRoutesCount).toArray();
        for (int mutatedRouteIX : mutatedRoutesIXes) {
            mutationOperator.mutate(offspring.get(mutatedRouteIX));
        }

        return offspring;
    }

    @Override
    public void removeWorstIndividuals(List<Route> population) {
        int populationSize = population.size();
        int individualsToRemove = (int) (populationSize * replaceRatio);
        population.subList(populationSize - individualsToRemove, populationSize).clear();
    }

    @Override
    public void sortPopulation(List<Route> population) {
        population.sort((o1, o2) -> fitnessComparator.compare(fitnessFunction.apply(o1), fitnessFunction.apply(o2)));
    }

    @Override
    public boolean isPopulationEvolvedEnough(List<Route> population) {
        return false;
    }
}
