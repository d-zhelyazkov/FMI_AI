package xrc.ai.ml.instance;

import java.util.Collection;

public class InstanceSetBean        implements InstanceSet{

    private final Collection<Instance> instances;

    private final Collection<Attribute> attributes;

    public InstanceSetBean(Collection<Instance> instances, Collection<Attribute> attributes) {
        this.instances = instances;
        this.attributes = attributes;
    }

    public Collection<Instance> getInstances() {
        return instances;
    }

    public Collection<Attribute> getAttributes() {
        return attributes;
    }
}
