import weka.core.Attribute;
import weka.core.Instances;
import xrc.ai.ml.algorithms.KNNClassifier;
import xrc.ai.ml.verifier.NFoldCrossValidator;
import xrc.ai.ml.weka.WekaAttributeAdapter;
import xrc.ai.ml.weka.WekaInstanceSetAdapter;

public class Main {

    public static void main(String[] args) throws Exception {

        DataLoader dataLoader = new DataLoader();
        Instances dataSet = dataLoader.loadDataSet();
        Attribute classAttribute = dataSet.attribute(dataSet.numAttributes() - 1);

        KNNClassifier classifier = new KNNClassifier(3);
        NFoldCrossValidator validator = new NFoldCrossValidator(10,
                new WekaInstanceSetAdapter(dataSet),
                new WekaAttributeAdapter(classAttribute));

        double score = validator.evaluateScore(classifier);
        double[] lastEvaluations = validator.getLastEvaluations();
        for (int i = 0; i < lastEvaluations.length; i++) {
            System.out.printf("Evaluation %d score: %.2f%%\n", i + 1, lastEvaluations[i] * 100);
        }
        System.out.printf("Total score: %.2f%%\n", score * 100);

    }

}
