package dzhelyazkov.genetic_algorithms;

import dzhelyazkov.evolutinary_algorithms.Individual;

import java.util.List;

public interface Chromosome extends Individual {

    /**
     * @return reference to this Chromosome's list of genes.
     */
    List<Gene> getGenes();
}
