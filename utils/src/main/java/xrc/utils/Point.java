package xrc.utils;

import java.util.Arrays;

public class Point {
    private final double[] coordinates;

    public Point(double[] coordinates) {
        this.coordinates = coordinates.clone();
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Point point = (Point) o;

        return Arrays.equals(coordinates, point.coordinates);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinates);
    }
}
