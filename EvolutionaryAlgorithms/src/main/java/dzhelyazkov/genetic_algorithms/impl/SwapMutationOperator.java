package dzhelyazkov.genetic_algorithms.impl;

import dzhelyazkov.evolutinary_algorithms.MutationOperator;
import dzhelyazkov.genetic_algorithms.Chromosome;
import dzhelyazkov.genetic_algorithms.Gene;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Mutates a chromosome by swapping two of its genes.
 */
public class SwapMutationOperator<GeneType extends Gene> implements MutationOperator<Chromosome<GeneType>> {

    @Override
    public void mutate(Chromosome<GeneType> individual) {
        List<GeneType> genes = individual.getGenes();

        int[] indexes = new Random().ints(0, genes.size()).distinct().limit(2).toArray();

        Collections.swap(genes, indexes[0], indexes[1]);
    }
}
