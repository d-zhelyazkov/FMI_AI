package dzhelyazkov.bestfirstsearch.sliding_blocks.cached;

import dzhelyazkov.bestfirstsearch.sliding_blocks.SBState;
import dzhelyazkov.utils.Point;

/**
 * Created by dzhel on 28.10.2017.
 */
public class SBStateCached extends SBState {

    private final int diffWithGoal;

    private final int chachedHashCode;

    public SBStateCached(Point emptyCell, int[][] board, int diffWithGoal) {
        super(emptyCell, board);
        this.diffWithGoal = diffWithGoal;
        chachedHashCode = super.hashCode();
    }

    public SBStateCached(SBState state, int diffWithGoal) {
        this(state.getEmptyCell(), state.getBoard(), diffWithGoal);
    }

    public int getDiffWithGoal() {
        return diffWithGoal;
    }

    @Override
    public int hashCode() {
        return chachedHashCode;
    }
}
