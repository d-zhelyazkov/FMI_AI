package xrc.ai.ml.algorithms;

import xrc.ai.ml.instance.Attribute;
import xrc.ai.ml.instance.AttributeValue;
import xrc.ai.ml.instance.Instance;
import xrc.ai.ml.instance.InstanceSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://en.wikipedia.org/wiki/Naive_Bayes_classifier#Gaussian_naive_Bayes">
 * Gaussian naive Bayes</a>
 */
public class GaussianNaiveBayesClassifier extends NaiveBayesClassifier {

    private static final double SQRT_2PI = Math.sqrt(2 * Math.PI);

    private Map<AttributeValue, ClassStats> classStatistics;

    @Override
    public void train(InstanceSet trainSet, Attribute classAttribute) {
        super.train(trainSet, classAttribute);

        classStatistics = new HashMap<>();
        for (Map.Entry<AttributeValue, Collection<Instance>> mapEntry : classInstances.entrySet()) {
            AttributeValue classValue = mapEntry.getKey();
            Collection<Instance> classInstanceSet = mapEntry.getValue();
            int instanceCount = classInstanceSet.size();

            Map<Attribute, Double> means = new HashMap<>(attributes.size());
            Map<Attribute, Double> standardDeviations = new HashMap<>(attributes.size());
            for (Attribute attribute : attributes) {
                double mean = 0;
                for (Instance instance : classInstanceSet)
                    mean += instance.get(attribute).getDoubleValue();
                mean /= instanceCount;
                means.put(attribute, mean);

                double sumOfDeviations = 0;
                for (Instance instance : classInstanceSet)
                    sumOfDeviations = Math.pow(instance.get(attribute).getDoubleValue() - mean, 2);
                double standardDeviation = Math.sqrt(sumOfDeviations / (instanceCount - 1));
                standardDeviations.put(attribute, standardDeviation);
            }

            classStatistics.put(classValue, new ClassStats(means, standardDeviations));
        }

    }

    @Override
    protected double getAttributeProbability(AttributeValue classValue, Attribute attribute,
            AttributeValue attributeValue) {
        ClassStats stats = classStatistics.get(classValue);

        double stdDev = stats.standardDeviations.get(attribute);
        double mean = stats.means.get(attribute);

        return Math.exp(-Math.pow(attributeValue.getDoubleValue() - mean, 2) / (2 * stdDev))
                / (SQRT_2PI * stdDev);

    }

    private class ClassStats {
        final Map<Attribute, Double> means;

        final Map<Attribute, Double> standardDeviations;

        ClassStats(Map<Attribute, Double> means, Map<Attribute, Double> standardDeviations) {
            this.means = means;
            this.standardDeviations = standardDeviations;
        }
    }
}
