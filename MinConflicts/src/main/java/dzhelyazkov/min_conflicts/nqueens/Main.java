package dzhelyazkov.min_conflicts.nqueens;

import dzhelyazkov.utils.Collections;
import dzhelyazkov.utils.Point2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dzhel on 04.11.2017.
 */
public class Main {
    private static final int N;

    static {
        try (Scanner sc = new Scanner(System.in)) {
            N = sc.nextInt();
        }
    }

    public static void main(String[] args) {

        QueenManager queenManager = new QueenManager(N);
        while (queenManager.hasQueensInConflict()) {
            Queen queen = Collections.getRandomElement(queenManager.getQueensInConflict());
            Point2D queenCell = queen.getCell();
            int col = (int) queenCell.getX();
            int row = (int) queenCell.getY();

            List<TestEntry> candidates = new ArrayList<>();
            candidates.add(new TestEntry(queenCell, queen.getQueensInConflict()));
            for (int i = 0; i < N; i++) {
                if (i == row) {
                    continue;
                }

                Point2D cell = new Point2D(col, i);
                Collection<Queen> queensInConflict = queenManager.getQueensInConflict(cell);
                TestEntry testEntry = new TestEntry(cell, queensInConflict);

                int compare = Integer.compare(queensInConflict.size(), candidates.get(0).queensInConflict.size());
                switch (compare) {
                case 0:
                    candidates.add(testEntry);
                    break;
                case -1:
                    candidates = new ArrayList<>();
                    candidates.add(testEntry);
                    break;
                }
            }

            TestEntry testEntry = Collections.getRandomElement(candidates);
            if (testEntry.cell.equals(queenCell)) {
                continue;
            }

            queen.setCell(testEntry.cell);
            queen.setQueensInConflict(testEntry.queensInConflict);
        }

        print(queenManager.getQueens());

    }

    private static void print(Collection<Queen> queens) {
        int cols[] = new int[N];
        for (Queen queen : queens) {
            Point2D cell = queen.getCell();
            cols[(int) cell.getY()] = (int) cell.getX();
        }

        char[] line = new char[N];
        for (int col : cols) {
            Arrays.fill(line,0, col, '.');
            line[col] = '*';
            Arrays.fill(line,col + 1, N, '.');
            System.out.println(line);
        }

    }
}

class TestEntry {
    Point2D cell;

    Collection<Queen> queensInConflict;

    TestEntry(Point2D cell, Collection<Queen> queensInConflict) {
        this.cell = cell;
        this.queensInConflict = queensInConflict;
    }
}
