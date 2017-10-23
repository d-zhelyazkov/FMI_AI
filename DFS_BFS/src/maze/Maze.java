package maze;

import api.Node;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dzhel on 11.10.2017 г..
 */
public class Maze {

    private static final int DIRS[][] = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

    private final char[][] board;

    private final int W;

    private final int H;

    public Maze(char[][] board) {
        this.board = board.clone();
        W = board[0].length;
        H = board.length;
    }

    public Collection<Node> getNodeNeighbours(MazeNode mazeNode) {

        Collection<Node> neighbours = new ArrayList<>();

        for (int[] dir : DIRS) {
            int x = mazeNode.getX() + dir[0];
            int y = mazeNode.getY() + dir[1];
            if (!checkCoord(x, W) || !checkCoord(y, H)) {
                continue;
            }

            if (board[y][x] == ' ') {
                neighbours.add(new MazeNode(this, x, y));
            }
        }

        return neighbours;
    }

    private boolean checkCoord(int x, int N) {
        return (0 <= x && x < N);
    }

}
