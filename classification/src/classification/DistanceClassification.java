package classification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistanceClassification {
    private float [][] trainingDataPredictors;
    private String [] trainingDataResults;
    private int rowCount;
    private int columnCount;
    private float density;
    private int numberOfClasses = 0;
    private String [] classes;
    private int [] elementsPerClass;
    private float[][][] sortedClassificationData;
    private float[][] featureMean;

    private float[][] testDataPredictors;
    private String[] testDataResults;
    private int testDataRowCount;
    private int testDataColumnCount;

    private String[][] predictedTestData;


    protected DistanceClassification(float [][] trainingDataPredictors, String [] trainingDataResults, int rowCount, int columnCount, float density) {
        this.trainingDataPredictors = trainingDataPredictors;
        this.trainingDataResults = trainingDataResults;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.density = density;

        getClassificationClasses();

        sortClassificationData();

        calcFeatureMean();
    }

    protected float[][][] getSortedClassificationData() {
        return this.sortedClassificationData;
    }

    protected float[][] getFeatureMean() {
        return this.featureMean;
    }

    protected void setTestData(float[][] testDataPredictors, String[] testDataResults, int rowCount, int columnCount) {
        this.testDataPredictors = testDataPredictors;
        this.testDataResults = testDataResults;
        this.testDataRowCount = rowCount;
        this.testDataColumnCount = columnCount;
    }

    protected void testModel() {
        testClassifcationModel();
    }

    private void getClassificationClasses() {
        int numberOfClasses = 0;
        List<String> classes = new ArrayList<String>();
        List<String> tempClasses = Arrays.asList(this.trainingDataResults);

        for (int i = 0; i < this.columnCount; i++) {
            if (!classes.contains(tempClasses.get(i))) {
                classes.add(tempClasses.get(i));
                numberOfClasses++;
                //System.out.println(i);
            }
        }

        this.numberOfClasses = numberOfClasses;
        this.classes  = new String[this.numberOfClasses];

        for (int i = 0; i < this.numberOfClasses; i++) {
            this.classes[i] = classes.get(i);
            //System.out.println(this.classes[i]);
        }
    }

    private void sortClassificationData() {
        ArrayList<ArrayList<ArrayList<Float>>> tempSortedClassificationData = new ArrayList<>();
        int [] tempNumberDataPerClass = new int[this.rowCount -1];
        for (int i = 0; i < rowCount -1; i++) {
            tempNumberDataPerClass[i] = 0;
        }

        for (int i = 0; i < this.numberOfClasses; i++) {
            tempSortedClassificationData.add(new ArrayList<ArrayList<Float>>(this.columnCount));
            for (int j = 0; j < this.columnCount; j++) {
                tempSortedClassificationData.get(i).add(new ArrayList<Float>(this.rowCount -1));
            }
        }

        for (int i = 0; i < this.columnCount; i++) {
            for (int j = 0; j < this.numberOfClasses; j++) {
                if (this.trainingDataResults[i].equals(this.classes[j])) {
                    for (int k = 0; k < this.rowCount -1; k++) {
                        tempSortedClassificationData.get(j).get(tempNumberDataPerClass[j]).add(k, this.trainingDataPredictors[i][k]);
                    }
                    tempNumberDataPerClass[j]++;
                }
            }
        }

        this.elementsPerClass = tempNumberDataPerClass;
        int maxNumOfElementsPerClass = 0;
        for (int i = 0; i < this.elementsPerClass.length; i++) {
            if (this.elementsPerClass[i] > maxNumOfElementsPerClass) {
                maxNumOfElementsPerClass = this.elementsPerClass[i];
            }
        }

        this.sortedClassificationData = new float[this.numberOfClasses][maxNumOfElementsPerClass][this.rowCount -1];

        // Reducing the size of the Array List
        for (int i = 0; i < this.numberOfClasses; i++) {
            for (int j = 0; j < maxNumOfElementsPerClass; j++) {
                if (j >= this.elementsPerClass[i]) {
                    continue;
                }
                for (int k = 0; k < this.rowCount -1; k++) {
                    this.sortedClassificationData[i][j][k] = tempSortedClassificationData.get(i).get(j).get(k);
                }
            }
        }
    }

    private void calcFeatureMean() {
        this.featureMean = new float[this.numberOfClasses][this.rowCount -1];
        for (int i = 0; i < this.numberOfClasses; i++) {
            for (int j = 0; j < this.rowCount -1; j++) {
                float tempCalcVar = 0;
                for (int k = 0; k < this.elementsPerClass[i]; k++) {
                    tempCalcVar += this.sortedClassificationData[i][k][j];
                }
                tempCalcVar /= this.elementsPerClass[i];
                this.featureMean[i][j] = tempCalcVar;
            }
        }
    }

    private void testClassifcationModel() {
        this.predictedTestData = new String[testDataColumnCount][2];

        // Check the distance for every class
        for (int i = 0; i < this.testDataColumnCount; i++) {
            float[][] tempDelta = new float[this.numberOfClasses][this.rowCount];
            for (int j = 0; j < this.numberOfClasses; j++) {
                for (int k = 0; k < this.rowCount -1; k++) {
                    tempDelta[j][k] = this.featureMean[j][k] - this.testDataPredictors[i][k];
                }

                tempDelta[j][this.rowCount -1] = (float) Math.sqrt(
                        Math.pow(tempDelta[j][0], 2) +
                        Math.pow(tempDelta[j][1], 2) +
                        Math.pow(tempDelta[j][2], 2) +
                        Math.pow(tempDelta[j][3], 2));
                System.out.print(classes[j] + " ");
                System.out.println(tempDelta[j][this.rowCount -1]);
            }


            /*
            for (int j = 0; j < this.numberOfClasses; j++) {
                for (int k = 0; k < this.rowCount -1; k++) {
                    System.out.print(tempDelta[j][k]);
                    System.out.print(" ");
                }
                System.out.println();
            }
            */
            this.predictedTestData[i][0] = "best result";
            this.predictedTestData[i][1] = "probability";


            //this.predictedTestData[i] = "best result";
        }






    }
}
