package dzhelyazkov.genetic_algorithms;

import java.util.List;

public interface ChromosomeBuilder<ChromosomeType extends Chromosome> {
    ChromosomeBuilder<ChromosomeType> setGenes(List<Gene> genes);

    ChromosomeType build();
}
