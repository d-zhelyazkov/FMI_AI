package dzhelyazkov.genetic_algorithms.impl;

import dzhelyazkov.genetic_algorithms.ChromosomeBuilder;
import dzhelyazkov.genetic_algorithms.Gene;

import java.util.List;

public class ChromosomeBeanBuilder<GeneType extends Gene>
        implements ChromosomeBuilder<GeneType, ChromosomeBean<GeneType>> {

    private List<GeneType> genes;

    @Override
    public ChromosomeBeanBuilder<GeneType> setGenes(List<GeneType> genes) {
        this.genes = genes;
        return this;
    }

    @Override
    public ChromosomeBean<GeneType> build() {
        return new ChromosomeBean<>(genes);
    }
}
