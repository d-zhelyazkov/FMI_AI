package dzhelyazkov.evolutinary_algorithms;

import java.util.List;

public interface CrossoverSelector {
    void setPopulation(List<Individual> population);

    Individual select();
}

