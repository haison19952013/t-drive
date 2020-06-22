package jkmeans;

/**
 * Class holding methods for normalizing data.
 */
public class Normalize {
	
    /**
     * MinMax normalization method normalizes each dimension of each point based on min/max 
     * of that dimension across all points.
     * 
     * @param data Array of data point coordinates, expressed as doubles.
     * @return Array of normalized data points, expressed as doubles.
     */
    public static double[][] minMax(double[][] data) {
        int points = data.length;
        int dimen = data[0].length;
        double[] min;
        double[] max;
        double[] range = new double[dimen]; //Calculate this d times, or else it's done p times for each point
        double[][] norm = new double[points][dimen];

        //Set the min and max of each attribute to the first point in the data set
        min = max = data[0];
        
        //Find the highest and lowest value of each attribute in the set
        for (int p = 0; p < points; p++) {
            for (int d = 0; d < dimen; d++) {
                double dimenValue = data[p][d];
                
                if (dimenValue < min[d]) {  //New min value for dimension?
                    min[d] = dimenValue;
                }
                
                if (dimenValue > max[d]) {  //New max value for dimension?
                    max[d] = dimenValue;
                }
            }
        }
        
        //Calculate range of each dimension
        for (int d = 0; d < dimen; d++) {
            range[d] = max[d] - min[d];
        }
        
        //Normalize data: norm = (raw - min) / range
        for (int p = 0; p < points; p++) {            
            for (int d = 0; d < dimen; d++) {
                if(range[d] == 0.) {    //Avoid divide by zero
                    norm[p][d] = 0.;
                } else {
                    norm[p][d] = (data[p][d] - min[d]) / range[d];
                }
            }
        }
        
        return norm;
    }

    /**
     * MaxNorm normalization method normalizes data points based on the max value of all the points' dimensions.
     * 
     * @param data Array of data point coordinates, expressed as doubles.
     * @return Array of normalized data points, expressed as doubles.
     */
    public static double[][] maxNorm(double[][] data ) {
        int points = data.length;
        int dimen = data[0].length;
        double[] max = data[0]; //Set the max of each attribute to the first point in the set
        double[][] norm = new double[points][dimen];
        
        //Find the max value of each attribute in the set
        for (int p = 0; p < points; p++) {
            for (int d = 0; d < dimen; d++) {
                if (data[p][d] > max[d]) {
                    max[d] = data[p][d];
                }
            }
        }
        
        //Normalize data: norm = raw / max
        for (int p = 0; p < points; p++) {
            for (int d = 0; d < dimen; d++) {
                if (max[d] == 0.) { //Avoid divide by zero
                    norm[p][d] = 0.;
                } else {
                    norm[p][d] = data[p][d] / max[d];
                }
            }
        }
        
        return norm;
    }

    /**
     * RangeNorm normalization method normalizes data points based on the range of all the points' dimensions.
     * 
     * @param data Array of data point coordinates, expressed as doubles.
     * @return Array of normalized data points, expressed as doubles.
     */
    public static double[][] rangeNorm(double[][] data) {
        int points = data.length;
        int dimen = data[0].length;
        double[] min;
        double[] max;
        double[] range = new double[dimen];
        double[][] norm = new double[points][dimen];
        //Set the min and max of each attribute to the first point in the set
        min = max = data[0];
        
        //Find the highest and lowest value of each attribute in the set
        for (int p = 0; p < points; p++) {
            for (int d = 0; d < dimen; d++) {
                double point = data[p][d];
                
                if (point < min[d]) {  //New min value for dimension?
                    min[d] = point;
                }
                
                if (point > max[d]) {  //New max value for dimension?
                    max[d] = point;
                }
            }
        }
        
        //Calculate the range of each dimension's values
        for (int d = 0; d < dimen; d++) {
            range[d] = max[d] - min[d];
        }
        
        //Normalize data: norm = raw / range. (Similar to MinMax)
        for (int p = 0; p < points; p++) {
            for (int d = 0; d < dimen; d++) {
                if (range[d] == 0.) {   //Avoid divide by zero
                    norm[p][d] = 0.;
                } else {
                    norm[p][d] = data[p][d] / range[d];
                }
            }
        }
        
        return norm;
    }

    /**
     * ZScore normalization method normalizes data points based on the z-score of each dimension.
     * 
     * @param data Array of data point coordinates, expressed as doubles.
     * @return Array of normalized data points, expressed as doubles.
     */
    public static double[][] zScore(double[][] data) {
        int dimen = data[0].length;
        int points = data.length;
        double[] mean = new double[dimen];		//List of the mean value of each attribute
        double[] sums = new double[dimen];		//List of the sum of values of each attribute
        double[] stdDev = new double[dimen];		//List of the standard deviation of each attribute
        double[] sumSquared = new double[dimen];	//List of the variance of each attribute
        double[][] norm = new double[points][dimen];
        
        //Find the sum of all values for each attribute in the data set
        for (int p = 0; p < points; p++) {
            for (int d = 0; d < dimen; d++) {
                sums[d] += data[p][d];
            }
        }
        
        //Calculate the mean value of each attribute in the data set
        for (int d = 0; d < dimen; d++) {
            mean[d] = sums[d] / points;
        }
        
        //Find the variance of each attribute in the data set
        for (int p = 0; p < points; p++) {
            for (int d = 0; d < dimen; d++) {
                sumSquared[d] += (data[p][d] - mean[d]) * (data[p][d] - mean[d]);
            }
        }
        
        //Calculate standard deviation of each attribute in the data set
        for (int d = 0; d < dimen; d++) {
            stdDev[d] = Math.sqrt(sumSquared[d] / (points - 1));
        }
        
        //Normalize data: norm = (raw - mean) / std dev.
        for (int p = 0; p < points; p++) {
            for (int d = 0; d < dimen; d++) {
                if (stdDev[d] == 0.) {  //Avoid divide by zero.
                    norm[p][d] = 0.;
                } else {
                    norm[p][d] = (data[p][d] - mean[d]) / stdDev[d];
                }
            }
        }
        
        return norm;
    }

    /**
     * EuclidNorm normalization method normalizes data points based on the Euclidean norm 
     * of all the points' dimensions.
     * 
     * @param data Array of data point coordinates, expressed as doubles.
     * @return Array of normalized data points, expressed as doubles.
     */
    public static double[][] euclidNorm(double[][] data) {
        int points = data.length;
        int dimen = data[0].length;
        double[] euclidNorm = new double [dimen];	//List of the Euclid. norm of each attribute
        double[][] norm = new double[points][dimen];
        
        //Calculate the Euclidean norm of each attribute
        for (int d = 0; d < dimen; d++) {
            double sumSquare = 0.;
            
            //EuclidNorm[dimension] = sum(EuclideanDist(point[dimension]))
            for (int p = 0; p < points; p++) {
                sumSquare += data[p][d] * data[p][d];
            }
            
            euclidNorm[d] = Math.sqrt(sumSquare);
        }
        
        //Normalize data: norm = raw / euclidNorm
        for (int p = 0; p < points; p++) {
            for (int d = 0; d < dimen; d++) {
                if (euclidNorm[d] == 0.) {  //Avoid divide by zero.
                    norm[p][d] = 0.;
                } else {
                    norm[p][d] = data[p][d] / euclidNorm[d];
                }
            }
        }
        
        return norm;
    }

    /**
     * RankedNorm normalization method normalizes data points based on the ranking of all the points.
     * 
     * @param data Array of data point coordinates, expressed as doubles.
     * @return Array of normalized data points, expressed as doubles.
     */
    public static double[][] rankedNorm(double[][] data ) {
        int dimen = data[0].length;
        int points = data.length;
        double[] tmp = data[0];
        double[][] rankings = new double[dimen][points];//List of rankings for each attr. value of each point in data set
        double[][] norm = new double[points][dimen];
        
        //Seed the rankings table with the values from the first point in the data set
        for (int d = 0; d < dimen; d++) {
            rankings[d][0] = tmp[d];
        }
        
        //Rank the data with an insertion sort. This code will fill out a D x N table of double values, in ascending order.
        for (int p = 1; p < points; p++) {
            tmp = data[p];
            
            for (int d = 0; d < dimen; d++) {
                /*
                * Use insertion sort to put attribute in its place. Based on pseudocode in 
                * Intro to Algorithms 3rd Ed. (Cormen, et al.) p.18
                */
                int i = p - 1;          //This corresponds to 'j - 1'.
                double key = tmp[d];    //The value to be inserted
                
                while(i >= 0 && rankings[d][i] > key) {
                    rankings[d][i + 1] = rankings[d][i];	//Move this element up one rank
                    i--;					//Decrement the index
                }
                
                rankings[d][i + 1] = key;	//Set the key in its new correct position
            }
        }
        
        //Normalize data: norm = rank(raw)
        for (int p = 0; p < points; p++) {
            tmp = data[p];
            int rank;       //Lowest rank with this value
            int maxRank;    //Highest rank with this value
            
            for (int d = 0; d < dimen; d++) {
                rank = 0;
                maxRank = 0;

                //Find the lowest rank matching that attribute value
                while (rank < points && tmp[d] > rankings[d][rank]) {
                    rank++;
                    maxRank++;
                }
                
                while (maxRank < points && tmp[d] == rankings[d][maxRank]) {
                    maxRank++;
                }
                
                //If only one ranking has that value, return the ranking
                if (maxRank == rank) {
                    norm[p][d] = rank;
                //If more than one ranking has that value, sum the value of rankings and divide by number
                } else {
                    double sum = 0.;    //Sum the rankings
                    int count = 0;      //Count how many rankings there are with this value
                    
                    for (int i = 0; i <  (maxRank - rank); i++) {
                        sum += (rank + i);
                        count++;
                    }
                    
                    norm[p][d] = sum / count;
                }
            }
        }
        
        return norm;
    }
}