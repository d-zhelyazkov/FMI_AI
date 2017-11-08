package dzhelyazkov.evolutinary_algorithms.api;

import java.util.List;

public interface PopulationManager {
    List<Individual> createOffspring(List<Individual> population);

    boolean isPopulationEvolvedEnough(List<Individual> population);

    void removeWorstIndividuals(List<Individual> population);
}
