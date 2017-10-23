package maze;

import api.GraphSearch;
import api.Node;
import impl.BFSSearch;

import java.util.Collection;

/**
 * Created by dzhel on 11.10.2017 г..
 */
public class Main {
    public static void main(String[] args) {
        char[][] board = {
                { ' ', ' ', ' ', ' ' },
                { ' ', '#', ' ', '#' },
                { ' ', ' ', ' ', ' ' },
                { ' ', ' ', '#', ' ' },
                { ' ', '#', ' ', ' ' }
        };

        Maze maze = new Maze(board);
        GraphSearch graphSearch = new BFSSearch();
        Collection<Node> path = graphSearch.searchPath(
                new MazeNode(maze, 0, 0),
                new MazeNode(maze, 3, 4));

        for (Node node : path) {
            MazeNode mazeNode = (MazeNode) node;

            board[mazeNode.getY()][mazeNode.getX()] = '*';
        }

        for (char[] line : board) {
            System.out.println(line);
        }

    }
}
