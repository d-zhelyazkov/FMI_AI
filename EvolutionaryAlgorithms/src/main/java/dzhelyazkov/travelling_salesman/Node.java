package dzhelyazkov.travelling_salesman;

import dzhelyazkov.genetic_algorithms.Gene;
import dzhelyazkov.utils.Point;

public class Node implements Gene{
    private final int id;

    private final Point position;

    Node(int id, Point position) {
        this.id = id;
        this.position = position;
    }

    Point getPosition() {
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

    int getID() {
        return id;
    }
}
