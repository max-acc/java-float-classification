package classification;

public class ProcessData {
    // --- Function for calculating the number of test data and flooring it, if it is not natural
    protected static int processDataValidation (int columnCount, float percentage) {
        return (int) Math.floor(columnCount * percentage);
    }

    // --- Function for returning a specific part of the data set from a lower to an upper boundary --------------------
    // --- Function for extracting the predictors
    protected static float[][] processDataPredictors (float [][] data, int lowerBoundary, int upperBoundary, int length) {
        // Creating an array for saving the data from the upper to the lower boundary
        float [][] dataPredictors = new float[upperBoundary - lowerBoundary][length];
        // Writing the data from the lower to the upper boundary to the new array
        for (int i = lowerBoundary; i < upperBoundary; i++) {
            dataPredictors[i - lowerBoundary]   = data[i];
        }
        return dataPredictors;
    }

    // --- Function for extracting the results
    protected static String[] processDataResults (String [] data, int lowerBoundary, int upperBoundary) {
        // Creating an array for saving the data from the upper to the lower boundary
        String [] dataResults = new String[upperBoundary - lowerBoundary];
        // Writing the data from the lower to the upper boundary to the new array
        for (int i = lowerBoundary; i < upperBoundary; i++) {
            dataResults[i - lowerBoundary]   = data[i];
        }
        return dataResults;
    }
}
