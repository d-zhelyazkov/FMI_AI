package sliding_blocks;

import java.util.Collection;

/**
 * Created by dzhel on 26.10.2017.
 */
public class SBUtil {

    /**
     * @see <a href="https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html">TilesSolvability</a>
     */
    public static boolean isSolvable(SBState state) {
        int blankRow = -1;
        int[][] board = state.getBoard();
        int elemsCnt = board.length * board.length - 1;
        int elems[] = new int[elemsCnt];
        int elemIx = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    blankRow = i;
                } else {
                    elems[elemIx++] = board[i][j];
                }
            }
        }

        int inversions = 0;
        for (int i = 0; i < elemsCnt; i++) {
            for (int j = i + 1; j < elemsCnt; j++) {
                if (elems[i] > elems[j]) {
                    inversions++;
                } else if (elems[i] == elems[j]) {
                    throw new IllegalArgumentException(
                            String.format("Element %d appearing several times.", elems[i]));
                }
            }
        }

        blankRow = board.length - blankRow;
        // ( (grid width odd) && (#inversions even) )
        // ||  ( (grid width even) && ((blank on odd row from bottom) == (#inversions even)) )
        return ((board.length % 2 == 1) && (inversions % 2 == 0))
                || ((board.length % 2 == 0) && ((blankRow % 2 == 1) == (inversions % 2 == 0)));
    }

    public static void printPath(Collection<SBTransition<SBState>> path) {
        System.out.println(path.size());
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
