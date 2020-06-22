package jkmeans;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * This class holds methods for returning an array of data values from CSV/TSV files using Apache Commons library.
 * 
 */
public class DataFileReader {
    
    /**
     * This method accepts a CSV file, no headers, and returns an array of values expressed as double.
     * 
     * @param dataFile, CSV file containing only numeric data to be clustered.
     * @return Array of values expressed as double values.
     */
    public static double[][] ReadCSVFile(File dataFile) {
        double[][] data = null; //Check for null response, to make sure everything went ok.
        FileReader reader = null;
        CSVParser parser = null;
        CSVFormat format = CSVFormat.DEFAULT;
        
        try {
            reader = new FileReader(dataFile);
            parser = new CSVParser(reader, format);
            
            List records = parser.getRecords();
            CSVRecord firstRecord = (CSVRecord) records.get(0);
            int numRecords = records.size();    //Assuming each line is a data point
            int numDimen = firstRecord.size();  //Assuming all data points have equal dimensionality
            
            data = new double[numRecords][numDimen];
            
            for (int i = 0; i < numRecords; i++) {
                CSVRecord thisRecord = (CSVRecord) records.get(i);
                
                for (int j = 0; j < numDimen; j++) {
                    data[i][j] = Double.parseDouble(thisRecord.get(j)); //Assuming all data is numeric
                }
            }
        } catch (IOException ex) {
            System.out.println("Error reading data file: " + ex.getMessage() + "...");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    System.out.println("Error closing file reader: " + ex.getMessage() + "...");
                }
            }
            
            if (parser != null) {
                try {
                    parser.close();
                } catch (IOException ex) {
                    System.out.println("Error closing CSV parser: " + ex.getMessage() + "...");
                }
            }
        }
        
        return data;
    }
    
    /**
     * This method accepts a TSV file, no headers, and returns an array of values expressed as double.
     * 
     * @param dataFile, TSV file containing only numeric data to be clustered.
     * @return Array of values expressed as double values.
     */
    public static double[][] ReadTSVFile(File dataFile) {
        double[][] data = null;
        //To-Do
        return data;
    }
}
