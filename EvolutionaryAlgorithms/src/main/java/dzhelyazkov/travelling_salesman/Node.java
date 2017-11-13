package dzhelyazkov.travelling_salesman;

import dzhelyazkov.genetic_algorithms.Gene;
import dzhelyazkov.utils.Point2D;

public class Node implements Gene{
    private final int id;

    private final Point2D position;

    public Node(int id, Point2D position) {
        this.id = id;
        this.position = position;
    }

    public Point2D getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Node node = (Node) o;

        return id == node.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
