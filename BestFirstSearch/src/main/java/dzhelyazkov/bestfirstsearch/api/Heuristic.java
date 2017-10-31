package dzhelyazkov.bestfirstsearch.api;

/**
 * Created by dzhel on 23.10.2017.
 */
public interface Heuristic<T extends Node> {
    Number calcDistance(T node1, T node2);
}
