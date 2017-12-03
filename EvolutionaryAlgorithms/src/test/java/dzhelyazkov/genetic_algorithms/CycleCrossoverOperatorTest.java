package dzhelyazkov.genetic_algorithms;

import dzhelyazkov.evolutinary_algorithms.CrossoverOperator;
import dzhelyazkov.genetic_algorithms.impl.ChromosomeBean;
import dzhelyazkov.genetic_algorithms.impl.ChromosomeBeanBuilder;
import dzhelyazkov.genetic_algorithms.impl.CycleCrossoverOperator;
import dzhelyazkov.genetic_algorithms.impl.ObjectGene;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CycleCrossoverOperatorTest {

    private static final int GENES_MAX_COUNT = 100000;

    @RepeatedTest(100)
    void createOffspring() throws Exception {
        int genesCount = new Random().nextInt(GENES_MAX_COUNT) + 1;

        System.out.printf("Testing with %d genes\n", genesCount);
        List<Gene> parent1Genes = generateRandomPermutationChromosome(genesCount);
        List<Gene> parent2Genes = generateRandomPermutationChromosome(genesCount);

        CrossoverOperator<ChromosomeBean<Gene>> cycleCrossoverOperator =
                new CycleCrossoverOperator<>(new ChromosomeBeanBuilder<>());

        Collection<ChromosomeBean<Gene>> offspring = cycleCrossoverOperator.createOffspring(Arrays.asList(
                new ChromosomeBean<>(parent1Genes),
                new ChromosomeBean<>(parent2Genes)));

        Iterator<ChromosomeBean<Gene>> children = offspring.iterator();
        List<Gene> child1Genes = children.next().getGenes();
        List<Gene> child2Genes = children.next().getGenes();

        int ch1Matches = 0;
        int ch2Matches = 0;
        for (int i = 0; i < genesCount; i++) {
            Gene p1Gene = parent1Genes.get(i);
            Gene p2Gene = parent2Genes.get(i);

            Gene ch1Gene = child1Genes.get(i);
            Gene ch2Gene = child2Genes.get(i);

            ch1Matches += p1Gene.equals(ch1Gene) ? 1 : 0;
            ch2Matches += p2Gene.equals(ch2Gene) ? 1 : 0;

            Assertions.assertTrue(
                    (p1Gene.equals(ch1Gene) && p2Gene.equals(ch2Gene))
                            || (p1Gene.equals(ch2Gene) && p2Gene.equals(ch1Gene))
            );
        }

        Assertions.assertEquals(ch1Matches, ch2Matches);
        System.out.println("Matches: " + ch1Matches);
    }

    private List<Gene> generateRandomPermutationChromosome(int genesCount) {
        List<Integer> ints = IntStream.range(0, genesCount).boxed().collect(Collectors.toList());
        Collections.shuffle(ints);
        return ints.stream().map(ObjectGene::new).collect(Collectors.toList());
    }

}