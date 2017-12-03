package dzhelyazkov.utils;

public class Point2D extends Point {

    public Point2D(double x, double y) {
        super(new double[] { x, y });
    }

    public double getX() {
        return getCoordinates()[0];
    }

    public double getY() {
        return getCoordinates()[1];
    }

    @Override
    public String toString() {
        return String.format("{x: %f, y: %f}", getX(), getY());
    }
}
