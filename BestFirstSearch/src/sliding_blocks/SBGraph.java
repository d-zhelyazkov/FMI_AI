package sliding_blocks;

import api.Graph;
import util.ArrayUtils;
import util.Point;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dzhel on 25.10.2017.
 */
public class SBGraph implements Graph<SBState, SBTransition> {
    @Override
    public Collection<SBTransition> getEdges(SBState node) {

        Collection<SBTransition> result = new ArrayList<>(SBTransition.Type.values().length);

        Point emptyCell = node.getEmptyCell();
        int[][] board = node.getBoard();
        for (SBTransition.Type transitionType : SBTransition.Type.values()) {
            try {
                int[][] newBoard = ArrayUtils.clone2DArray(board);
                Point newEmptyCell =
                        new Point(emptyCell.getX() + transitionType.xDiff, emptyCell.getY() + transitionType.yDiff);
                ArrayUtils.swapIn2DArray(newBoard, emptyCell, newEmptyCell);

                result.add(new SBTransition(node, new SBState(newEmptyCell, newBoard), transitionType));
            } catch (IndexOutOfBoundsException ignored) {
                //impossible transition
            }
        }

        return result;
    }

    public SBState getGoalState(int N) {
        int[][] board = new int[N][N];
        int element = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = element++;
            }
        }
        board[N - 1][N - 1] = 0;

        return new SBState(new Point(N - 1, N - 1), board);
    }
}
