package dzhelyazkov.distance_calculator;

import dzhelyazkov.utils.Point;

/**
 * Created by dzhel on 25.10.2017.
 */
public class ManhattanDistanceCalculator implements DistanceCalculator {

    @Override
    public double calcDistance(Point p1, Point p2) {
        double[] p1Coords = p1.getCoordinates();
        double[] p2Coords = p2.getCoordinates();

        double distance = 0;
        for (int i = 0; i < p1Coords.length; i++) {
            distance += Math.abs(p1Coords[i] - p2Coords[i]);
        }

        return distance;
    }
}
