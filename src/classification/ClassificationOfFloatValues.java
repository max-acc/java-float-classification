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

    // --- Classification result data variables
    private String[][][] predictedTestData;
    private int[][] sortedProbability;
    private int numberOfClasses;

    private boolean[] validationModel = {false, false, false};


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

    // --- Function for processing the data (reading and writing the data to an array)
    public void dataProcessing() throws Exception {
        // Get row and column count
        this.rowCount = CSVread.calcRowCount(this.datasetName, this.index);
        this.columnCount = CSVread.calcColumnCount(this.datasetName, this.index);
        // Get predictor and result data
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
    // --- Functions for setting the index and header data
    public void setIndex (boolean index) { this.index = index; }

    public void setHeader (boolean header) { this.header = header; }

    // --- Function for changing the data density to clear extreme values | Not working
    public void setDensity (float density) {this.density = density;}

    // --- Function for changing the ratio between training and testing data
    public void dataValidation (float trainingData) {
        this.validation[0] = trainingData;
        this.validation[1] = 1- trainingData;
    }
    // --- Function for setting the validation model
    public void setEvaluation(String evaluationName) {
        if (evaluationName.equals("Confusion Matrix")) {
            this.validationModel[0] = true;
        }else if (evaluationName.equals("Simple Confusion Matrix")) {
            this.validationModel[1] = true;
        }else if (evaluationName.equals("Normalized Confusion Matrix")) {
            this.validationModel[2] = true;
        }
    }


    // --- Functions for evaluating the machine learning results -------------------------------------------------------
    // --- Function for printing confusion matrices
    public void evaluateResults() {
        // Creating an object to calculate confusion matrices
        DATA_evaluation evaluationObject    = new DATA_evaluation(this.testDataResults,
                this.columnCount - this.numberOfTrainingData,
                this.predictedTestData,
                this.sortedProbability,
                this.numberOfClasses);

        // Printing a basic confusion matrix
        if (this.validationModel[0] == true) {
            System.out.println("\nConfusion Matrix");
            evaluationObject.getConfusionMatrix();
        }
        // Printing a simplified confusion matrix
        if (this.validationModel[1] == true) {
            System.out.println("\nSimple Confusion Matrix");
            evaluationObject.getConfusionMatrixSimple();
        }
        // Printing a normalized confusion matrix
        if (this.validationModel[2] == true) {
            System.out.println("\nNormalized Confusion Matrix");
            evaluationObject.getConfusionMatrixNormalized();
        }

    }


    // --- Functions for private calculations --------------------------------------------------------------------------
    // --- Function for returning usable index data
    private int returnIndex() {
        // If there is an index it returns -1 because the usable data of the processed data has one element less
        // The index does not belong to the important data
        if (this.index == true) { return -1; } else { return 0; }
    }

    // --- Function for checking if all required processes have been completed successful before starting the
    // classification algorithms
    private boolean checkRequiredProcesses () {
        // dataProcessingBool:  Process the CSV data (reading)
        // dataSubdivisionBool: Dividing the data into training and testing data
        if (this.dataProcessingBool == true &&
            this.dataSubdivisionBool == true) {
            return true;
        }else {
            if (this.dataProcessingBool == false) {
                System.out.println("Error 310 | The data has not been divided into training and testing data!");
            }
            if (this.dataSubdivisionBool == false) {
                System.out.println("Error 311 | The data has not been divided into training and testing data!");
            }
            return false;
        }
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

            // Return the number of found classes
            this.numberOfClasses = classificationObject.getNumberOfClasses();

            // Get the predicted text data
            this.predictedTestData  = classificationObject.getPredictedTestData();
            this.sortedProbability  = classificationObject.getSortedProbability();
        }

    }
}
