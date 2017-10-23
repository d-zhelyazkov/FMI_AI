package frogs;

import api.Node;
import util.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static frogs.State.EMPTY;
import static frogs.State.LEFT;
import static frogs.State.RIGHT;

/**
 * Created by dzhel on 8.10.2017 г..
 */
public class FrogsField implements Node {

    private static final int FARTHEST_JUMP = 2;

    private final char[] field;

    FrogsField(char[] field) {
        this.field = field.clone();
    }

    @Override
    public Collection<Node> getNeighbours() {

        Collection<Node> result = new ArrayList<>();

        for (int i = 0; i < field.length; i++) {
            int ixToSwap = -1;
            switch (field[i]) {
            case EMPTY:
                continue;
            case LEFT:
                ixToSwap = findLeftSpace(i);
                break;
            case RIGHT:
                ixToSwap = findRightSpace(i);
                break;
            }
            if (ixToSwap == -1) {
                continue;
            }

            char[] neighbourField = field.clone();
            ArrayUtils.swap(neighbourField, i, ixToSwap);
            result.add(new FrogsField(neighbourField));
        }

        return result;
    }

    private int findRightSpace(int ix) {
        for (int i = 1; i <= FARTHEST_JUMP; i++) {
            int candidate = ix + i;
            if (candidate >= field.length) {
                return -1;
            }

            if (field[candidate] == EMPTY) {
                return candidate;
            }
        }
        return -1;
    }

    private int findLeftSpace(int ix) {
        for (int i = 1; i <= FARTHEST_JUMP; i++) {
            int candidate = ix - i;
            if (candidate < 0) {
                return -1;
            }

            if (field[candidate] == EMPTY) {
                return candidate;
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        FrogsField frogsNode = (FrogsField) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(field, frogsNode.field);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(field);
    }

    @Override
    public String toString() {
        return String.valueOf(field);
    }
}
