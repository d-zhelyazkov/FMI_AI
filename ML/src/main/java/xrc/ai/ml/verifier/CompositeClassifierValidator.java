package xrc.ai.ml.verifier;

import xrc.ai.ml.Classifier;

public interface CompositeClassifierValidator extends ClassifierValidator {

    default double evaluateValidatorScore(ClassifierValidator validator, Classifier classifier) {
        return validator.evaluateScore(classifier);
    }
}
