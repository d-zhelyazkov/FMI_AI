package dzhelyazkov.bestfirstsearch.sliding_blocks.cached;

import dzhelyazkov.bestfirstsearch.api.Graph;
import dzhelyazkov.bestfirstsearch.sliding_blocks.SBTransition;
import dzhelyazkov.utils.ArrayUtils;
import dzhelyazkov.utils.ManhattanDistanceCalc;
import dzhelyazkov.utils.Point;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dzhel on 28.10.2017.
 */
public class SBCachedGraph implements Graph<SBStateCached, SBTransition<SBStateCached>> {
    @Override
    public Collection<SBTransition<SBStateCached>> getEdges(SBStateCached node) {

        Collection<SBTransition<SBStateCached>> result = new ArrayList<>(SBTransition.Type.values().length);

        Point emptyCell = node.getEmptyCell();
        int[][] board = node.getBoard();
        for (SBTransition.Type transitionType : SBTransition.Type.values()) {
            try {
                int[][] newBoard = ArrayUtils.clone2DArray(board);
                Point newEmptyCell =
                        new Point(emptyCell.getX() + transitionType.xDiff, emptyCell.getY() + transitionType.yDiff);
                ArrayUtils.swapIn2DArray(newBoard, emptyCell, newEmptyCell);

                int diffWithGoal = node.getDiffWithGoal() + calcDiffInDistance(newBoard, emptyCell, newEmptyCell);

                result.add(new SBTransition<>(
                        node,
                        new SBStateCached(newEmptyCell, newBoard, diffWithGoal),
                        transitionType));

            } catch (IndexOutOfBoundsException ignored) {
                //impossible transition
            }
        }

        return result;
    }

    private int calcDiffInDistance(int[][] board, Point cell, Point oldCell) {
        int N = board.length;
        int swappedValue = board[(int) cell.getY()][(int) cell.getX()];
        Point goalCell = calcGoalPoint(swappedValue, N);

        ManhattanDistanceCalc distanceCalc = new ManhattanDistanceCalc();
        int diff = (int)
                (distanceCalc.calcDistance(goalCell, cell) - distanceCalc.calcDistance(goalCell, oldCell));

        Point emptyGoalCell = new Point(N - 1, N - 1);
        diff += (int)
                (distanceCalc.calcDistance(emptyGoalCell, oldCell) - distanceCalc.calcDistance(emptyGoalCell, cell));

        return diff;
    }

    private Point calcGoalPoint(int value, int N) {
        value--;
        return new Point(value % N, value / N);
    }
}
