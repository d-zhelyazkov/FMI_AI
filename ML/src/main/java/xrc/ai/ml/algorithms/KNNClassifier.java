package xrc.ai.ml.algorithms;

import xrc.ai.ml.Classifier;
import xrc.ai.ml.instance.AttributeValue;
import xrc.ai.ml.instance.Instance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KNNClassifier extends KNNBase implements Classifier {

    public KNNClassifier(int k) {
        super(k);
    }

    @Override
    public AttributeValue classify(Instance classifiedInstance) {

        Collection<InstanceDistance> nearestNeighbours = getNearestNeighbours(classifiedInstance);
        Map<AttributeValue, ClassEvaluation> classes = new HashMap<>();
        for (InstanceDistance instanceDistance : nearestNeighbours) {
            AttributeValue classValue = instanceDistance.instance.get(classAttribute);
            ClassEvaluation classEvaluation = classes.computeIfAbsent(classValue,
                    classVal -> new ClassEvaluation(classVal, instanceDistance.distance));
            classEvaluation.instancesCount++;
            classEvaluation.bestDistance = Math.min(classEvaluation.bestDistance, instanceDistance.distance);
        }

        List<ClassEvaluation> values = new ArrayList<>(classes.values());
        Collections.sort(values);
        return values.get(0).classValue;
    }

    private class ClassEvaluation implements Comparable<ClassEvaluation> {
        final AttributeValue classValue;

        double bestDistance;

        int instancesCount = 0;

        private ClassEvaluation(AttributeValue classValue, double initialDistance) {
            this.classValue = classValue;
            this.bestDistance = initialDistance;
        }

        @Override
        public int compareTo(ClassEvaluation o) {
            int compare = Integer.compare(o.instancesCount, instancesCount);
            if (compare != 0) {
                return compare;
            }

            return Double.compare(bestDistance, o.bestDistance);
        }
    }
}
