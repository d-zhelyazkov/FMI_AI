package dzhelyazkov.evolutinary_algorithms;

import java.util.List;

public class EvolutionaryAlgorithm {

    private final PopulationManager populationManager;

    public EvolutionaryAlgorithm(PopulationManager populationManager) {
        this.populationManager = populationManager;
    }

    public void evolve(List<Individual> population, int generations) {

        for (int i = 0; (i < generations); i++) {
            populationManager.sortPopulation(population);

            if (populationManager.isPopulationEvolvedEnough(population)) {
                break;
            }

            List<Individual> offspring = populationManager.createOffspring(population);

            populationManager.removeWorstIndividuals(population);

            population.addAll(offspring);
        }

    }

}
