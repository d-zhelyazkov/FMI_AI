package dzhelyazkov.bestfirstsearch.api;

/**
 * Created by dzhel on 23.10.2017.
 */
public interface Edge<T extends Node> {
    T from();

    T to();

    double weight();
}
