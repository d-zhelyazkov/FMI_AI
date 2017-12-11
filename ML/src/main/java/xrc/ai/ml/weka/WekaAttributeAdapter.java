package xrc.ai.ml.weka;

import weka.core.Attribute;

public class WekaAttributeAdapter implements xrc.ai.ml.instance.Attribute {
    private final Attribute attribute;

    public WekaAttributeAdapter(Attribute attribute) {
        this.attribute = attribute;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        WekaAttributeAdapter that = (WekaAttributeAdapter) o;

        return attribute.equals(that.attribute);
    }

    @Override
    public int hashCode() {
        return attribute.hashCode();
    }

    @Override
    public String toString() {
        return attribute.toString();
    }
}
