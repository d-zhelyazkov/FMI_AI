package impl;

import api.Edge;
import api.EdgeBase;
import api.Graph;
import api.GraphSearch;
import api.Heuristic;
import api.NoPathFoundException;
import api.Node;

import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Created by dzhel on 23.10.2017.
 */
public class AStarSearch<T extends Node> implements GraphSearch<T> {

    private final Heuristic<T> heuristic;

    public AStarSearch(Heuristic<T> heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public Collection<Edge<T>> findPath(T start, T goal, Graph<T> graph) throws NoPathFoundException {
        Queue<AStarTraverseEntry<T>> traverseQueue = new PriorityQueue<>();
        traverseQueue.add(new BeginEntry<>(start));
        Set<T> traversedNodes = new HashSet<>();

        while (!traverseQueue.isEmpty()) {
            AStarTraverseEntry<T> traverseEntry = traverseQueue.poll();
            T node = traverseEntry.edge.to();
            if (traversedNodes.contains(node)) {
                continue;
            }
            traversedNodes.add(node);

            if (node.equals(goal)) {
                return constructPathStack(traverseEntry);
            }

            Collection<Edge<T>> edges = graph.getEdges(node);
            for (Edge<T> edge : edges) {
                double cost = traverseEntry.cost + edge.weight();
                double estimation = heuristic.estimate(edge.to(), goal);
                traverseQueue.add(
                        new AStarTraverseEntry<T>(edge, traverseEntry, cost, estimation));
            }

        }

        throw new NoPathFoundException();
    }

    private Stack<Edge<T>> constructPathStack(AStarTraverseEntry<T> traverseEntry) {
        Stack<Edge<T>> path = new Stack<>();

        do {
            path.add(traverseEntry.edge);
            traverseEntry = traverseEntry.prevEntry;

        } while (!(traverseEntry instanceof BeginEntry));

        return path;
    }

}

class AStarTraverseEntry<T extends Node> implements Comparable<AStarTraverseEntry<T>> {
    final Edge<T> edge;

    final AStarTraverseEntry<T> prevEntry;

    final double cost;

    final double estimatedRemainingCost;

    AStarTraverseEntry(Edge<T> edge, AStarTraverseEntry<T> prevEntry, double cost, double estimatedRemainingCost) {
        this.edge = edge;
        this.prevEntry = prevEntry;
        this.cost = cost;
        this.estimatedRemainingCost = estimatedRemainingCost;
    }

    @Override
    public int compareTo(AStarTraverseEntry<T> o) {
        return Double.compare(cost + estimatedRemainingCost, o.cost + o.estimatedRemainingCost);
    }
}

class BeginEntry<T extends Node> extends AStarTraverseEntry<T> {

    BeginEntry(T start) {
        super(
                new EdgeBase<>(null, start, 0),
                null, 0, 0);
    }
}
