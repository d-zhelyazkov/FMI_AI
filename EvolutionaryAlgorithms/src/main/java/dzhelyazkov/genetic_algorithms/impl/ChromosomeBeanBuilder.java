package dzhelyazkov.genetic_algorithms.impl;

import dzhelyazkov.genetic_algorithms.ChromosomeBuilder;
import dzhelyazkov.genetic_algorithms.Gene;

import java.util.List;

public class ChromosomeBeanBuilder implements ChromosomeBuilder<ChromosomeBean> {

    private List<Gene> genes;

    @Override
    public ChromosomeBuilder<ChromosomeBean> setGenes(List<Gene> genes) {
        this.genes = genes;
        return this;
    }

    @Override
    public ChromosomeBean build() {
        return new ChromosomeBean(genes);
    }
}
