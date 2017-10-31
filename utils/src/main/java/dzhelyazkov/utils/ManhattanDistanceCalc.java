package dzhelyazkov.utils;

/**
 * Created by dzhel on 25.10.2017.
 */
public class ManhattanDistanceCalc {
    public double calcDistance(Point p1, Point p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }
}
