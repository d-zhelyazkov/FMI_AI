package api;

import java.util.Collection;

/**
 * Created by dzhel on 7.10.2017 г..
 */
public interface Node {
    Collection<Node> getNeighbours();
}
