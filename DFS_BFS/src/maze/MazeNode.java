package maze;

import api.Node;

import java.util.Collection;

/**
 * Created by dzhel on 11.10.2017 г..
 */
public class MazeNode implements Node {

    private final Maze maze;

    private final int x;

    private final int y;

    public MazeNode(Maze maze, int x, int y) {
        this.maze = maze;
        this.x = x;
        this.y = y;
    }

    @Override
    public Collection<Node> getNeighbours() {
        return maze.getNodeNeighbours(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        MazeNode mazeNode = (MazeNode) o;

        return x == mazeNode.x && y == mazeNode.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
