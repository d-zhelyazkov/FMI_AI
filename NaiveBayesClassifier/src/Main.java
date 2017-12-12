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

    static void read() throws IOException {
        String filePath = "C:\\Program Files\\Weka-3-8\\data\\iris.arff";
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File(filePath));

        //structure = loader.getStructure();
        //structure.setClassIndex(structure.numAttributes() - 1);
        structure = loader.getDataSet();
        System.out.println(structure);

    }
}
