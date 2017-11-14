package dzhelyazkov.evolutinary_algorithms;

import java.util.List;

public interface PopulationManager<IndividualType extends Individual> {
    void sortPopulation(List<IndividualType> population);

    boolean isPopulationEvolvedEnough(List<IndividualType> population);

    List<IndividualType> createOffspring(List<IndividualType> population);

    void removeWorstIndividuals(List<IndividualType> population);

}
