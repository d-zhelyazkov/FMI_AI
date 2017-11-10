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
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class CycleCrossoverOperatorTest {

    private static final int GENES_MAX_COUNT = 100000;

    @RepeatedTest(200)
    void createOffspring() throws Exception {
        int genesCount = new Random().nextInt(GENES_MAX_COUNT) + 1;

        List<Gene> parent1Genes = generateRandomPermutationChromosome(genesCount);
        List<Gene> parent2Genes = generateRandomPermutationChromosome(genesCount);

        CrossoverOperator<ChromosomeBean> cycleCrossoverOperator = new CycleCrossoverOperator<>(
                new ChromosomeBeanBuilder());

        Collection<ChromosomeBean> offspring = cycleCrossoverOperator.createOffspring(Arrays.asList(
                new ChromosomeBean(parent1Genes),
                new ChromosomeBean(parent2Genes)));

        Iterator<ChromosomeBean> children = offspring.iterator();
        List<Gene> child1Genes = children.next().getGenes();
        List<Gene> child2Genes = children.next().getGenes();

        for (int i = 0; i < genesCount; i++) {
            Gene p1Gene = parent1Genes.get(i);
            Gene p2Gene = parent2Genes.get(i);

            Gene ch1Gene = child1Genes.get(i);
            Gene ch2Gene = child2Genes.get(i);

            Assertions.assertTrue(
                    (p1Gene.equals(ch1Gene) && p2Gene.equals(ch2Gene))
                            || (p1Gene.equals(ch2Gene) && p2Gene.equals(ch1Gene))
            );
        }
    }

    private List<Gene> generateRandomPermutationChromosome(int genesCount) {
        return new Random().ints(0, genesCount).distinct().limit(genesCount)
                .boxed().map(ObjectGene::new).collect(Collectors.toList());
    }

}