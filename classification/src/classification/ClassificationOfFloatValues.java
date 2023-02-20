package classification;

import java.lang.Math;

public class ClassificationOfFloatValues {
    // --- Object variables --------------------------------------------------------------------------------------------
    // Declaring and partly initialising necessary variables for the class
    private boolean index = false;   // Dataset has an index
    private  boolean header = false; // Dataset has a header
    private String datasetName;
    private int rowCount;
    private int columnCount;
    private float[][] predictorData;
    private String[] resultData;
    private float [] validation = {0.7f, 0.3f};  // Standard ratio for training and testing data
    private float [][] trainingDataPredictors;
    private String [] trainingDataResults;
    private float [][] testDataPredictors;
    private String [] testDataResults;
    private float density = 1.0f;
    private int numberOfTrainingData;



    // --- Validation variables
    private boolean dataProcessingBool = false; // Data has been processed (from CSV to array)
    private boolean dataSubdivisionBool = false; // Data has been divided into training and test data
    private String MLAlgorithm;     // Variable for saving which machine learning algorithm has been used


    // Function to add the members of the class
    public float[][] output() { return this.predictorData; }


    // --- Required functions ------------------------------------------------------------------------------------------
    // The required functions could be called instantly by the constructor class, but the separation gives
    // the user more control over the machine learning models, especially concerning an index, a header or
    // the training and testing data ratio.

    // --- Creating a constructor for different machine learning algorithms
    public ClassificationOfFloatValues(String dataset) throws Exception {
        this.datasetName = dataset;
    }

    // --- Function for creating
    public void dataProcessing() throws Exception {
        this.rowCount = CSVread.calcRowCount(this.datasetName, this.index);
        this.columnCount = CSVread.calcColumnCount(this.datasetName, this.index);
        //System.out.println(this.rowCount);
        //System.out.println(this.columnCount);
        this.predictorData = CSVread.transformPredictorData(this.datasetName, this.index, this.header, this.columnCount, this.rowCount);
        this.resultData = CSVread.transformResultData(this.datasetName, this.index, this.header, this.columnCount, this.rowCount);
        this.dataProcessingBool = true;
    }

    // --- Function for dividing the data into training and testing data
    public void dataSubdivision () {
        // Variable contains the number of columns which should be used for the training data
        this.numberOfTrainingData = ProcessData.processDataValidation(this.columnCount, this.validation[0]);

        // --- Dividing the data into training and testing data
        // Data for training the model
        this.trainingDataPredictors   = ProcessData.processDataPredictors(this.predictorData, 0, numberOfTrainingData, this.rowCount - returnIndex());
        this.trainingDataResults       = ProcessData.processDataResults(this.resultData, 0, numberOfTrainingData);

        // Data for testing the model
        this.testDataPredictors   = ProcessData.processDataPredictors(this.predictorData, numberOfTrainingData, this.columnCount, this.rowCount - returnIndex());
        this.testDataResults       = ProcessData.processDataResults(this.resultData, numberOfTrainingData, this.columnCount);

        this.dataSubdivisionBool = true;
    }


    // --- Functions for additional user control -----------------------------------------------------------------------
    // --- Function for changing the ratio between training and testing data
    public void setIndex (boolean index) { this.index = index; }
    public void setHeader (boolean header) { this.header = header; }
    public void setDensity (float density) {this.density = density;}
    public void dataValidation (float trainingData) {
        this.validation[0] = trainingData;
        this.validation[1] = 1- trainingData;
    }


    // --- Functions for evaluating the machine learning results -------------------------------------------------------
    public void confusionMatrix() {
        if (this.MLAlgorithm == "DistanceClassification") {
            System.out.println("nice confusion");
        }
        else {
            System.out.println("There is no algorithm");
        }
    }


    // --- Functions for private calculations --------------------------------------------------------------------------
    // --- Function for returning usable index data
    private int returnIndex() {
        // If there is an index it returns -1 because the usable data of the processed data has one element less
        if (this.index == true) { return -1; } else { return 0; }
    }

    //
    private boolean checkRequiredProcesses () {
        if (this.dataProcessingBool == true &&
            this.dataSubdivisionBool == true) {
            return true;
        }
        if (this.dataProcessingBool == false) {
            System.out.println("Error 310 | The data has not been divided into training and testing data!");
        }
        if (this.dataSubdivisionBool == false) {
            System.out.println("Error 311 | The data has not been divided into training and testing data!");
        }
        return false;
    }


    // -----------------------------------------------------------------------------------------------------------------
    // --- Machine Learning algorithms ---------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // The machine learning algorithms are callable by the user.
    // Depending on the chosen algorithm a new object is being created.
    // The real machine learning algorithm cannot be called by the user directly.

    // --- Distance Classification
    // Creating an object from the DistanceClassification class
    public void distanceClassification (){
        // The algorithm can only work if the has been processed previously
        if (checkRequiredProcesses()) {
            // Training the distance classification model
            DistanceClassification classificationObject = new DistanceClassification(this.trainingDataPredictors, this.trainingDataResults, this.rowCount, this.numberOfTrainingData, this.density);

            // Testing the distance classification model
            classificationObject.setTestData(this.testDataPredictors, this.testDataResults, this.rowCount, this.columnCount - this.numberOfTrainingData);
            classificationObject.testModel();
        }

    }
}
