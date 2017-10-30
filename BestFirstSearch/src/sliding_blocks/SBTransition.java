package sliding_blocks;

import api.EdgeBase;

/**
 * Created by dzhel on 25.10.2017.
 */
public class SBTransition<T extends SBState> extends EdgeBase<T> {

    private final Type type;

    public SBTransition(T from, T to, Type type) {
        super(from, to, 1);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        UP(0, 1),
        DOWN(0, -1),
        LEFT(1, 0),
        RIGHT(-1, 0);

        public final int xDiff;
        public final int yDiff;

        Type(int xDiff, int yDiff) {
            this.xDiff = xDiff;
            this.yDiff = yDiff;
        }
    }
}

