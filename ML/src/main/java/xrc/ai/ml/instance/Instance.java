package xrc.ai.ml.instance;

import java.util.Collection;

public interface Instance {
    AttributeValue get(Attribute attribute);

    Collection<Attribute> getAttributes();
}
