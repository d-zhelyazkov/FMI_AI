package dzhelyazkov.bestfirstsearch.sliding_blocks;

import dzhelyazkov.bestfirstsearch.api.Node;
import dzhelyazkov.utils.Point;

import java.util.Arrays;

/**
 * Created by dzhel on 25.10.2017.
 */
public class SBState implements Node {

    private final Point emptyCell;

    private final int[][] board;

    public SBState(Point emptyCell, int[][] board) {
        this.emptyCell = emptyCell;
        this.board = board;
    }

    public Point getEmptyCell() {
        return emptyCell;
    }

    public int[][] getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SBState sbState = (SBState) o;

        return Arrays.deepEquals(board, sbState.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}
