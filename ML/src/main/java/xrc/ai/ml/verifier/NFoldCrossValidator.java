package xrc.ai.ml.verifier;

import xrc.ai.ml.Classifier;
import xrc.ai.ml.instance.Attribute;
import xrc.ai.ml.instance.Instance;
import xrc.ai.ml.instance.InstanceSet;
import xrc.ai.ml.instance.InstanceSetBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NFoldCrossValidator implements ClassifierValidator {

    private final Collection<ClassifierValidator> validators;

    private final double[] lastEvaluations;

    public NFoldCrossValidator(int N, InstanceSet instanceSet, Attribute classAttribute) {
        validators = new ArrayList<>(N);
        lastEvaluations = new double[N];

        Collection<Attribute> attributes = instanceSet.getAttributes();
        List<Instance> instances = new ArrayList<>(instanceSet.getInstances());
        Collections.shuffle(instances);

        int testSetSize = instances.size() / N;
        for (int i = 0; i < N; i++) {
            List<Instance> trainInstances = new ArrayList<>(instances);
            int ix = i * testSetSize;
            List<Instance> testInstances = trainInstances.subList(ix, ix + testSetSize);

            InstanceSet testSet = new InstanceSetBean(new ArrayList<>(testInstances), attributes);
            testInstances.clear();
            InstanceSet trainSet = new InstanceSetBean(trainInstances, attributes);

            validators.add(new SimpleClassifierValidator(trainSet, testSet, classAttribute));
        }
    }

    @Override
    public double evaluateScore(Classifier classifier) {
        double sumOfScores = 0;
        int scoreIx = 0;
        for (ClassifierValidator verifier : validators) {
            double score = verifier.evaluateScore(classifier);
            sumOfScores += score;

            lastEvaluations[scoreIx++] = score;
        }

        return sumOfScores / validators.size();
    }

    public double[] getLastEvaluations() {
        return lastEvaluations;
    }
}
