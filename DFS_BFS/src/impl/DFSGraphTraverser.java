package impl;

import api.GraphTraverser;
import api.Node;
import api.NodeProcessor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dzhel on 7.10.2017 г..
 */
public class DFSGraphTraverser implements GraphTraverser {

    private final NodeProcessor nodeProcessor;

    private Set<Node> traversedNodes;

    public DFSGraphTraverser(NodeProcessor nodeProcessor) {
        this.nodeProcessor = nodeProcessor;
    }

    @Override
    public void traverse(Node begin) {
        traversedNodes = new HashSet<>();
        innerTraverse(begin);
    }

    private void innerTraverse(Node node) {
        if (traversedNodes.contains(node)) {
            return;
        }
        nodeProcessor.process(node);
        traversedNodes.add(node);

        Collection<Node> neighbours = node.getNeighbours();
        for (Node neighbour : neighbours) {
            innerTraverse(neighbour);
        }

    }
}
