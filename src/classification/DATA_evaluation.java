package classification;

public class DATA_evaluation {
    private String[][][] predictedTestData;
    private int[][] sortedProbability;

    private int columnCount;
    private int numberOfClasses;
    private String [] testDataResults;
    private int[][] confustionMatrix;

    private boolean createConfusionMatrix = false;

    protected DATA_evaluation(String[] testDataResults, int columnCount, String[][][] predictedTestData, int[][] sortedProbability, int numberOfClasses) {
        this.testDataResults    = testDataResults;
        this.columnCount        = columnCount;
        this.predictedTestData  = predictedTestData;
        this.sortedProbability  = sortedProbability;
        this.numberOfClasses    = numberOfClasses;
        this.confustionMatrix   = new int[this.numberOfClasses][this.numberOfClasses];
        this.createConfusionMatrix = false;
    }

    private void confusionMatrix() {

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

    protected int[][] getConfusionMatrixSimple() {
        if (!this.createConfusionMatrix) {
            confusionMatrix();
        }

        int[][] confusionMatrixSimple = new int[this.numberOfClasses][2];
        for (int i = 0; i < this.numberOfClasses; i++) {
            for (int j = 0; j < 2; j++) {
                confusionMatrixSimple[i][j] = 0;
            }
        }

        for (int i = 0; i < this.columnCount; i++) {
            if (this.testDataResults[i].equals(this.predictedTestData[i][this.sortedProbability[i][0]][0])) {
                confusionMatrixSimple[this.sortedProbability[i][0]][0]++;
            }
            else {
                confusionMatrixSimple[this.sortedProbability[i][0]][1]++;
            }
        }



        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(confusionMatrixSimple[i][j] + " ");
            }
            System.out.println();
        }

        return confusionMatrixSimple;
    }
    protected float[][] getConfusionMatrixNormalized() {
        if (!this.createConfusionMatrix) {
            confusionMatrix();
        }
        float[][] confusionMatrixNormalized = new float[this.numberOfClasses][this.numberOfClasses];

        for (int i = 0; i < this.numberOfClasses; i++) {
            int tempCount = 0;
            for (int j = 0; j < this.numberOfClasses; j++) {
                tempCount += this.confustionMatrix[i][j];
            }

            if (tempCount == 0) {
                continue;
            }
            for (int j = 0; j < this.numberOfClasses; j++) {
                //System.out.println(this.confustionMatrix[i][j]);
                //System.out.println(tempCount);
                confusionMatrixNormalized[i][j] = (float)this.confustionMatrix[i][j] / (float)tempCount;
            }

        }

        for (int i = 0; i < this.numberOfClasses; i++) {
            for (int j = 0; j < this.numberOfClasses; j++) {
                System.out.print(confusionMatrixNormalized[i][j] + " ");
            }
            System.out.println();
        }


        return confusionMatrixNormalized;
    }
}