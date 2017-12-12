package xrc.ai.ml.weka;

import weka.core.Attribute;
import weka.core.Instance;
import xrc.ai.ml.instance.AttributeValue;
import xrc.utils.Enumerations;

import java.util.Collection;
import java.util.stream.Collectors;

public class WekaInstanceAdapter implements xrc.ai.ml.instance.Instance {
    private final Instance instance;

    public WekaInstanceAdapter(Instance instance) {
        this.instance = instance;
    }

    @Override
    public AttributeValue get(xrc.ai.ml.instance.Attribute attribute) {
        Attribute wekaAttr = ((WekaAttributeAdapter) attribute).getAttribute();
        return new WekaAttributeValue(instance, wekaAttr);
    }

    @Override
    public Collection<xrc.ai.ml.instance.Attribute> getAttributes() {
        return Enumerations.stream(instance.enumerateAttributes())
                .map(WekaAttributeAdapter::new).collect(Collectors.toList());
    }
}
