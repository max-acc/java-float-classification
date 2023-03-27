package classification;

public class DATA_evaluation {
    private String[][][] predictedTestData;
    private int[][] sortedProbability;

    private int columnCount;
    private int numberOfClasses;
    private String [] testDataResults;
    private int[][] confustionMatrix;

    private boolean createConfusionMatrix = false;

    // --- Creating a constructor for data evaluation
    protected DATA_evaluation(String[] testDataResults, int columnCount, String[][][] predictedTestData, int[][] sortedProbability, int numberOfClasses) {
        this.testDataResults    = testDataResults;
        this.columnCount        = columnCount;
        this.predictedTestData  = predictedTestData;
        this.sortedProbability  = sortedProbability;
        this.numberOfClasses    = numberOfClasses;
        this.confustionMatrix   = new int[this.numberOfClasses][this.numberOfClasses];
        this.createConfusionMatrix = false;
    }

    // --- Function for creating a basic confusion matrix for further calculations -------------------------------------
    private void confusionMatrix() {
        // Resetting the confusion matrix
        for (int i = 0; i < this.numberOfClasses; i++) {
            for (int j = 0; j < this.numberOfClasses; j++) {
                this.confustionMatrix[i][j] = 0;
            }
        }

        // Going through every column of the data (only test data)
        for (int i = 0; i < this.columnCount; i++) {
            // Compare the predicted result with the actual result
            if (this.testDataResults[i].equals(this.predictedTestData[i][this.sortedProbability[i][0]][0])) {
                // If true: The data has been predicted truly
                this.confustionMatrix[this.sortedProbability[i][0]][this.sortedProbability[i][0]]++;
            }
            // If the predicted data does not match the actual result, it has been predicted falsely
            else {
                // Writing data as a false prediction to the confusion matrix
                for (int j = 0; j < this.numberOfClasses; j++) {
                    if (this.testDataResults[i].equals(this.predictedTestData[i][this.sortedProbability[i][j]][0])) {
                        this.confustionMatrix[this.sortedProbability[i][0]][this.sortedProbability[i][j]]++;
                    }
                }
            }
        }
    }

    // --- Printing a basic confusion matrix
    protected void getConfusionMatrix() {
        // If the confusion matrix has not been created yet, it will be
        if (!this.createConfusionMatrix) {
            confusionMatrix();
        }

        // Printing every element of the confusion matrix
        for (int i = 0; i < this.numberOfClasses; i++) {
            for (int j = 0; j < this.numberOfClasses; j++) {
                System.out.print(confustionMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // --- Printing a simple confusion matrix
    protected void getConfusionMatrixSimple() {
        // If the confusion matrix has not been created yet, it will be
        if (!this.createConfusionMatrix) {
            confusionMatrix();
        }

        // Creating an array for calculating a simple confusion matrix
        int[][] confusionMatrixSimple = new int[this.numberOfClasses][2];
        for (int i = 0; i < this.numberOfClasses; i++) {
            for (int j = 0; j < 2; j++) {
                confusionMatrixSimple[i][j] = 0;
            }
        }

        // Going through every column of the data (only test data)
        for (int i = 0; i < this.columnCount; i++) {
            // If the data has been predicted correctly it is TT
            if (this.testDataResults[i].equals(this.predictedTestData[i][this.sortedProbability[i][0]][0])) {
                confusionMatrixSimple[this.sortedProbability[i][0]][0]++;
            }
            // If the data has been predicted correctly it is TN
            else {
                confusionMatrixSimple[this.sortedProbability[i][0]][1]++;
            }
        }

        // Printing every element of the simple confusion matrix
        for (int i = 0; i < this.numberOfClasses; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(confusionMatrixSimple[i][j] + " ");
            }
            System.out.println();
        }

        //return confusionMatrixSimple;
    }

    // --- Printing a normalized confusion matrix
    protected void getConfusionMatrixNormalized() {
        // If the confusion matrix has not been created yet, it will be
        if (!this.createConfusionMatrix) {
            confusionMatrix();
        }

        // Creating an array for calculating a normalized confusion matrix
        float[][] confusionMatrixNormalized = new float[this.numberOfClasses][this.numberOfClasses];

        // Going through every column of the data (only test data)
        for (int i = 0; i < this.numberOfClasses; i++) {
            // Adding all predicted values per class
            int tempCount = 0;
            for (int j = 0; j < this.numberOfClasses; j++) {
                tempCount += this.confustionMatrix[i][j];
            }

            if (tempCount == 0) {
                continue;
            }

            // Dividing the predicted data per class
            for (int j = 0; j < this.numberOfClasses; j++) {
                confusionMatrixNormalized[i][j] = (float)this.confustionMatrix[i][j] / (float)tempCount;
            }
        }

        // Printing every element of the normalized confusion matrix
        for (int i = 0; i < this.numberOfClasses; i++) {
            for (int j = 0; j < this.numberOfClasses; j++) {
                System.out.print(confusionMatrixNormalized[i][j] + " ");
            }
            System.out.println();
        }

        //return confusionMatrixNormalized;
    }
}