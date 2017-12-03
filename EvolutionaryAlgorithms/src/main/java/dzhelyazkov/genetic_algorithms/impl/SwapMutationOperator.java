package dzhelyazkov.genetic_algorithms.impl;

import dzhelyazkov.evolutinary_algorithms.MutationOperator;
import dzhelyazkov.genetic_algorithms.Chromosome;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Mutates a chromosome by swapping two of its genes.
 */
public class SwapMutationOperator implements MutationOperator<Chromosome> {

    private static final int SWAPS = 1;

    private final int swaps;

    public SwapMutationOperator(int swaps) {
        this.swaps = swaps;
    }

    public SwapMutationOperator() {
        this(SWAPS);
    }

    @Override
    public void mutate(Chromosome individual) {
        List genes = individual.getGenes();
        int genesCount = genes.size();
        Random random = new Random();
        for (int i = 0; i < swaps; i++) {
            Collections.swap(genes, random.nextInt(genesCount), random.nextInt(genesCount));
        }
    }
}
