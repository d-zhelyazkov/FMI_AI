package xrc.utils.distance_calculator;

import xrc.utils.Point;

public class EuclideanDistanceCalculator implements DistanceCalculator {
    @Override
    public double calcDistance(Point p1, Point p2) {

        double sum = 0;
        double[] p1Coordinates = p1.getCoordinates();
        double[] p2Coordinates = p2.getCoordinates();

        for (int i = 0; i < p1Coordinates.length; i++) {
            sum += Math.pow(p1Coordinates[i] - p2Coordinates[i], 2);
        }

        return Math.sqrt(sum);
    }
}
