package dzhelyazkov.genetic_algorithms.impl;

import dzhelyazkov.genetic_algorithms.Gene;

public class ObjectGene<ObjectType> implements Gene {
    private final ObjectType object;

    public ObjectGene(ObjectType object) {
        this.object = object;
    }

    public ObjectType getObject() {
        return object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ObjectGene<?> that = (ObjectGene<?>) o;

        return object.equals(that.object);
    }

    @Override
    public int hashCode() {
        return object.hashCode();
    }
}
