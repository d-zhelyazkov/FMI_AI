package dzhelyazkov.evolutinary_algorithms;

import java.util.List;

public class EvolutionaryAlgorithm<IndividualType extends Individual> {

    private final PopulationManager<IndividualType> populationManager;

    public EvolutionaryAlgorithm(PopulationManager<IndividualType> populationManager) {
        this.populationManager = populationManager;
    }

    public void evolve(List<IndividualType> population, int generations) {

        for (int i = 0; (i < generations); i++) {

            if (populationManager.isPopulationEvolvedEnough(population)) {
                break;
            }

            evolve(population);
        }

    }

    public void evolve(List<IndividualType> population) {

        populationManager.sortPopulation(population);

        List<IndividualType> offspring = populationManager.createOffspring(population);

        populationManager.removeWorstIndividuals(population);

        population.addAll(offspring);
    }

}
