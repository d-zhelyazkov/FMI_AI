package api;

import java.util.Collection;

/**
 * Created by dzhel on 23.10.2017.
 */
public interface GraphSearch<T extends Node, V extends Edge<T>> {
    Collection<V> findPath(T from, T to, Graph<T, V> graph) throws NoPathFoundException;
}
