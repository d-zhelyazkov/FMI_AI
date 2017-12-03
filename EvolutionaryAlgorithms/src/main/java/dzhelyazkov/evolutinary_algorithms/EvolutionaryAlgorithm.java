package dzhelyazkov.evolutinary_algorithms;

import java.util.Collection;
import java.util.List;

public class EvolutionaryAlgorithm<IndividualType extends Individual> {

    private final PopulationManager<IndividualType> populationManager;

    public EvolutionaryAlgorithm(PopulationManager<IndividualType> populationManager) {
        this.populationManager = populationManager;
    }

    public int evolve(List<IndividualType> population, int generations) {

        int i = 0;
        while (i < generations) {
            evolve(population);
            i++;

            if (populationManager.isPopulationEvolvedEnough(population)) {
                break;
            }
        }

        return i;
    }

    public void evolve(List<IndividualType> population) {

        populationManager.sortPopulation(population);

        Collection<IndividualType> offspring = populationManager.createOffspring(population);

        populationManager.removeWorstIndividuals(population);

        population.addAll(offspring);
    }

}
