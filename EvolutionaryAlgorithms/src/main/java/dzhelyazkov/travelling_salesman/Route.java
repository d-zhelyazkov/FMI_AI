package dzhelyazkov.travelling_salesman;

import dzhelyazkov.evolutinary_algorithms.Individual;

import java.util.List;

public class Route implements Individual {
    private int[] points;

    public Route(List<Integer> resultPoints) {

    }

    public List<Integer> getPoints() {
        return points;
    }
}
