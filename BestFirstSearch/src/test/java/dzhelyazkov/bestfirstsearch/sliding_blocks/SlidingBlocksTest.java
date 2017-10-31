package dzhelyazkov.bestfirstsearch.sliding_blocks;

import dzhelyazkov.bestfirstsearch.api.GraphSearch;
import dzhelyazkov.bestfirstsearch.api.Heuristic;
import dzhelyazkov.bestfirstsearch.api.NoPathFoundException;
import dzhelyazkov.bestfirstsearch.impl.AStarSearch;
import dzhelyazkov.bestfirstsearch.sliding_blocks.builder.SBGoalStateBuilder;
import dzhelyazkov.bestfirstsearch.sliding_blocks.builder.SBStateBuilder;
import dzhelyazkov.bestfirstsearch.sliding_blocks.builder.SBStateRandomBuilder;
import dzhelyazkov.bestfirstsearch.sliding_blocks.cached.SBCachedDistance;
import dzhelyazkov.bestfirstsearch.sliding_blocks.cached.SBCachedGraph;
import dzhelyazkov.bestfirstsearch.sliding_blocks.cached.SBStateCached;
import dzhelyazkov.utils.Point;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static dzhelyazkov.bestfirstsearch.sliding_blocks.SBTransition.Type.LEFT;
import static dzhelyazkov.bestfirstsearch.sliding_blocks.SBUtil.printPath;
import static dzhelyazkov.bestfirstsearch.sliding_blocks.SBUtil.printState;

/**
 * Created by dzhel on 26.10.2017.
 */
@RunWith(Parameterized.class)
public class SlidingBlocksTest {

    private static final SBState exampleState = new SBState(new Point(0, 2), new int[][] {
            { 1, 2, 3 },
            { 4, 5, 6 },
            { 0, 7, 8 } });

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][] {
                { false, 0 },
                { false, 1 },
                { false, 2 },
                { false, 3 },
                { false, 3 },
                { false, 3 },
                { false, 3 },
                { false, 3 },
                { false, 3 },
//                   {false, 4},
//                   {false, 5},
                { true, 0 },
                { true, 1 },
                { true, 2 },
                { true, 3 },
                { true, 3 },
                { true, 3 },
                { true, 3 },
                { true, 3 },
                { true, 3 },
//                { true, 4 },
//                { true, 5 }
        };

    }

    private final boolean cacheEnable;

    private final int N;

    private SBState testState;

    private SBState goalState;

    private Collection<SBTransition<SBState>> path;

    public SlidingBlocksTest(boolean cacheEnable, int n) {
        this.cacheEnable = cacheEnable;
        N = n;
    }

    @Before
    public void beforeMethod() {

        System.out.printf("Executing test - N: %d, cacheEnable: %b\n", N, cacheEnable);

        if (N == 0) {
            testState = exampleState;

            goalState = new SBGoalStateBuilder(3).build();
        } else {
            SBStateBuilder sbStateBuilder = new SBStateRandomBuilder(N);
            do {
                System.out.println("Generating random board...");
                testState = sbStateBuilder.build();
            } while (!SBUtil.isSolvable(testState));

            goalState = new SBGoalStateBuilder(N).build();
        }
    }

    @Test
    public void test() throws NoPathFoundException {
        printState(testState);

        if (cacheEnable) {
            executeWithCachedEnv();
        } else {
            execute();
        }

        printPath(path);

        printState(goalState);

    }

    private void execute() throws NoPathFoundException {

        SBGraph graph = new SBGraph();
        SBState goalState = new SBGoalStateBuilder(testState.getBoard().length).build();
        GraphSearch<SBState, SBTransition<SBState>> graphSearch = new AStarSearch<>(new SBManhattanDistance());
        path = graphSearch.findPath(testState, goalState, graph);
    }

    private void executeWithCachedEnv() throws NoPathFoundException {
        SBCachedGraph graph = new SBCachedGraph();
        GraphSearch<SBStateCached, SBTransition<SBStateCached>> graphSearch = new AStarSearch<>(new SBCachedDistance());

        Heuristic<SBState> distanceCalc = new SBManhattanDistance();
        SBStateCached stateCached = new SBStateCached(
                testState, distanceCalc.calcDistance(testState, goalState).intValue());
        path = graphSearch.findPath(stateCached, new SBStateCached(goalState, 0), graph).stream()
                .map(p -> new SBTransition<SBState>(p.from(), p.to(), p.getType())).collect(Collectors.toList());
    }

    @After
    public void afterMethod() {
        if (N == 0) {
            Assert.assertEquals(
                    path.stream().map(SBTransition::getType).collect(Collectors.toList()),
                    Arrays.asList(LEFT, LEFT));
        }
    }
}