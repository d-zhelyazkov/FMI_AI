package sliding_blocks.builder;

import sliding_blocks.SBState;
import util.Point;

import java.util.Scanner;

/**
 * Created by dzhel on 28.10.2017.
 */
public class SBStateCinBuilder implements SBStateBuilder {
    @Override
    public SBState build() {

        int[][] board;
        Point emptyCell = null;
        try (Scanner sc = new Scanner(System.in)) {
            int N = sc.nextInt();
            N = ((int) Math.sqrt(N)) + 1;

            board = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    board[i][j] = sc.nextInt();

                    if (board[i][j] == 0) {
                        emptyCell = new Point(j, i);
                    }
                }
            }
        }

        return new SBState(emptyCell, board);
    }
}
