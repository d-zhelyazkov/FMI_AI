package dzhelyazkov.genetic_algorithms;

import dzhelyazkov.evolutinary_algorithms.MutationOperator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SwapMutationOperator implements MutationOperator<Chromosome> {

    @Override
    public void mutate(Chromosome individual) {
        List<Gene> genes = individual.getGenes();

        int[] indexes = new Random().ints(2, 0, genes.size()).distinct().toArray();

        Collections.swap(genes, indexes[0], indexes[1]);
    }
}
