package dzhelyazkov.min_conflicts.nqueens;

import dzhelyazkov.observer.Observable;
import dzhelyazkov.observer.Observer;
import dzhelyazkov.utils.Point2D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class QueenManager implements Observer {
    private final Collection<Queen> queens;

    private final Collection<Queen> queensInConflict;

    QueenManager(int queensCount) {
        queens = new ArrayList<>(queensCount);
        queensInConflict = new HashSet<>(queensCount);

        List<Integer> queenRows = new Random().ints(queensCount, 0, queensCount)
                .boxed().collect(Collectors.toList());
        for (int i = 0; i < queensCount; i++) {
            Point2D queenCell = new Point2D(i, queenRows.get(i));
            Queen queen = new Queen(queenCell);
            queens.add(queen);
            queen.addObserver(this);

            Collection<Queen> queensInConflict = getQueensInConflict(queenCell);
            queen.setQueensInConflict(queensInConflict);
        }
    }

    Collection<Queen> getQueensInConflict(Point2D cell) {
        Collection<Queen> result = new ArrayList<>(queens.size() - 1);

        int col = (int) cell.getX();
        int row = (int) cell.getY();
        for (Queen queen : queens) {
            Point2D queenCell = queen.getCell();
            int queenCol = (int) queenCell.getX();
            int queenRow = (int) queenCell.getY();

            if (queenCol == col)
                continue;

            if ((queenRow == row)
//                    || (Math.abs(queenCol - col) == Math.abs(queen - i))) {
                    || (row - col) == (queenRow - queenCol)
                    || (row + col) == (queenRow + queenCol)) {

                result.add(queen);
            }
        }

        return result;
    }

    @Override
    public void notify(Observable object) {
        Queen queen = (Queen) object;
        if (queen.getQueensInConflict().isEmpty()) {
            queensInConflict.remove(queen);
        } else {
            queensInConflict.add(queen);
        }
    }

    Collection<Queen> getQueensInConflict() {
        return queensInConflict;
    }

    boolean hasQueensInConflict() {
        return !queensInConflict.isEmpty();
    }

    Collection<Queen> getQueens() {
        return queens;
    }
}
