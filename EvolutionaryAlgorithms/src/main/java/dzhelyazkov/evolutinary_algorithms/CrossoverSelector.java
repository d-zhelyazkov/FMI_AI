package dzhelyazkov.evolutinary_algorithms;

import java.util.List;
import java.util.function.Supplier;

public interface CrossoverSelector<IndividualType extends Individual> extends Supplier<IndividualType>{
    void setPopulation(List<IndividualType> population);
}

