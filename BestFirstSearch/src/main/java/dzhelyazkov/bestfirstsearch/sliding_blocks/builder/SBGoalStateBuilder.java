package dzhelyazkov.bestfirstsearch.sliding_blocks.builder;

import dzhelyazkov.bestfirstsearch.sliding_blocks.SBState;
import dzhelyazkov.utils.Point;

/**
 * Created by dzhel on 30.10.2017.
 */
public class SBGoalStateBuilder implements SBStateBuilder {

    private final int N;

    public SBGoalStateBuilder(int n) {
        N = n;
    }

    @Override
    public SBState build() {
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
