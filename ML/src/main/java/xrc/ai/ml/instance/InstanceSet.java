package xrc.ai.ml.instance;

import java.util.Collection;

public interface InstanceSet {
    Collection<Instance> getInstances();

    Collection<Attribute> getAttributes();
}
