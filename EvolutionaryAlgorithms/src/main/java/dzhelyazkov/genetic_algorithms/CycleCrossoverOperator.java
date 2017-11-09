package dzhelyazkov.genetic_algorithms;

import dzhelyazkov.evolutinary_algorithms.CrossoverOperator;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Applies cycle crossover over the two parent chromosomes.
 * Works with chromosomes with permutation encoding.
 * Produces offspring of two new chromosomes
 */
public class CycleCrossoverOperator<ChromosomeType extends Chromosome> implements CrossoverOperator<ChromosomeType> {

    private final ChromosomeBuilder<ChromosomeType> chromosomeBuilder;

    public CycleCrossoverOperator(ChromosomeBuilder<ChromosomeType> chromosomeBuilder) {
        this.chromosomeBuilder = chromosomeBuilder;
    }

    @Override
    public Collection<ChromosomeType> createOffspring(Collection<ChromosomeType> parents) {
        Iterator<ChromosomeType> parentsIt = parents.iterator();
        List<Gene> parent1Genes = parentsIt.next().getGenes();
        List<Gene> parent2Genes = parentsIt.next().getGenes();
        int genesCount = parent1Genes.size();

        Gene[] child1Genes = new Gene[genesCount];
        Gene[] child2Genes = new Gene[genesCount];

        Set<Integer> indexes = new HashSet<>(
                IntStream.range(0, genesCount).boxed().collect(Collectors.toList()));
        Map<Gene, Integer> p1GeneIndexes = IntStream.range(0, genesCount).boxed()
                .collect(Collectors.toMap(parent1Genes::get, i -> i));

        boolean bit = false;
        while (!indexes.isEmpty()) {
            Integer index = indexes.iterator().next();
            while (indexes.contains(index)) {
                Gene p1Gene = parent1Genes.get(index);
                Gene p2Gene = parent2Genes.get(index);
                child1Genes[index] = (bit) ? p1Gene : p2Gene;
                child2Genes[index] = (bit) ? p2Gene : p1Gene;

                indexes.remove(index);
                index = p1GeneIndexes.get(p2Gene);
            }

            bit = !bit;
        }

        return Arrays.asList(
                chromosomeBuilder.setGenes(Arrays.asList(child1Genes)).build(),
                chromosomeBuilder.setGenes(Arrays.asList(child2Genes)).build()
        );
    }
}
