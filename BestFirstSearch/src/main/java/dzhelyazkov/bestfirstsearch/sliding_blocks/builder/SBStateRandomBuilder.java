package dzhelyazkov.bestfirstsearch.sliding_blocks.builder;

import dzhelyazkov.bestfirstsearch.sliding_blocks.SBState;
import dzhelyazkov.utils.Point;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by dzhel on 28.10.2017.
 */
public class SBStateRandomBuilder implements SBStateBuilder {

    private final int N;

    public SBStateRandomBuilder(int n) {
        N = n;
    }

    @Override
    public SBState build() {

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
