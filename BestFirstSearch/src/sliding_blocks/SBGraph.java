package sliding_blocks;

import api.Graph;
import util.ArrayUtils;
import util.Point;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dzhel on 25.10.2017.
 */
public class SBGraph implements Graph<SBState, SBTransition<SBState>> {
    @Override
    public Collection<SBTransition<SBState>> getEdges(SBState node) {

        Collection<SBTransition<SBState>> result = new ArrayList<>(SBTransition.Type.values().length);

        Point emptyCell = node.getEmptyCell();
        int[][] board = node.getBoard();
        for (SBTransition.Type transitionType : SBTransition.Type.values()) {
            try {
                int[][] newBoard = ArrayUtils.clone2DArray(board);
                Point newEmptyCell =
                        new Point(emptyCell.getX() + transitionType.xDiff, emptyCell.getY() + transitionType.yDiff);
                ArrayUtils.swapIn2DArray(newBoard, emptyCell, newEmptyCell);

                result.add(new SBTransition<>(node, new SBState(newEmptyCell, newBoard), transitionType));
            } catch (IndexOutOfBoundsException ignored) {
                //impossible transition
            }
        }

        return result;
    }
}
