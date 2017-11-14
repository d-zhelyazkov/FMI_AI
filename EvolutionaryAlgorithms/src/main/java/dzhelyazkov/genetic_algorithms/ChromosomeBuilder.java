package dzhelyazkov.genetic_algorithms;

import java.util.List;

public interface ChromosomeBuilder<GeneType extends Gene, ChromosomeType extends Chromosome<GeneType>> {
    ChromosomeBuilder<GeneType, ChromosomeType> setGenes(List<GeneType> genes);

    ChromosomeType build();
}
