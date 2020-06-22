package jkmeans;

public class KMeans {
    
    private static int K;
    private static int I;
    private static int N;
    private static int D;
    private static double[][] data;
    private static Cluster[] clusters;
    
    /**
     * Performs k-means clustering on data set, provided K,I, and seed positions.
     * 
     * @param dat   Array of data point coordinate, expressed as doubles.
     * @param k     Number of clusters in the data set.
     * @param iter  Number of iterations to run k-means on data set
     * @param seeds List of point indices to be used as seeds for initial centroids.
     * @return 
     */
    public static Cluster[] kmeans(double[][] dat, int k, int iter, int[] seeds) {
        K = k;
        I = iter;
        N = dat.length;
        D = dat[0].length;
        //Kmeans object keeps own copy of input data, array of clusters w/centroids.
        data = dat;
        clusters = new Cluster[K];
        double[][] centroids = new double[K][D];    //Used only to hold final centroids for output
        
        //Initialize K clusters with the K seeds that were pre-chosen.
        for (int i = 0; i < K; i++) {
            clusters[i] = new Cluster(data[seeds[i]]);
        }
        
        boolean needToBeCleared;
        //For each iteration I, cluster the points to centroids, calculate new centroids, and clear points.
        for (int i = 0; i < I; i++) {
            needToBeCleared = i != (I - 1);
            ClusterPoints(needToBeCleared);
            System.out.println("Iteration " + (i + 1) + " complete...\n");
        }
        
        //Copy each of the K centroids to an array for output.
        // for (int i = 0; i < K; i++) {
        //     // centroids[i] = clusters[i].Centroid();
        //     centroids[i] = clusters[i];
        // }
        
        // return centroids;
        return clusters;
    }
    
    /**
     * Clusters all data points into their nearest centroid's cluster.
     * 
     */
    private static void ClusterPoints(boolean needClear) {
        //Assign points to nearest cluster
        for (int i = 0; i < N; i++) {
            if (data[i] == null) {  //This may be too late for error check, may remove later
                System.out.println("There is no point...");
            }
            
            //Insert point into cluster with nearest centroid
            clusters[findNearestCentroid(data[i])].Insert(data[i]);
        }
        
        //Calculate new centroid for each cluster
        for (int i = 0; i < K; i++) {
            clusters[i].CalcCentroid();
        }
        
        //Total SSE is sum of SSE of all clusters, overall will decrease.
        double totalSSE = 0.;
        
        //Print out SSE's to monitor progress
        for (int i = 0; i < K; i++) {
            double sse = clusters[i].SumSquareError();
            
            totalSSE += sse;
            System.out.println("The SSE for cluster " + (i + 1) + " is " + sse);
        }
        
        System.out.println("The total SSE for this iteration: " + totalSSE);
        
        //Clear the points in all clusters
        if (needClear){
            for (int i = 0; i < K; i++) {
                clusters[i].ClearPoints();
            }
        }
    }
    
    /**
     * Calculates the nearest centroid of the candidate point, returns the cluster's index.
     * 
     * @param candidate List of coordinates of a point, expressed as doubles.
     * @return Index of cluster with nearest centroid.
     */
    private static int findNearestCentroid(double[] candidate) {
        int pos = 0;
        double minDist = Distance.Euclidean(candidate, clusters[pos].Centroid());
        
        //Check distance of each centroid after the first.
        for (int i = 1; i < clusters.length; i++) {
            if (Distance.Euclidean(candidate, clusters[i].Centroid()) < minDist) {
                pos = i;
                minDist = Distance.Euclidean(candidate, clusters[pos].Centroid());
            }
        }
        
        return pos;
    }    
}