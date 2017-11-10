package dzhelyazkov.evolutinary_algorithms;

import java.util.List;

public interface CrossoverSelector<IndividualType extends Individual> {
    void setPopulation(List<IndividualType> population);

    IndividualType select();
}

