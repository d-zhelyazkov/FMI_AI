package api;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by dzhel on 7.10.2017 г..
 */
public class CompositeNode implements Node {

    private Set<Node> neighbours = new LinkedHashSet<>();

    public void setNeighbours(Collection<Node> neighbours) {
        this.neighbours = new LinkedHashSet<>(neighbours);
    }

    @Override
    public Collection<Node> getNeighbours() {
        return new LinkedHashSet<>(neighbours);
    }
}
