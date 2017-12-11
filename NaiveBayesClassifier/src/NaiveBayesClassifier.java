import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NaiveBayesClassifier {

    private final Map<String, ClassStats> classStatistics = new HashMap<>();

    private final Collection<Attribute> attributes;

    public NaiveBayesClassifier(Instances instances) {
        Attribute classAttribute = instances.classAttribute();

        Map<String, Collection<Instance>> classInstances = new HashMap<>();
        for (Instance instance : instances) {
            String classStr = instance.stringValue(classAttribute);
            Collection<Instance> classInstanceSet = classInstances.computeIfAbsent(classStr, k -> new ArrayList<>());
            classInstanceSet.add(instance);
        }

        attributes = Collections.list(instances.enumerateAttributes());
        attributes.remove(classAttribute);
        for (Map.Entry<String, Collection<Instance>> mapEntry : classInstances.entrySet()) {
            Collection<Instance> instanceSet = mapEntry.getValue();
            int instanceCount = instanceSet.size();

            double probability = (double) instanceCount / instances.size();

            Map<Attribute, Double> mues = new HashMap<>(attributes.size());
            Map<Attribute, Double> sigmas = new HashMap<>(attributes.size());
            for (Attribute attribute : attributes) {
                double mu = 0;
                for (Instance instance : instanceSet)
                    mu += instance.value(attribute);
                mu /= instanceCount;
                mues.put(attribute, mu);

                double sumOfDeviations = 0;
                for (Instance instance : instanceSet)
                    sumOfDeviations = Math.pow(instance.value(attribute) - mu, 2);
                double sigma = Math.sqrt((1.0 / (instanceCount - 1)) * sumOfDeviations);
                sigmas.put(attribute, sigma);
            }

            classStatistics.put(mapEntry.getKey(), new ClassStats(mues, sigmas, probability));
        }

    }

    String classify(Instance instance) {
        double maxProbability = 0;
        String maxClass = "";

        for (Map.Entry<String, ClassStats> classStats : classStatistics.entrySet()) {
            ClassStats stats = classStats.getValue();

            double proizv = 1;
            for (Attribute attribute : attributes) {
                double sigma = stats.sigmas.get(attribute);
                double sigmaSq = Math.pow(sigma, 2);
                double mu = stats.mues.get(attribute);

                proizv *= 1.0 / Math.sqrt(2 * Math.PI * sigmaSq) *
                        Math.pow(Math.exp(1),
                                -Math.pow(instance.value(attribute) - mu, 2) / (2 * sigmaSq));
            }

            double probability = proizv * stats.probability;
            if (probability > maxProbability) {
                maxProbability = probability;
                maxClass = classStats.getKey();
            }
        }

        return maxClass;
    }

    class ClassStats {
        final Map<Attribute, Double> mues;

        final Map<Attribute, Double> sigmas;

        final double probability;

        ClassStats(Map<Attribute, Double> mues, Map<Attribute, Double> sigmas, double probability) {
            this.mues = mues;
            this.sigmas = sigmas;
            this.probability = probability;
        }
    }

}
