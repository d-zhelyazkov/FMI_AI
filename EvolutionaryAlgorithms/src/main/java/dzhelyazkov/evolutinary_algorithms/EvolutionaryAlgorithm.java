package dzhelyazkov.evolutinary_algorithms;

import java.util.Comparator;
import java.util.List;

public class EvolutionaryAlgorithm<IndividualType extends Individual> {

    private final Comparator<IndividualType> comparator;

    private final PopulationManager<IndividualType> populationManager;

    public EvolutionaryAlgorithm(Comparator<IndividualType> comparator, PopulationManager<IndividualType> populationManager) {
        this.comparator = comparator;
        this.populationManager = populationManager;
    }

    public void evolve(List<IndividualType> population, int generations) {

        for (int i = 0; (i < generations); i++) {
            population.sort(comparator);

            if (populationManager.isPopulationEvolvedEnough(population)) {
                break;
            }

            List<IndividualType> offspring = populationManager.createOffspring(population);

            populationManager.removeWorstIndividuals(population);

            population.addAll(offspring);
        }

    }

}
