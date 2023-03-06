
import classification.ClassificationOfFloatValues;

public class Main {


    public static void main (String[] args) throws Exception {
        // Creating of instance of second class present
        // in the same project
        boolean index = true;
        boolean header = true;
        String dataset = "Iris_unordered_2.csv";

        ClassificationOfFloatValues ob = new ClassificationOfFloatValues(dataset);
        ob.setIndex(true);
        ob.setHeader(true);

        // traingingData = percentage of training data <1
        ob.dataValidation(0.7f);
        ob.dataProcessing();
        ob.dataSubdivision();
        //System.out.println(ob.feedback()[0][2]);
        ob.distanceClassification();

        ob.evaluateResults();
    }

}
