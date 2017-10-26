package sliding_blocks;

import api.GraphSearch;
import api.NoPathFoundException;
import impl.AStarSearch;
import org.junit.Assert;
import org.junit.Test;
import util.Point;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static sliding_blocks.SBPrinter.printPath;
import static sliding_blocks.SBPrinter.printState;
import static sliding_blocks.SBTransition.Type.LEFT;

/**
 * Created by dzhel on 26.10.2017.
 */
public class SlidingBlocksTest {

    @Test
    public void testExampleCase() throws NoPathFoundException {
        int[][] board = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 0, 7, 8 }
        };

        SBState initialState = new SBState(new Point(0, 2), board);
        printState(initialState);

        SBGraph graph = new SBGraph();
        SBState goalState = graph.getGoalState(initialState.getBoard().length);

        GraphSearch<SBState, SBTransition> graphSearch = new AStarSearch<>(new SBManhattanDistance());
        Collection<SBTransition> path = graphSearch.findPath(initialState, goalState, graph);

        printPath(path);

        printState(goalState);

        Assert.assertEquals(
                path.stream().map(SBTransition::getType).collect(Collectors.toList()),
                Arrays.asList(LEFT, LEFT));
    }
}