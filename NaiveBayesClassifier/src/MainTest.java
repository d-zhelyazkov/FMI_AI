import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import xrc.ai.ml.Classifier;
import xrc.ai.ml.algorithms.GaussianNaiveBayesClassifier;
import xrc.ai.ml.instance.AttributeValue;
import xrc.ai.ml.weka.WekaAttributeAdapter;
import xrc.ai.ml.weka.WekaAttributeValue;
import xrc.ai.ml.weka.WekaInstanceAdapter;
import xrc.ai.ml.weka.WekaInstanceSetAdapter;

import java.util.Random;

class MainTest {

    private Classifier classifier;

    @BeforeEach
    void beforeEach() {
        classifier = new GaussianNaiveBayesClassifier();
    }

    @RepeatedTest(value = 200)
    void test() throws Exception {
        DataLoader dataLoader = new DataLoader();
        Instances dataSet = dataLoader.loadDataSet();
        System.out.println("Data set size - " + dataSet.size());

        Random random = new Random();
        Instance testInstance = dataSet.get(random.nextInt(dataSet.numInstances()));
        dataSet.remove(testInstance);
        System.out.println("Test instance: " + testInstance);

        Attribute classAttribute = dataSet.attribute(dataSet.numAttributes() - 1);

        classifier.train(
                new WekaInstanceSetAdapter(dataSet),
                new WekaAttributeAdapter(classAttribute));

        AttributeValue classValue = classifier.classify(
                new WekaInstanceAdapter(testInstance));

        System.out.println("Classified as: " + classValue);

        Assertions.assertEquals(
                new WekaAttributeValue(testInstance, classAttribute),
                classValue);
    }

}