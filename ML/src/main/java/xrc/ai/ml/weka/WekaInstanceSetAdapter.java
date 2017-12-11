package xrc.ai.ml.weka;

import weka.core.Instances;
import xrc.ai.ml.instance.Attribute;
import xrc.ai.ml.instance.Instance;
import xrc.ai.ml.instance.InstanceSet;
import xrc.utils.Enumerations;

import java.util.Collection;
import java.util.stream.Collectors;

public class WekaInstanceSetAdapter implements InstanceSet {

    private final Instances instanceSet;

    public WekaInstanceSetAdapter(Instances instanceSet) {
        this.instanceSet = instanceSet;
    }

    @Override
    public Collection<Instance> getInstances() {
        return Enumerations.stream(instanceSet.enumerateInstances())
                .map(WekaInstanceAdapter::new).collect(Collectors.toList());
    }

    @Override
    public Collection<Attribute> getAttributes() {
        return Enumerations.stream(instanceSet.enumerateAttributes()).map(WekaAttributeAdapter::new)
                .collect(Collectors.toList());
    }
}
