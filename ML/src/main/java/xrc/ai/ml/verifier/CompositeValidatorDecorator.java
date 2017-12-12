package xrc.ai.ml.verifier;

public class CompositeValidatorDecorator extends ClassifierValidatorDecorator implements CompositeClassifierValidator {

    private final CompositeClassifierValidator validator;

    public CompositeValidatorDecorator(CompositeClassifierValidator validator) {
        super(validator);
        this.validator = validator;
    }
}
