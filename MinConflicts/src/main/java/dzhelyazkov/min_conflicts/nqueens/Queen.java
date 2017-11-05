package dzhelyazkov.min_conflicts.nqueens;

import dzhelyazkov.observer.Observable;
import dzhelyazkov.observer.Observer;
import dzhelyazkov.utils.Point2D;

import java.util.Collection;
import java.util.HashSet;

public class Queen extends Observable implements Observer {

    private Point2D mCell;

    private Collection<Queen> mQueensInConflict = new HashSet<>();

    Queen(Point2D cell) {
        mCell = cell;
    }


    void setQueensInConflict(Collection<Queen> queensInConflict) {
        Collection<Queen> queensNoLongerInConflict = new HashSet<>(mQueensInConflict);
        queensNoLongerInConflict.removeAll(queensInConflict);
        for (Queen queen : queensNoLongerInConflict) {
            queen.removeObserver(this);
        }

        Collection<Queen> newQueensInConflict = new HashSet<>(queensInConflict);
        newQueensInConflict.removeAll(mQueensInConflict);
        for (Queen queen : newQueensInConflict) {
            queen.addQueenInConflict(this, true);
            this.addQueenInConflict(queen, false);
        }

        mQueensInConflict = new HashSet<>(queensInConflict);
        notifyObservers();
    }

    private void addQueenInConflict(Queen queen, boolean notify) {
        mQueensInConflict.add(queen);
        queen.addObserver(this);

        if (notify) {
            notifyObservers();
        }
    }

    @Override
    public void notify(Observable object) {
        Queen queen = (Queen) object;
        if (queen.getQueensInConflict().contains(this)) {
            return;
        }

        queen.removeObserver(this);
        mQueensInConflict.remove(queen);
        notifyObservers();

    }

    Collection<Queen> getQueensInConflict() {
        return mQueensInConflict;
    }

    Point2D getCell() {
        return mCell;
    }

    void setCell(Point2D mCell) {
        this.mCell = mCell;
    }
}
