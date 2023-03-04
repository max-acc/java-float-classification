package classification;

public class DATA_evaluation {
    private String[][][] predictedTestData;
    private int[][] sortedProbability;

    private int columnCount;
    private int numberOfClasses;
    private String [] testDataResults;
    private int[][] confustionMatrix = new int[3][2];

    protected DATA_evaluation(String[] testDataResults, int columnCount, String[][][] predictedTestData, int[][] sortedProbability) {
        this.testDataResults    = testDataResults;
        this.columnCount        = columnCount;
        this.predictedTestData  = predictedTestData;
        this.sortedProbability  = sortedProbability;
    }

    protected void confusionMatrix() {
        System.out.println(this.testDataResults[0]);
        System.out.println(this.columnCount);
        System.out.println(this.predictedTestData[0][0][0]);
        System.out.println(this.predictedTestData[0][0][1]);
        System.out.println(this.predictedTestData[0][1][0]);
        System.out.println(this.predictedTestData[0][1][1]);
        System.out.println(this.predictedTestData[0][2][0]);
        System.out.println(this.predictedTestData[0][2][1]);
        System.out.println(this.sortedProbability[0][0]);
        System.out.println(this.sortedProbability[0][1]);
        System.out.println(this.sortedProbability[0][2]);

        // Resetting the confusion matrix
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                this.confustionMatrix[i][j] = 0;
            }
        }

        for (int i = 0; i < this.columnCount; i++) {
            if (this.testDataResults[i].equals(this.predictedTestData[i][this.sortedProbability[i][0]][0])) {
                this.confustionMatrix[this.sortedProbability[i][0]][0]++;
            }
            else {
                this.confustionMatrix[this.sortedProbability[i][0]][1]++;
            }
        }



        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(this.confustionMatrix[i][j] + " ");
            }
            System.out.println();
        }


    }
}