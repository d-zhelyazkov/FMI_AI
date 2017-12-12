package xrc.ai.ml.algorithms;

import xrc.ai.ml.Classifier;
import xrc.ai.ml.instance.Attribute;
import xrc.ai.ml.instance.AttributeValue;
import xrc.ai.ml.instance.Instance;
import xrc.ai.ml.instance.InstanceSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class NaiveBayesClassifier implements Classifier {

    protected Map<AttributeValue, Double> classProbabilities;

    protected Map<AttributeValue, Collection<Instance>> classInstances;

    protected Collection<Attribute> attributes;

    @Override
    public void train(InstanceSet trainSet, Attribute classAttribute) {
        Collection<Instance> instances = trainSet.getInstances();

        classInstances = new HashMap<>();
        for (Instance instance : instances) {
            AttributeValue classStr = instance.get(classAttribute);
            Collection<Instance> classInstanceSet = classInstances.computeIfAbsent(classStr, k -> new ArrayList<>());
            classInstanceSet.add(instance);
        }

        classProbabilities = new HashMap<>();
        attributes = trainSet.getAttributes();
        attributes.remove(classAttribute);

        for (Map.Entry<AttributeValue, Collection<Instance>> mapEntry : classInstances.entrySet()) {
            AttributeValue classValue = mapEntry.getKey();
            Collection<Instance> classInstanceSet = mapEntry.getValue();
            int instanceCount = classInstanceSet.size();

            double probability = (double) instanceCount / instances.size();
            classProbabilities.put(classValue, probability);
        }

    }

    /**
     * <a href="https://en.wikipedia.org/wiki/Naive_Bayes_classifier#Constructing_a_classifier_from_the_probability_model">
     *     Constructing a classifier from the probability model</a>
     */
    @Override
    public AttributeValue classify(Instance instance) {
        double maxProbability = 0;
        AttributeValue maxClass = null;

        for (AttributeValue classValue : classProbabilities.keySet()) {

            double probabilitiesProduct = 1;
            for (Attribute attribute : attributes) {
                AttributeValue attributeValue = instance.get(attribute);
                probabilitiesProduct *= getAttributeProbability(classValue, attribute, attributeValue);
            }

            double probability = probabilitiesProduct * classProbabilities.get(classValue);
            if ((maxClass == null) || (probability > maxProbability)) {
                maxProbability = probability;
                maxClass = classValue;
            }
        }

        return maxClass;
    }

    protected abstract double getAttributeProbability(
            AttributeValue classValue,
            Attribute attribute,
            AttributeValue attributeValue);

}
