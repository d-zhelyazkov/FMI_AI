package dzhelyazkov.bestfirstsearch.sliding_blocks.cached;

import dzhelyazkov.bestfirstsearch.api.Heuristic;

/**
 * Created by dzhel on 28.10.2017.
 */
public class SBCachedDistance implements Heuristic<SBStateCached> {
    @Override
    public Number calcDistance(SBStateCached node1, SBStateCached node2) {
        return node1.getDiffWithGoal();
    }
}
