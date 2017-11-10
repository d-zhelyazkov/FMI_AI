package dzhelyazkov.evolutinary_algorithms;

public interface FitnessFunction<FitnessType, IndividualType extends Individual> {
    FitnessType getFitness(IndividualType individual);
}
