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

    private String[][][] predictedTestData;
    private int[][] sortedProbability;


    // --- Creating an object for distance classification --------------------------------------------------------------
    protected DistanceClassification(float [][] trainingDataPredictors, String [] trainingDataResults, int rowCount, int columnCount, float density) {
        //Setting specific attributes
        this.trainingDataPredictors = trainingDataPredictors;
        this.trainingDataResults = trainingDataResults;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.density = density;

        // Getting the different classification classes
        getClassificationClasses();

        // Sorting the classification data
        sortClassificationData();

        // Calculating the mean per feature (to calculate the distance)
        calcFeatureMean();
    }

    // --- Functions for returning classification data for internal use in other classes -------------------------------
    // --- Function for returning the number of different classes
    protected int getNumberOfClasses () {
        return this.numberOfClasses;
    }

    // --- Function for returning the sorted classification data (after classification)
    protected float[][][] getSortedClassificationData() {
        return this.sortedClassificationData;
    }

    // --- Function for returning the predicted test data (probability per class)
    protected String[][][] getPredictedTestData() {
        return this.predictedTestData;
    }

    // --- Function for returning the sorted probability (ranking of the highest probability)
    protected int[][] getSortedProbability() {
        return this.sortedProbability;
    }

    // --- Function for returning the mean value per feature
    protected float[][] getFeatureMean() {
        return this.featureMean;
    }

    // --- Required Functions for classifying the data -----------------------------------------------------------------
    // All Functions have to been called in the ClassificationOfFloatValues.java file

    // --- Function for setting the test data (with an upper and lower boundary -> already set in the classification file)
    protected void setTestData(float[][] testDataPredictors, String[] testDataResults, int rowCount, int columnCount) {
        this.testDataPredictors = testDataPredictors;
        this.testDataResults = testDataResults;
        this.testDataRowCount = rowCount;
        this.testDataColumnCount = columnCount;
    }

    // --- Calling a Function for testing the model
    protected void testModel() {
        testClassificationModel();
    }

    // --- Private Functions for intern calculations of the actual classification --------------------------------------
    // --- Function for getting the different classification classes and their number
    private void getClassificationClasses() {
        int numberOfClasses = 0;
        List<String> classes = new ArrayList<String>();
        List<String> tempClasses = Arrays.asList(this.trainingDataResults);

        // Going through every column of the dataset (test data) and reading the result data (class)
        for (int i = 0; i < this.columnCount; i++) {
            // If the class is not already in the saved in the class array list, it will be added
            if (!classes.contains(tempClasses.get(i))) {
                classes.add(tempClasses.get(i));
                numberOfClasses++;
            }
        }

        this.numberOfClasses = numberOfClasses;
        this.classes  = new String[this.numberOfClasses];

        // Converting the array list to an array
        for (int i = 0; i < this.numberOfClasses; i++) {
            this.classes[i] = classes.get(i);
        }
    }

    // --- Function for sorting the test data which is going to be classified
    private void sortClassificationData() {
        ArrayList<ArrayList<ArrayList<Float>>> tempSortedClassificationData = new ArrayList<>();
        int [] tempNumberDataPerClass = new int[this.rowCount -1];
        // Resetting the array
        for (int i = 0; i < rowCount -1; i++) {
            tempNumberDataPerClass[i] = 0;
        }

        // Creating an array list for saving the sorted classification data
        // This has to be done because array list sometimes cause unintended behaviour
        // Going through every class
        for (int i = 0; i < this.numberOfClasses; i++) {
            // Adding elements in the second dimension (with a maximum value of the overall count of columns from the data set)
            tempSortedClassificationData.add(new ArrayList<ArrayList<Float>>(this.columnCount));
            // Going through every column of the prior initialised second dimension
            for (int j = 0; j < this.columnCount; j++) {
                // Adding elements in the third dimension (with the number of the features)
                tempSortedClassificationData.get(i).add(new ArrayList<Float>(this.rowCount -1));
            }
        }

        // Going through every column of the dataset
        for (int i = 0; i < this.columnCount; i++) {
            // Going through every class to try to match the dataset to a class of the sorted classification data
            for (int j = 0; j < this.numberOfClasses; j++) {
                // If the result of the dataset matches a prior defined class, it will be written to it
                if (this.trainingDataResults[i].equals(this.classes[j])) {
                    // Transferring all values to the new array
                    for (int k = 0; k < this.rowCount -1; k++) {
                        tempSortedClassificationData.get(j).get(tempNumberDataPerClass[j]).add(k, this.trainingDataPredictors[i][k]);
                    }
                    // The number of the data per class is being incremented
                    tempNumberDataPerClass[j]++;
                }
            }
        }

        // Getting the maximum number of elements which belong to one class
        this.elementsPerClass = tempNumberDataPerClass;
        int maxNumOfElementsPerClass = 0;
        for (int i = 0; i < this.elementsPerClass.length; i++) {
            if (this.elementsPerClass[i] > maxNumOfElementsPerClass) {
                maxNumOfElementsPerClass = this.elementsPerClass[i];
            }
        }

        // Initialising an array for saving the sorted classification data ([class][test data][feature]: float[file data from feature])
        this.sortedClassificationData = new float[this.numberOfClasses][maxNumOfElementsPerClass][this.rowCount -1];

        // Reducing the size of the Array List and writing it to an array
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

    // --- Function for calculating the mean for every feature (training the model)
    private void calcFeatureMean() {
        // Array Structure: [class][mean per feature]
        this.featureMean = new float[this.numberOfClasses][this.rowCount -1];
        // Iterating through every class
        for (int i = 0; i < this.numberOfClasses; i++) {
            // Iterating through every feature
            for (int j = 0; j < this.rowCount -1; j++) {
                float tempCalcVar = 0;
                // Adding all elements per class and feature to a temp var
                for (int k = 0; k < this.elementsPerClass[i]; k++) {
                    tempCalcVar += this.sortedClassificationData[i][k][j];
                }
                // Calculating the mean for every feature
                tempCalcVar /= this.elementsPerClass[i];
                this.featureMean[i][j] = tempCalcVar;
            }
        }
    }

    // --- Function for testing the classification model
    private void testClassificationModel() {
        this.predictedTestData = new String[this.testDataColumnCount][this.numberOfClasses][2];
        this.sortedProbability = new int[this.testDataColumnCount][this.numberOfClasses];

        // Check the distance for every column of the test dataset
        for (int i = 0; i < this.testDataColumnCount; i++) {
            // Create a temporary function for calculating a temporary delta
            float[][] tempDelta = new float[this.numberOfClasses][this.rowCount];

            // Calculating the temporary delta of each row per class
            for (int j = 0; j < this.numberOfClasses; j++) {
                // Calculating the current delta value per class
                for (int k = 0; k < this.rowCount -1; k++) {
                    tempDelta[j][k] = this.featureMean[j][k] - this.testDataPredictors[i][k];
                }

                // Getting the prediction per class
                this.predictedTestData[i][j][0] = this.classes[j];
                // Setting temp variables
                float tempCalcDistance = 0;

                // Calculating the distance between data points (pythagorean theorem)
                for (int k = 0; k < this.rowCount -1; k++) {
                    tempCalcDistance += (float) Math.pow(tempDelta[j][k], 2);
                }
                tempDelta[j][this.rowCount -1] = (float) Math.sqrt(tempCalcDistance);

                // Setting the calculated temp delta to the likeliness of a result
                this.predictedTestData[i][j][1] = Float.toString(tempDelta[j][this.rowCount -1]);
            }

            float min = Float.valueOf(this.predictedTestData[i][0][1]);

            int tempIndex = 0;
            this.sortedProbability[i][0] = tempIndex;

            // Calculating the index of the class with the most likely outcome (best prediction)
            for (int j = 0; j < this.numberOfClasses; j++) {
                if (min > Float.valueOf(this.predictedTestData[i][j][1])) {
                    min = Float.valueOf(this.predictedTestData[i][j][1]);
                    tempIndex = j;
                }
            }

            // Blackbox for sorting the likeliness for every class
            this.sortedProbability[i][0] = tempIndex;
            for (int j = 1; j < this.numberOfClasses; j++) {
                tempIndex = 0;
                this.sortedProbability[i][j] = tempIndex;
                min = Float.valueOf(this.predictedTestData[i][this.sortedProbability[i][j-1]][1]);
                for (int k = 0; k < this.numberOfClasses; k++) {
                    if ((min > Float.valueOf(this.predictedTestData[i][k][1]) &&
                            Float.valueOf(this.predictedTestData[i][k][1]) > Float.valueOf(this.predictedTestData[i][this.sortedProbability[i][j-1]][1])) ||
                            (Float.valueOf(this.predictedTestData[i][k][1]) > Float.valueOf(this.predictedTestData[i][this.sortedProbability[i][j-1]][1]) &&
                            min == Float.valueOf(this.predictedTestData[i][this.sortedProbability[i][j-1]][1]))) {
                        if (min > Float.valueOf(this.predictedTestData[i][k][1]) &&
                                Float.valueOf(this.predictedTestData[i][k][1]) > Float.valueOf(this.predictedTestData[i][this.sortedProbability[i][j-1]][1])) {
                        }
                        if (Float.valueOf(this.predictedTestData[i][k][1]) > Float.valueOf(this.predictedTestData[i][this.sortedProbability[i][j-1]][1]) &&
                                k != this.sortedProbability[i][j-1]) {
                        }
                        min = Float.valueOf(this.predictedTestData[i][k][1]);
                        tempIndex = k;
                    }
                }
                this.sortedProbability[i][j] = tempIndex;
            }
        }
    }
}
