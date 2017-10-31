package dzhelyazkov.bestfirstsearch.api;

import java.util.Collection;

/**
 * Created by dzhel on 23.10.2017.
 */
public interface Graph<T extends Node, V extends Edge<T>> {
    Collection<V> getEdges(T node);
}
