package xrc.ai.ml.verifier;

import xrc.ai.ml.Classifier;

public interface ClassifierValidator {

    double evaluateScore(Classifier classifier);
}
