import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static Instances structure;

    static Instances newData;

    public static void main(String[] args) throws Exception {
        read();

        //applyFilter();

        Instance testInstance = structure.get(structure.numInstances() - 1);
        structure.remove(structure.numInstances() - 1);
        structure.setClassIndex(structure.numAttributes() - 1);
        System.out.println("Real class: " + testInstance.stringValue(testInstance.numValues() - 1));

        NaiveBayesClassifier classifier = new NaiveBayesClassifier(structure);
        String aClass = classifier.classify(testInstance);
        System.out.println(aClass);
    }

    private static String classify(Instance testInstance) {
        List<Evaluation> evaluations = new ArrayList<>(newData.numInstances());

        Enumeration<Instance> instanceEnumeration = newData.enumerateInstances();
        while (instanceEnumeration.hasMoreElements()) {
            Instance instance = instanceEnumeration.nextElement();
            double distance = distance(instance, testInstance);
            evaluations.add(new Evaluation(distance, instance));
        }

        Collections.sort(evaluations);
        Map<String, Class> classCounts = new HashMap<>();
        for (Evaluation evaluation : evaluations.subList(0, 3)) {
            String attribute = evaluation.instance.stringValue(testInstance.numAttributes() - 1);
            Class aClass = classCounts.get(attribute);
            if (aClass == null) {
                aClass = new Class(attribute, evaluation.distance);
                classCounts.put(attribute, aClass);
            } else {
                aClass.count++;
                if (aClass.bestDistance > evaluation.distance)
                    aClass.bestDistance = evaluation.distance;
            }

        }

        List<Class> classes = new ArrayList<>(classCounts.values());
        Collections.sort(classes);

        return classes.get(0).id;
    }

    private static double distance(Instance instance, Instance testInstance) {
        double distance = 0;
        for (int i = 0; i < instance.numAttributes() - 1; i++) {
            distance += Math.pow((instance.value(i) - testInstance.value(i)), 2);
        }

        return Math.sqrt(distance);
    }

    static void read() throws IOException {
        String filePath = "C:\\Program Files\\Weka-3-8\\data\\iris.arff";
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File(filePath));

        //structure = loader.getStructure();
        //structure.setClassIndex(structure.numAttributes() - 1);
        structure = loader.getDataSet();
        System.out.println(structure);

    }

    static void applyFilter() throws Exception {
        Normalize normalize = new Normalize();
        normalize.setInputFormat(structure);
        newData = Filter.useFilter(structure, normalize);
        System.out.println(newData);
    }

    static class Evaluation implements Comparable<Evaluation> {
        double distance;

        Instance instance;

        public Evaluation(double distance, Instance instance) {
            this.distance = distance;
            this.instance = instance;
        }

        @Override
        public int compareTo(Evaluation o) {
            return Double.compare(distance, o.distance);
        }
    }

    static class Class implements Comparable<Class> {
        String id;

        int count = 1;

        double bestDistance;

        public Class(String id, double initialDistance) {
            this.id = id;
            bestDistance = initialDistance;
        }

        @Override
        public int compareTo(Class o) {
            int compare = Integer.compare(o.count, count);
            if (compare != 0) {
                return compare;
            }

            return Double.compare(bestDistance, o.bestDistance);
        }
    }
}
