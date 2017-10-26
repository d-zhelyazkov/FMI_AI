package sliding_blocks;

import java.util.Collection;

/**
 * Created by dzhel on 26.10.2017.
 */
public class SBPrinter {

    public static void printPath(Collection<SBTransition> path) {
        for (SBTransition transition : path) {
            System.out.println(transition.getType().toString().toLowerCase());
        }

    }

    public static void printState(SBState initialState) {
        int[][] board = initialState.getBoard();
        for (int[] aBoard : board) {
            for (int anABoard : aBoard) {
                System.out.printf("%d ", anABoard);
            }
            System.out.println();
        }
    }
}
