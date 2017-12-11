package xrc.ai.ml;

import xrc.ai.ml.instance.Attribute;
import xrc.ai.ml.instance.InstanceSet;

public interface MLAlgorithm {

    void train(InstanceSet trainSet, Attribute classAttribute);
}

