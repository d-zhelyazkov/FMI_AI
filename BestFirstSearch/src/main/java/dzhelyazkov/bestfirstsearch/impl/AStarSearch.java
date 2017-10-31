package dzhelyazkov.bestfirstsearch.impl;

import dzhelyazkov.bestfirstsearch.api.Edge;
import dzhelyazkov.bestfirstsearch.api.Graph;
import dzhelyazkov.bestfirstsearch.api.GraphSearch;
import dzhelyazkov.bestfirstsearch.api.Heuristic;
import dzhelyazkov.bestfirstsearch.api.NoPathFoundException;
import dzhelyazkov.bestfirstsearch.api.Node;

import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

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

        Set<T> closedSet = new HashSet<>();
        Map<T, AStarTraverseEntry<T, V>> openSet = new HashMap<>();
        BeginEntry<T, V> beginEntry = new BeginEntry<>(start);
        openSet.put(start, beginEntry);
        Queue<AStarTraverseEntry<T, V>> traverseQueue = new PriorityQueue<>();
        traverseQueue.add(beginEntry);

        while (!traverseQueue.isEmpty()) {
            AStarTraverseEntry<T, V> traverseEntry = traverseQueue.poll();
            T node = traverseEntry.getNode();
            if (closedSet.contains(node)) {
                continue;
            }
            if (node.equals(goal)) {
                return constructPath(traverseEntry);
            }

            closedSet.add(node);
            openSet.remove(node);

            Collection<V> edges = graph.getEdges(node);
            for (V edge : edges) {
                T neighbour = edge.to();
                if (closedSet.contains(neighbour)) {
                    continue;
                }

                double cost = traverseEntry.getCost() + edge.weight();
                AStarTraverseEntry<T, V> neighbourTraverseEntry = openSet.get(neighbour);
                double estimation;
                if (neighbourTraverseEntry != null) {
                    if (cost >= neighbourTraverseEntry.getCost()) {
                        continue;
                    }

                    estimation = neighbourTraverseEntry.getEstimatedRemainingCost();
                } else {
                    estimation = heuristic.calcDistance(neighbour, goal).doubleValue();
                }

                neighbourTraverseEntry = new AStarTraverseEntry<>(edge, traverseEntry, cost, estimation);
                openSet.put(neighbour, neighbourTraverseEntry);
                traverseQueue.add(neighbourTraverseEntry);

            }
        }

        throw new NoPathFoundException();
    }

    private Collection<V> constructPath(AStarTraverseEntry<T, V> traverseEntry) {
        Deque<V> path = new LinkedList<V>();

        for (; !(traverseEntry instanceof BeginEntry); traverseEntry = traverseEntry.getPrevEntry()) {
            path.addFirst(traverseEntry.getEdge());
        }

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

    public double getEstimatedRemainingCost() {
        return estimatedRemainingCost;
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
