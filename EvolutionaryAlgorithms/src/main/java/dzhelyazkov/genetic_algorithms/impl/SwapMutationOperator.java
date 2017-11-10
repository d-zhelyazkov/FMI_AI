package dzhelyazkov.genetic_algorithms.impl;

import dzhelyazkov.evolutinary_algorithms.MutationOperator;
import dzhelyazkov.genetic_algorithms.Chromosome;
import dzhelyazkov.genetic_algorithms.Gene;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SwapMutationOperator implements MutationOperator<Chromosome> {

    @Override
    public void mutate(Chromosome individual) {
        List<Gene> genes = individual.getGenes();

        int[] indexes = new Random().ints(0, genes.size()).distinct().limit(2).toArray();

        Collections.swap(genes, indexes[0], indexes[1]);
    }
}
