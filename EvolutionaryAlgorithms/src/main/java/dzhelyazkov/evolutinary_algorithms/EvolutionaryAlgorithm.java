package dzhelyazkov.evolutinary_algorithms;

import java.util.Comparator;
import java.util.List;

public class EvolutionaryAlgorithm<IndividualType extends Individual, FitnessType> {

    private final Comparator<FitnessType> fitnessComparator;

    private final PopulationManager<IndividualType> populationManager;

    private final FitnessFunction<FitnessType, IndividualType> fitnessFunction;

    public EvolutionaryAlgorithm(
            FitnessFunction<FitnessType, IndividualType> fitnessFunction,
            Comparator<FitnessType> fitnessComparator,
            PopulationManager<IndividualType> populationManager) {

        this.fitnessFunction = fitnessFunction;
        this.fitnessComparator = fitnessComparator;
        this.populationManager = populationManager;
    }

    public void evolve(List<IndividualType> population, int generations) {

        for (int i = 0; (i < generations); i++) {
            population.sort(new IndividualComparator());

            if (populationManager.isPopulationEvolvedEnough(population)) {
                break;
            }

            List<IndividualType> offspring = populationManager.createOffspring(population);

            populationManager.removeWorstIndividuals(population);

            population.addAll(offspring);
        }

    }

    class IndividualComparator implements Comparator<IndividualType> {

        @Override
        public int compare(IndividualType o1, IndividualType o2) {
            return fitnessComparator.compare(fitnessFunction.getFitness(o1), fitnessFunction.getFitness(o2));
        }
    }

}
