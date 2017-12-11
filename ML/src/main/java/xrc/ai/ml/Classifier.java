package xrc.ai.ml;

import xrc.ai.ml.instance.AttributeValue;
import xrc.ai.ml.instance.Instance;

public interface Classifier extends MLAlgorithm {

    /**
     * @return the class value of {@code instance}
     */
    AttributeValue classify(Instance instance);
}
