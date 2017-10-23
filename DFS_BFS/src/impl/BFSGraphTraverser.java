package impl;

import api.GraphTraverser;
import api.Node;
import api.NodeProcessor;

import java.util.*;

/**
 * Created by dzhel on 7.10.2017 г..
 */
public class BFSGraphTraverser implements GraphTraverser {

    private final NodeProcessor nodeProcessor;

    public BFSGraphTraverser(NodeProcessor nodeProcessor) {
        this.nodeProcessor = nodeProcessor;
    }

    @Override
    public void traverse(Node begin) {
        Set<Node> traversedNodes = new HashSet<>();
        Queue<Node> traverseQueue = new ArrayDeque<>();
        traverseQueue.add(begin);
        traversedNodes.add(begin);

        while (!traverseQueue.isEmpty()) {
            Node node = traverseQueue.poll();

            nodeProcessor.process(node);

            Collection<Node> neighbours = node.getNeighbours();
            for (Node neighbour : neighbours) {
                if (traversedNodes.contains(neighbour)) {
                    continue;
                }

                traversedNodes.add(neighbour);
                traverseQueue.add(neighbour);
            }

        }
    }
}
