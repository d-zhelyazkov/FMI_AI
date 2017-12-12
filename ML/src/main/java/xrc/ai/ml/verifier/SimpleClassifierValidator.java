package xrc.ai.ml.verifier;

import xrc.ai.ml.Classifier;
import xrc.ai.ml.instance.Attribute;
import xrc.ai.ml.instance.AttributeValue;
import xrc.ai.ml.instance.Instance;
import xrc.ai.ml.instance.InstanceSet;

import java.util.Collection;

public class SimpleClassifierValidator implements ClassifierValidator {

    private final InstanceSet trainSet;

    private final InstanceSet testSet;

    private final Attribute classAttribute;

    public SimpleClassifierValidator(InstanceSet trainSet, InstanceSet testSet, Attribute classAttribute) {
        this.trainSet = trainSet;
        this.testSet = testSet;
        this.classAttribute = classAttribute;
    }

    @Override
    public double evaluateScore(Classifier classifier) {
        classifier.train(trainSet, classAttribute);

        int correctlyClassified = 0;
        Collection<Instance> instances = testSet.getInstances();
        for (Instance instance : instances) {
            AttributeValue aClass = classifier.classify(instance);
            if (aClass.equals(instance.get(classAttribute))) {
                correctlyClassified++;
            }
        }

        return ((double) correctlyClassified) / instances.size();
    }

}
