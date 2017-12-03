package dzhelyazkov.genetic_algorithms.impl;

import dzhelyazkov.evolutinary_algorithms.CrossoverOperator;
import dzhelyazkov.genetic_algorithms.Chromosome;
import dzhelyazkov.genetic_algorithms.ChromosomeBuilder;
import dzhelyazkov.genetic_algorithms.Gene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Applies cycle crossover over the two parent chromosomes.
 * Works with chromosomes with permutation encoding.
 * Produces offspring of two new chromosomes
 */
public class CycleCrossoverOperator<GeneType extends Gene, ChromosomeType extends Chromosome<GeneType>>
        implements CrossoverOperator<ChromosomeType> {

    private final ChromosomeBuilder<GeneType, ChromosomeType> chromosomeBuilder;

    public CycleCrossoverOperator(ChromosomeBuilder<GeneType, ChromosomeType> chromosomeBuilder) {
        this.chromosomeBuilder = chromosomeBuilder;
    }

    @Override
    public Collection<ChromosomeType> createOffspring(Collection<ChromosomeType> parents) {
        Iterator<ChromosomeType> parentsIt = parents.iterator();
        List<GeneType> parent1Genes = parentsIt.next().getGenes();
        List<GeneType> parent2Genes = parentsIt.next().getGenes();
        int genesCount = parent1Genes.size();

        List<GeneType> child1Genes = new ArrayList<>(Collections.nCopies(genesCount, null));
        List<GeneType> child2Genes = new ArrayList<>(Collections.nCopies(genesCount, null));

        Set<Integer> indexes = new HashSet<>(genesCount);
        Map<GeneType, Integer> p1GeneIndexes = new HashMap<>(genesCount);
        for (int i = 0; i < genesCount; i++) {
            indexes.add(i);
            p1GeneIndexes.put(parent1Genes.get(i), i);
        }

        boolean bit = false;
        while (!indexes.isEmpty()) {
            Integer index = dzhelyazkov.utils.Collections.getRandomElement(indexes);
            while (indexes.contains(index)) {
                GeneType p1Gene = parent1Genes.get(index);
                GeneType p2Gene = parent2Genes.get(index);
                child1Genes.set(index, (bit) ? p1Gene : p2Gene);
                child2Genes.set(index, (bit) ? p2Gene : p1Gene);

                indexes.remove(index);
                index = p1GeneIndexes.get(p2Gene);
            }

            bit = !bit;
        }

        return Arrays.asList(
                chromosomeBuilder.setGenes(child1Genes).build(),
                chromosomeBuilder.setGenes(child2Genes).build()
        );
    }
}
