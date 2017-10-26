package sliding_blocks;

import api.GraphSearch;
import api.NoPathFoundException;
import impl.AStarSearch;
import util.Point;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static sliding_blocks.SBPrinter.printPath;
import static sliding_blocks.SBPrinter.printState;

/**
 * Created by dzhel on 25.10.2017.
 */
public class Main {

    public static void main(String[] args) throws NoPathFoundException {
        SBState initialState = constructRandomSBState(4);
        printState(initialState);

        SBGraph graph = new SBGraph();
        SBState goalState = graph.getGoalState(initialState.getBoard().length);

        GraphSearch<SBState, SBTransition> graphSearch = new AStarSearch<>(new SBManhattanDistance());
        Collection<SBTransition> path = graphSearch.findPath(initialState, goalState, graph);
        printPath(path);

        printState(goalState);
    }


    private static SBState readStateFromCin() {
        int[][] board;
        Point emptyCell = null;
        try (Scanner sc = new Scanner(System.in)) {
            int N = sc.nextInt();
            N = ((int) Math.sqrt(N)) + 1;

            board = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    board[i][j] = sc.nextInt();

                    if (board[i][j] == 0) {
                        emptyCell = new Point(j, i);
                    }
                }
            }
        }

        return new SBState(emptyCell, board);
    }

    private static SBState constructRandomSBState(int N) {

        int[][] board = new int[N][N];
        Point emptyCell = null;

        List<Integer> elements = IntStream.range(0, N * N).boxed().collect(Collectors.toList());
        Collections.shuffle(elements);
        Iterator<Integer> iterator = elements.iterator();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = iterator.next();

                if (board[i][j] == 0) {
                    emptyCell = new Point(j, i);
                }
            }
        }

        return new SBState(emptyCell, board);
    }
}
