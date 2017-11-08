package dzhelyazkov.evolutinary_algorithms.travelling_salesman;

import dzhelyazkov.evolutinary_algorithms.api.Individual;
import dzhelyazkov.evolutinary_algorithms.api.PopulationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoutesPopulationManager implements PopulationManager {

    private final float partToReplace;

    private final float individualsToReproducePart;

    public RoutesPopulationManager(float partToReplace, float individualsToReproducePart) {
        this.partToReplace = partToReplace;
        this.individualsToReproducePart = individualsToReproducePart;
    }

    @Override
    public List<Individual> createOffspring(List<Individual> population) {
        int parentsSize = population.size();
        int individualsToReproduce = (int) (parentsSize * individualsToReproducePart);

        List<Individual> potentialParrents = population.subList(0, individualsToReproduce);
        Random random = new Random();
        int offspringSize = (int) (parentsSize * partToReplace);
        List<Individual> offspring = new ArrayList<>(offspringSize);
        for (int i = 0; i < offspringSize; i++) {
            int parent1Ix = random.nextInt(parentsSize);
            int parent2Ix = random.nextInt(parentsSize);

            offspring.add(crossover(
                    (Route) potentialParrents.get(parent1Ix),
                    (Route) potentialParrents.get(parent2Ix)));
        }

        return offspring;
    }

    private Route crossover(Route route1, Route route2) {
        List<Integer> r1Points = route1.getPoints();
        int points = r1Points.size();
        int i = new Random().nextInt(points - 2) + 1;

        ArrayList<Integer> resultPoints = new ArrayList<>(r1Points.subList(0, i));
        resultPoints.ensureCapacity(points);
        List<Integer> r2Points = route2.getPoints();
        for (int j = i; i < points; i++) {
            int nextElem;
            do {
                nextElem = r2Points.get(j % points);
                j++;
            } while (!resultPoints.contains(nextElem));

            resultPoints.add(nextElem);
        }

        return new Route(resultPoints);
    }

    @Override
    public boolean isPopulationEvolvedEnough(List<Individual> population) {
        return false;
    }

    @Override
    public void removeWorstIndividuals(List<Individual> population) {
        int individuals = population.size();
        int individualsToRemove = (int) (individuals * partToReplace);
        population.subList(individuals - individualsToRemove, individuals).clear();
    }
}
