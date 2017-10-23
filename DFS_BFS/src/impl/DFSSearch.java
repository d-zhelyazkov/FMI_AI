package impl;

import api.GraphSearch;
import api.Node;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dzhel on 8.10.2017 г..
 */
public class DFSSearch implements GraphSearch {

    private Set<Node> searchedNodes;

    private Deque<Node> path;

    @Override
    public Collection<Node> searchPath(Node start, Node goal) {
        searchedNodes = new HashSet<>();
        path = new ArrayDeque<>();

        boolean result = search(start, goal);
        return (result) ? path : Collections.emptyList();
    }

    private boolean search(Node node, Node goal) {
        if (searchedNodes.contains(node)) {
            return false;
        }
        searchedNodes.add(node);
        path.add(node);

        if (node.equals(goal)) {
            return true;
        }

        Collection<Node> neighbours = node.getNeighbours();
        for (Node neighbour : neighbours) {
            boolean result = search(neighbour, goal);
            if (result) {
                return true;
            }
        }

        path.removeLast();
        return false;
    }
}
