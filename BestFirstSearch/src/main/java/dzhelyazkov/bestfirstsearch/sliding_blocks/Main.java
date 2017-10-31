package dzhelyazkov.bestfirstsearch.sliding_blocks;

import dzhelyazkov.bestfirstsearch.api.GraphSearch;
import dzhelyazkov.bestfirstsearch.api.NoPathFoundException;
import dzhelyazkov.bestfirstsearch.impl.AStarSearch;
import dzhelyazkov.bestfirstsearch.sliding_blocks.builder.SBGoalStateBuilder;
import dzhelyazkov.bestfirstsearch.sliding_blocks.builder.SBStateCinBuilder;

import java.util.Collection;

import static dzhelyazkov.bestfirstsearch.sliding_blocks.SBUtil.printPath;

/**
 * Created by dzhel on 25.10.2017.
 */
public class Main {

    public static void main(String[] args) throws NoPathFoundException {

        SBState initialState = new SBStateCinBuilder().build();
        if (!SBUtil.isSolvable(initialState)) {
            System.out.println("You entered unsolvable board.");
            return;
        }


        SBGraph graph = new SBGraph();
        SBState goalState = new SBGoalStateBuilder(initialState.getBoard().length).build();

        GraphSearch<SBState, SBTransition<SBState>> graphSearch = new AStarSearch<>(new SBManhattanDistance());
        Collection<SBTransition<SBState>> path = graphSearch.findPath(initialState, goalState, graph);

        printPath(path);
    }

}
