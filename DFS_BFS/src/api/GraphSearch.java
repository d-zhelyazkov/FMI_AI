package api;

import java.util.Collection;

/**
 * Created by dzhel on 8.10.2017 г..
 */
public interface GraphSearch {

    Collection<Node> searchPath(Node start, Node goal);
}
