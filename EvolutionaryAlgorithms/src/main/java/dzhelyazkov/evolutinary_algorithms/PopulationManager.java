package dzhelyazkov.evolutinary_algorithms;

import java.util.List;

public interface PopulationManager<IndividualType extends Individual> {
    List<IndividualType> createOffspring(List<IndividualType> population);

    boolean isPopulationEvolvedEnough(List<IndividualType> population);

    void removeWorstIndividuals(List<IndividualType> population);
}
