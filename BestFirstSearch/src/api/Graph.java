package api;

import java.util.Collection;

/**
 * Created by dzhel on 23.10.2017.
 */
public interface Graph<T extends Node> {
    Collection<Edge<T>> getEdges(T node);
}
