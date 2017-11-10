package dzhelyazkov.genetic_algorithms.impl;

import dzhelyazkov.genetic_algorithms.Chromosome;
import dzhelyazkov.genetic_algorithms.Gene;

import java.util.List;

public class ChromosomeBean implements Chromosome {

    private final List<Gene> genes;

    public ChromosomeBean(List<Gene> genes) {
        this.genes = genes;
    }

    @Override
    public List<Gene> getGenes() {
        return genes;
    }
}
