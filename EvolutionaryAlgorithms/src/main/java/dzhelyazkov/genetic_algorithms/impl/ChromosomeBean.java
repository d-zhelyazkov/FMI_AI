package dzhelyazkov.genetic_algorithms.impl;

import dzhelyazkov.genetic_algorithms.Chromosome;
import dzhelyazkov.genetic_algorithms.Gene;

import java.util.List;

public class ChromosomeBean<GeneType extends Gene> implements Chromosome<GeneType> {

    private final List<GeneType> genes;

    public ChromosomeBean(List<GeneType> genes) {
        this.genes = genes;
    }

    @Override
    public List<GeneType> getGenes() {
        return genes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ChromosomeBean that = (ChromosomeBean) o;

        return genes.equals(that.genes);
    }

    @Override
    public int hashCode() {
        return genes.hashCode();
    }
}
