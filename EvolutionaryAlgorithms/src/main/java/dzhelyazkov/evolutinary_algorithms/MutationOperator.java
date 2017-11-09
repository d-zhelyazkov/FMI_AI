package dzhelyazkov.evolutinary_algorithms;

public interface MutationOperator<IndividualType extends Individual> {

    void mutate(IndividualType individual);

}
