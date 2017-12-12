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
 *     Gaussian naive Bayes</a>
 */
public class GaussianNaiveBayesClassifier extends NaiveBayesClassifier {
    private Map<AttributeValue, ClassStats> classStatistics;

    @Override
    public void train(InstanceSet trainSet, Attribute classAttribute) {
        super.train(trainSet, classAttribute);

        classStatistics = new HashMap<>();
        for (Map.Entry<AttributeValue, Collection<Instance>> mapEntry : classInstances.entrySet()) {
            AttributeValue classValue = mapEntry.getKey();
            Collection<Instance> classInstanceSet = mapEntry.getValue();
            int instanceCount = classInstanceSet.size();

            Map<Attribute, Double> mues = new HashMap<>(attributes.size());
            Map<Attribute, Double> sigmas = new HashMap<>(attributes.size());
            for (Attribute attribute : attributes) {
                double mu = 0;
                for (Instance instance : classInstanceSet)
                    mu += instance.get(attribute).getDoubleValue();
                mu /= instanceCount;
                mues.put(attribute, mu);

                double sumOfDeviations = 0;
                for (Instance instance : classInstanceSet)
                    sumOfDeviations = Math.pow(instance.get(attribute).getDoubleValue() - mu, 2);
                double sigma = Math.sqrt((1.0 / (instanceCount - 1)) * sumOfDeviations);
                sigmas.put(attribute, sigma);
            }

            classStatistics.put(classValue, new ClassStats(mues, sigmas));
        }

    }

    @Override
    protected double getAttributeProbability(AttributeValue classValue, Attribute attribute,
            AttributeValue attributeValue) {
        ClassStats stats = classStatistics.get(classValue);

        double sigma = stats.sigmas.get(attribute);
        double doubleSigmaSq = Math.pow(sigma, 2) * 2;
        double mu = stats.mues.get(attribute);

        double ePow = -Math.pow(attributeValue.getDoubleValue() - mu, 2) / doubleSigmaSq;
        return 1.0 / Math.sqrt(Math.PI * doubleSigmaSq) * Math.pow(Math.E, ePow);

    }

    private class ClassStats {
        final Map<Attribute, Double> mues;

        final Map<Attribute, Double> sigmas;

        ClassStats(Map<Attribute, Double> mues, Map<Attribute, Double> sigmas) {
            this.mues = mues;
            this.sigmas = sigmas;
        }
    }
}
