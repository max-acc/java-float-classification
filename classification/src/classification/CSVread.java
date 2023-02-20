package classification;

import java.io.File;
import java.util.Scanner;

public class CSVread {

    // ---  Function for counting the number of rows in a CSV file
    static int calcRowCount (String dataset, boolean index) throws Exception {
        int rowCount = 0;
        String line;
        // Scanning the CSV file by rows
        try (Scanner scanner = new Scanner(new File(dataset));){
            line = scanner.nextLine();
        }
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                rowCount++;
                rowScanner.next();
            }
        }
        // If the file has an index, the count is being decremented
        if (index == true) { return rowCount -1; }
        else { return rowCount;}
    }

    // --- Function for counting the number of columns in a CSV file
    static int calcColumnCount (String dataset, boolean header) throws Exception {
        int columnCount = 0;
        // Scanning the CSV file by columns
        try (Scanner columnScanner = new Scanner(new File(dataset))) {
            while (columnScanner.hasNextLine()) {
                columnCount++;
                columnScanner.nextLine();
            }
        }
        // If the file has a header, the count is being decremented
        if (header == true) { return columnCount -1; }
        else { return columnCount;}
    }

    // --- Function for reading the predictor data from a CSV file
    static float[][] transformPredictorData(String dataset, boolean index, boolean header, int columnCount, int rowCount) throws Exception{
        float[][] predictorData = new float[columnCount][rowCount];
        boolean skip = true;
        // Scanning the CSV file column by column
        try (Scanner scanner = new Scanner(new File(dataset));) {
            for (int i = 0; scanner.hasNextLine(); i++) {
                // Skipping the first iteration if there is a header
                if (header == true && skip == true) {
                    skip = false;
                    scanner.nextLine();
                }
                // Calling the getRecordFromLine function for reading the line in the CSV file and writing it as an array to the data
                predictorData[i] = getRecordFromLine(scanner.nextLine(), index, rowCount -1);
                // Row count -1 because in the last row is the result data
            }
        }
        return predictorData;
    }

    // --- Function for reading the lines of and separating it into a string by a delimiter
    static float[] getRecordFromLine (String line, boolean index, int rowCount) {
        boolean skip = true;
        float[] values = new float[rowCount];
        try (Scanner rowScanner = new Scanner(line)) {
            // Using , as a delimiter for separating the line input
            rowScanner.useDelimiter(",");
            for (int i = 0; rowScanner.hasNext(); i++) {
                // Skip if there is an index
                if ((index == true && skip == true) || i == rowCount) {
                    skip = false;
                    rowScanner.next();
                    // Break if it has iterated through the elements of the line
                    // The last element is going to be skipped (result data)
                    if (i == rowCount) {
                        break;
                    }
                }values[i] = Float.parseFloat(rowScanner.next());
            }
        }
        return values;
    }

    // --- Function for reading the predictor data from a CSV file
    static String[] transformResultData(String dataset, boolean index, boolean header, int columnCount, int rowCount) throws Exception{
        String[] resultData = new String[columnCount];
        boolean skip = true;
        // Scanning the CSV file column by column
        try (Scanner scanner = new Scanner(new File(dataset));) {
            for (int i = 0; scanner.hasNextLine(); i++) {
                // Skip if there is a header
                if (header == true && skip == true) {
                    skip = false;
                    scanner.nextLine();
                }
                // Calling the getRecordFromLineString function for reading the line in the CSV file and writing it as an string element
                resultData[i] = getRecordFromLineString(scanner.nextLine(), index, rowCount);
            }
        }
        return resultData;
    }

    // --- Function for reading the lines of and separating it into a string by a delimiter
    static String getRecordFromLineString (String line, boolean index, int rowCount) {
        boolean skip = true;
        String values = new String();
        try (Scanner rowScanner = new Scanner(line)) {
            // Using , as a delimiter for separating the line input
            rowScanner.useDelimiter(",");
            for (int i = 0; rowScanner.hasNext(); i++) {
                if (i == rowCount) {
                    // Only writing the last element to the return string (result data)
                    if (i == rowCount) {
                        values = rowScanner.next();
                        break;
                    }
                }
                rowScanner.next();
            }
        }
        return values;
    }
}
