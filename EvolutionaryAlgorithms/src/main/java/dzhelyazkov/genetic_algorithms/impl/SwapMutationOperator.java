package dzhelyazkov.genetic_algorithms.impl;

import dzhelyazkov.evolutinary_algorithms.MutationOperator;
import dzhelyazkov.genetic_algorithms.Chromosome;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Mutates a chromosome by swapping two of its genes.
 */
public class SwapMutationOperator<ChromosomeType extends Chromosome>
        implements MutationOperator<ChromosomeType> {

    private final int swaps;

    public SwapMutationOperator(int swaps) {
        this.swaps = swaps;
    }

    public SwapMutationOperator() {
        swaps = 0;
    }

    @Override
    public void mutate(ChromosomeType individual) {
        List genes = individual.getGenes();
        Random random = new Random();
        for (int i = 0; i < swaps; i++) {
            int[] indexes = random.ints(0, genes.size()).distinct().limit(2).toArray();

            Collections.swap(genes, indexes[0], indexes[1]);
        }
    }
}
