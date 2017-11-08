package dzhelyazkov.evolutinary_algorithms.api;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EvolutionaryAlgorithm {

    private final Comparator<Individual> comparator;

    private final PopulationManager populationManager;

    public EvolutionaryAlgorithm(Comparator<Individual> comparator, PopulationManager populationManager) {
        this.comparator = comparator;
        this.populationManager = populationManager;
    }

    public void evolve(List<Individual> population, int generations) {

        for (int i = 0; (i < generations); i++) {
            Collections.sort(population, comparator);

            if (populationManager.isPopulationEvolvedEnough(population)) {
                break;
            }

            List<Individual> offspring = populationManager.createOffspring(population);

            populationManager.removeWorstIndividuals(population);

            population.addAll(offspring);
        }

    }

}
