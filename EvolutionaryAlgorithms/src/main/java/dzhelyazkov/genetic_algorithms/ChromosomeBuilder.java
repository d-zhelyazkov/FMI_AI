package dzhelyazkov.genetic_algorithms;

import java.util.List;

public interface ChromosomeBuilder<GeneType extends Gene> {
    ChromosomeBuilder<GeneType> setGenes(List<GeneType> genes);

    Chromosome<GeneType> build();
}
