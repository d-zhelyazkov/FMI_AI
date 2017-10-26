package impl;

import api.Edge;
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
public class AStarSearch<T extends Node, V extends Edge<T>> implements GraphSearch<T, V> {

    private final Heuristic<T> heuristic;

    public AStarSearch(Heuristic<T> heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public Collection<V> findPath(T start, T goal, Graph<T, V> graph) throws NoPathFoundException {

        Queue<AStarTraverseEntry<T, V>> traverseQueue = new PriorityQueue<>();
        traverseQueue.add(new BeginEntry<>(start));
        Set<T> traversedNodes = new HashSet<>();

        while (!traverseQueue.isEmpty()) {
            AStarTraverseEntry<T, V> traverseEntry = traverseQueue.poll();
            T node = traverseEntry.getNode();
            if (traversedNodes.contains(node)) {
                continue;
            }
            traversedNodes.add(node);

            if (node.equals(goal)) {
                return constructPathStack(traverseEntry);
            }

            Collection<V> edges = graph.getEdges(node);
            for (V edge : edges) {
                if (traversedNodes.contains(edge.to())) {
                    continue;
                }

                double cost = traverseEntry.getCost() + edge.weight();
                double estimation = heuristic.calcDistance(edge.to(), goal).doubleValue();
                traverseQueue.add(new AStarTraverseEntry<>(edge, traverseEntry, cost, estimation));
            }
        }

        throw new NoPathFoundException();
    }

    private Stack<V> constructPathStack(AStarTraverseEntry<T, V> traverseEntry) {
        Stack<V> path = new Stack<>();

        do {
            path.add(traverseEntry.getEdge());
            traverseEntry = traverseEntry.getPrevEntry();

        } while (!(traverseEntry instanceof BeginEntry));

        return path;
    }

}

class AStarTraverseEntry<T extends Node, V extends Edge<T>> implements Comparable<AStarTraverseEntry<T, V>> {
    private final V edge;

    private final AStarTraverseEntry<T, V> prevEntry;

    private final double cost;

    private final double estimatedRemainingCost;

    AStarTraverseEntry(V edge, AStarTraverseEntry<T, V> prevEntry, double cost, double estimatedRemainingCost) {
        this.edge = edge;
        this.prevEntry = prevEntry;
        this.cost = cost;
        this.estimatedRemainingCost = estimatedRemainingCost;
    }

    @Override
    public int compareTo(AStarTraverseEntry<T, V> o) {
        return Double.compare(cost + estimatedRemainingCost, o.cost + o.estimatedRemainingCost);
    }

    public V getEdge() {
        return edge;
    }

    public T getNode() {
        return edge.to();
    }

    public AStarTraverseEntry<T, V> getPrevEntry() {
        return prevEntry;
    }

    public double getCost() {
        return cost;
    }
}

class BeginEntry<T extends Node, V extends Edge<T>> extends AStarTraverseEntry<T, V> {

    private final T startNode;

    BeginEntry(T startNode) {
        super(null, null, 0, 0);
        this.startNode = startNode;
    }

    @Override
    public T getNode() {
        return startNode;
    }
}
