package api;

/**
 * Created by dzhel on 23.10.2017.
 */
public class EdgeBase<T extends Node> implements Edge<T> {

    private final T from;

    private final T to;

    private final double weight;

    public EdgeBase(T from, T to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public T from() {
        return from;
    }

    @Override
    public T to() {
        return to;
    }

    @Override
    public double weight() {
        return weight;
    }
}
