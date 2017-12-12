package xrc.ai.ml.verifier;

import xrc.ai.ml.Classifier;

public class ClassifierValidatorDecorator implements ClassifierValidator {

    private final ClassifierValidator validator;

    public ClassifierValidatorDecorator(ClassifierValidator validator) {
        this.validator = validator;
    }

    @Override
    public double evaluateScore(Classifier classifier) {
        return validator.evaluateScore(classifier);
    }
}
