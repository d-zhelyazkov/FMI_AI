package impl;

import api.CompositeNode;

/**
 * Created by dzhel on 7.10.2017 г..
 */
public class CharNode extends CompositeNode {
    private final char id;

    public CharNode(char id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharNode charNode = (CharNode) o;

        return id == charNode.id;
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public String toString() {
        return "Node{" + id + '}';
    }
}
