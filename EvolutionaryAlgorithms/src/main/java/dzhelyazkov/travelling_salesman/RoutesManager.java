package dzhelyazkov.travelling_salesman;

import dzhelyazkov.evolutinary_algorithms.CrossoverOperator;
import dzhelyazkov.evolutinary_algorithms.CrossoverSelector;
import dzhelyazkov.evolutinary_algorithms.MutationOperator;
import dzhelyazkov.evolutinary_algorithms.PopulationManager;
import dzhelyazkov.genetic_algorithms.Chromosome;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoutesManager implements PopulationManager<Route> {

    static int EXCEPTIONS = 0;

    private final float replaceRatio;

    private final float mutateRatio;

    private final RoutesFitnessRegister fitnessRegister;

    private final Comparator<Number> fitnessComparator;

    private final CrossoverSelector<Route> crossoverSelector;

    private final CrossoverOperator<Route> crossoverOperator;

    private final MutationOperator<Chromosome> mutationOperator;

    RoutesManager(
            float replaceRatio, float mutateRatio,
            RoutesFitnessRegister fitnessRegister,
            Comparator<Number> fitnessComparator,
            CrossoverSelector<Route> crossoverSelector,
            CrossoverOperator<Route> crossoverOperator,
            MutationOperator<Chromosome> mutationOperator) {

        this.replaceRatio = replaceRatio;
        this.mutateRatio = mutateRatio;
        this.fitnessRegister = fitnessRegister;
        this.fitnessComparator = fitnessComparator;
        this.crossoverSelector = crossoverSelector;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
    }

    @Override
    public List<Route> createOffspring(List<Route> population) {
        int populationSize = population.size();
        int offspringSize = getRenewSize(populationSize);
        if(offspringSize == 0) {
            return Collections.emptyList();
        }

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
        List<Route> routesForRemoval = population.subList(populationSize - getRenewSize(populationSize), populationSize);
        for (Route route : routesForRemoval) {
            fitnessRegister.remove(route);
        }

        routesForRemoval.clear();
    }

    @Override
    public void sortPopulation(List<Route> population) {
        try {
            population
                    .sort((o1, o2) -> fitnessComparator.compare(fitnessRegister.apply(o1), fitnessRegister.apply(o2)));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            //TODO fixme
            EXCEPTIONS ++;
        }
    }

    @Override
    public boolean isPopulationEvolvedEnough(List<Route> population) {
        return false;
    }

    RoutesFitnessRegister getFitnessRegister() {
        return fitnessRegister;
    }

    int getLastUnchangedIX(List<Route> population) {
        int size = population.size();
        return size - getRenewSize(size) - 1;
    }

    private int getRenewSize(int populationSize) {
        return (int) (populationSize * replaceRatio);
    }

}
