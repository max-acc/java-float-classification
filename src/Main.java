
import classification.ClassificationOfFloatValues;

public class Main {


    public static void main (String[] args) throws Exception {
        // Creating of instance of second class present
        // in the same project
        String dataset = "Iris_unordered_2.csv";
        dataset = "Dry_Bean_Dataset.csv";



        ClassificationOfFloatValues ob = new ClassificationOfFloatValues(dataset);
        ob.setIndex(true);
        ob.setHeader(true);

        // trainingData = percentage of training data <1
        ob.dataValidation(0.7f);
        ob.dataProcessing();
        ob.dataSubdivision();

        ob.distanceClassification();

        ob.setEvaluation("Confusion Matrix");
        ob.setEvaluation("Simple Confusion Matrix");
        ob.setEvaluation("Normalized Confusion Matrix");

        ob.evaluateResults();
    }
}
