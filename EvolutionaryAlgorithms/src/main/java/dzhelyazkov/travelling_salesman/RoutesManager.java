package dzhelyazkov.travelling_salesman;

import dzhelyazkov.evolutinary_algorithms.CrossoverOperator;
import dzhelyazkov.evolutinary_algorithms.CrossoverSelector;
import dzhelyazkov.evolutinary_algorithms.MutationOperator;
import dzhelyazkov.evolutinary_algorithms.PopulationManager;
import dzhelyazkov.genetic_algorithms.Chromosome;

import java.util.ArrayList;
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

    private final Comparator<Route> routeComparator;

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
        this.crossoverSelector = crossoverSelector;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;

        this.routeComparator =
                (r1, r2) -> fitnessComparator.compare(fitnessRegister.apply(r1), fitnessRegister.apply(r2));
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
        //create (offspringSize / 2) different pairs of parents and cross them
        Stream.generate(() -> Stream.generate(crossoverSelector).distinct().limit(2).collect(Collectors.toSet()))
                .distinct().limit(offspringSize / 2)
                .forEach(routes -> offspring.addAll(crossoverOperator.createOffspring(routes)));
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
        List<Route> routesForRemoval =
                population.subList(populationSize - getRenewSize(populationSize), populationSize);
        for (Route route : routesForRemoval) {
            fitnessRegister.remove(route);
        }

        routesForRemoval.clear();
    }

    @Override
    public void sortPopulation(List<Route> population) {
        try {
            population.sort(routeComparator);
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
        return (int) (size * (1 - replaceRatio)) - 1;
    }

    private int getRenewSize(int populationSize) {
        return (int) (populationSize * replaceRatio);
    }

}
