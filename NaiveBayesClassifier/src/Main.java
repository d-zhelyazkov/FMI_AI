import weka.core.Attribute;
import weka.core.Instances;
import xrc.ai.ml.Classifier;
import xrc.ai.ml.algorithms.GaussianNaiveBayesClassifier;
import xrc.ai.ml.verifier.ClassifierValidator;
import xrc.ai.ml.verifier.NFoldCrossValidator;
import xrc.ai.ml.weka.WekaAttributeAdapter;
import xrc.ai.ml.weka.WekaInstanceSetAdapter;

public class Main {

    public static void main(String[] args) throws Exception {

        DataLoader dataLoader = new DataLoader();
        Instances dataSet = dataLoader.loadDataSet();
        Attribute classAttribute = dataSet.attribute(dataSet.numAttributes() - 1);

        final int[] i = { 0 };
        NFoldCrossValidator validator = new NFoldCrossValidator(10,
                new WekaInstanceSetAdapter(dataSet),
                new WekaAttributeAdapter(classAttribute)) {

            @Override
            public double evaluateValidatorScore(ClassifierValidator validator, Classifier classifier) {
                double score = super.evaluateValidatorScore(validator, classifier);
                System.out.printf("Evaluation %d score: %.2f%%\n", ++i[0], score * 100);
                return score;
            }
        };

        Classifier classifier = new GaussianNaiveBayesClassifier();
        double score = validator.evaluateScore(classifier);
        System.out.printf("Total score: %.2f%%\n", score * 100);

    }

}
