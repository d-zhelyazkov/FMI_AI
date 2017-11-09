package dzhelyazkov.evolutinary_algorithms;

import java.util.Collection;

public interface CrossoverOperator<IndividualType extends Individual> {

    Collection<IndividualType> createOffspring(Collection<IndividualType> parents);

}
