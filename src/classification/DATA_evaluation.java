package classification;

public class DATA_evaluation {
    private String[][][] predictedTestData;
    private int[][] sortedProbability;

    private int columnCount;
    private int numberOfClasses;
    private String [] testDataResults;
    private int[][] confustionMatrix = new int[3][3];

    protected DATA_evaluation(String[] testDataResults, int columnCount, String[][][] predictedTestData, int[][] sortedProbability, int numberOfClasses) {
        this.testDataResults    = testDataResults;
        this.columnCount        = columnCount;
        this.predictedTestData  = predictedTestData;
        this.sortedProbability  = sortedProbability;
        this.numberOfClasses    = numberOfClasses;
        this.confustionMatrix   = new int[this.numberOfClasses][this.numberOfClasses];
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
        for (int i = 0; i < this.numberOfClasses; i++) {
            for (int j = 0; j < this.numberOfClasses; j++) {
                this.confustionMatrix[i][j] = 0;
            }
        }

        for (int i = 0; i < this.columnCount; i++) {
            //System.out.println(this.testDataResults[i] + " " + this.predictedTestData[i][this.sortedProbability[i][0]][0]);
            if (this.testDataResults[i].equals(this.predictedTestData[i][this.sortedProbability[i][0]][0])) {
                this.confustionMatrix[this.sortedProbability[i][0]][this.sortedProbability[i][0]]++;
            }
            else {

                for (int j = 0; j < this.numberOfClasses; j++) {
                    if (this.testDataResults[i].equals(this.predictedTestData[i][this.sortedProbability[i][j]][0])) {
                        this.confustionMatrix[this.sortedProbability[i][0]][this.sortedProbability[i][j]]++;
                    }
                }


                //this.confustionMatrix[this.sortedProbability[i][0]][1]++;
            }

        }



        for (int i = 0; i < this.numberOfClasses; i++) {
            for (int j = 0; j < this.numberOfClasses; j++) {
                System.out.print(this.confustionMatrix[i][j] + " ");
            }
            System.out.println();
        }


    }
}