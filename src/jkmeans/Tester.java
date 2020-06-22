package jkmeans;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class demonstrates the use of the Kmeans library and helper classes. Based on code written Fall 2012.
 */

public class Tester {
    
    private static int[] seeds;

    public static void main(String[] args) {
        //Grab params here, add bounds checking
        String filename = args[0];
        String dir = args[1];
        int k = Integer.parseInt(args[2]);
        int i = Integer.parseInt(args[3]);
        
        //Use DataFileReader to process data file into double[][].
        double[][] data = DataFileReader.ReadCSVFile(new File(filename));
        int n = data.length;

        //Choose initial seeds for cluster centroids.
        randomSeeds(k, n);  //This will eventually be replaced by k-means++
        
        //Grab a list of the final centroids after clustering.
        Cluster[]clusters = KMeans.kmeans(data, k, i, seeds);
        double[][]centroids = new double[clusters.length][2]; //Clustering executed in this line

        for (int m = 0; m < k; m++) {
            centroids[m] = clusters[m].Centroid();
        }

        //Print output
        System.out.println("Clustering complete. Final centroids:");
        
        for (int j = 0; j < centroids.length; j++) {
            StringBuilder line = new StringBuilder();
            line.append("Centroid ");
            line.append(j);
            line.append(": ");
            
            for (int m = 0; m < centroids[0].length; m++) {
                line.append("\t");
                line.append(centroids[j][m]);
            }
            
            System.out.println(line.toString());
        }


        try {
            File myFile = new File(dir);
            myFile.createNewFile();

            FileWriter myWrite = new FileWriter(dir);
            for (int j = 0; j < clusters.length; j++) {
                ArrayList<double[]> points = clusters[j].GetPoints();
                for (int iter = 0; iter < points.size(); iter++) {
                    double[] point = points.get(iter);
                    myWrite.write(point[0] + ", " + point[1] + ", " + (j+1) + "\n");
                }

            }
            myWrite.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error has occurred");
            e.printStackTrace();
        }
    }
    
    /**
     * This method randomly selects the indices of K points to use as the initial centroids for seeding.
     * @param k Number of clusters that exist in the data.
     * @param n Total number of candidate points in the data.
     */
    private static void randomSeeds(int k, int n) {
        seeds = new int[k];
        int count = 0;
        
        //Randomly choose k points to be centroids. After each choice, check to see if index
        //has already been used. If so, choose another.
        while (count < k) {
            boolean uniqueIndex = true;
            Random rand = new Random();
            int index = rand.nextInt(n);
            
            //Check for uniqueness
            for (int i = 0; i < count; i++) {
                if (index == seeds[i]) {
                    uniqueIndex = false;
                }
            }
            
            //If unique, add index and continue
            if(uniqueIndex) {
                seeds[count] = index;
                count++;
            }
        }
    }
}