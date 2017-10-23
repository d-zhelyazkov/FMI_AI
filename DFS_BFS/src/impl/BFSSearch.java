package impl;

import api.GraphSearch;
import api.Node;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by dzhel on 11.10.2017 г..
 */
public class BFSSearch implements GraphSearch {
    @Override
    public Collection<Node> searchPath(Node start, Node goal) {
        if (start.equals(goal)) {
            return Collections.singletonList(start);
        }

        Map<Node, Node> nodeParents = new HashMap<>();
        Queue<Node> traverseQueue = new ArrayDeque<>();

        nodeParents.put(start, null);
        traverseQueue.add(start);

        while (!traverseQueue.isEmpty() && !nodeParents.containsKey(goal)) {
            Node node = traverseQueue.poll();
            Collection<Node> neighbours = node.getNeighbours();
            for (Node neighbour : neighbours) {
                if (nodeParents.containsKey(neighbour)) {
                    continue;
                }

                nodeParents.put(neighbour, node);
                traverseQueue.add(neighbour);

                if (neighbour.equals(goal)) {
                    break;
                }
            }

        }

        Stack<Node> path = new Stack<>();

        for (Node node = goal; node != null; node = nodeParents.get(node)) {
            path.add(node);
        }

        return path;
    }
}
