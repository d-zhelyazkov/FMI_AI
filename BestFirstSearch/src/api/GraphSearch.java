package api;

import java.util.Collection;

/**
 * Created by dzhel on 23.10.2017.
 */
public interface GraphSearch<T extends Node> {
    Collection<Edge<T>> findPath(T from, T to, Graph<T> graph) throws NoPathFoundException;
}
