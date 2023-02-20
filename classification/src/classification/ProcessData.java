package classification;

public class ProcessData {
    protected static int processDataValidation (int columnCount, float percentage) {
        return (int) Math.floor(columnCount * percentage);
    }

    protected static float[][] processDataPredictors (float [][] data, int lowerBoundary, int upperBoundary, int length) {
        float [][] dataPredictors = new float[upperBoundary - lowerBoundary][length];
        for (int i = lowerBoundary; i < upperBoundary; i++) {
            dataPredictors[i - lowerBoundary]   = data[i];
        }
        return dataPredictors;
    }

    protected static String[] processDataResults (String [] data, int lowerBoundary, int upperBoundary) {
        String [] dataResults = new String[upperBoundary - lowerBoundary];
        for (int i = lowerBoundary; i < upperBoundary; i++) {
            dataResults[i - lowerBoundary]   = data[i];
        }
        return dataResults;
    }
}
