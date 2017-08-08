package com.daviserrano.phd.dimensionality.clustering;

import java.util.ArrayList;
import java.util.List;

import com.daviserrano.phd.nlp.token.Token;

public class KMeansClustering extends TokenClustering{

	//Number of Clusters. This metric should be related to the number of points
    private int NUM_CLUSTERS;
    private int MAX_ITERATIONS;
    
    private KMeansClustering(int num_clusters, int max_iterations) {
    	this.NUM_CLUSTERS = num_clusters;
    	this.MAX_ITERATIONS = max_iterations;
    }
    
    public static KMeansClustering create(List<? extends TokenCluster> firstCentroids, int max_iterations){
    	
    	if(validateClusters(firstCentroids)){
    		KMeansClustering kmc = new KMeansClustering(firstCentroids.size(),max_iterations);
    		kmc.setClusters(firstCentroids);
    		return kmc;
    	}else
    		return null;
    	
    }
    
    public static KMeansClustering create(List<? extends TokenCluster> firstCentroids){
    	return create(firstCentroids,Integer.MAX_VALUE);
    }
    
    private static boolean validateClusters(List<? extends TokenCluster> firstCentroids) {
		for (TokenCluster tokenCluster : firstCentroids) {
			if(tokenCluster.getCentroid() == null || tokenCluster.getTokens().size()<=0)
				return false;
		}
		return true;
	}

	@Override
	public List<? extends TokenCluster> cluster() {
		this.calculate();
		return this.getClusters();
	}
    
	//The process to calculate the K Means, with iterating method.
    public void calculate() {
    	if(this.clusters.size()>0){
    		boolean finish = false;
            int iteration = 0;
            
            // Add in new data, one at a time, recalculating centroids with each new one. 
            while(!finish) {
            	//Clear cluster state
            	clearClusters();
            	
            	List<Token> lastCentroids = getCentroids();
            	
            	//Assign points to the closer cluster
            	assignCluster();
                
                //Calculate new centroids.
            	calculateCentroids();
            	
            	iteration++;
            	
            	List<Token> currentCentroids = getCentroids();
            	
            	//Calculates total distance between new and old Centroids
            	double distance = 0;
            	for(int i = 0; i < lastCentroids.size(); i++) {
            		distance += clusters.get(0).calculateDistance(lastCentroids.get(i),currentCentroids.get(i));
            	}
            	        	
            	if(distance == 0 || iteration>this.MAX_ITERATIONS) {
            		finish = true;
            	}
            }
    	}
    }
    
    private void clearClusters() {
    	for(TokenCluster cluster : clusters) {
    		cluster.clear();
    	}
    }
    
    private List<Token> getCentroids() {
    	List<Token> centroids = new ArrayList<Token>(NUM_CLUSTERS);
    	for(TokenCluster cluster : clusters) {
    		Token aux = cluster.getCentroid();
    		Token point = aux.clone();
    		centroids.add(point);
    	}
    	return centroids;
    }
    
    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max; 
        int cluster = 0;                 
        double distance = 0.0; 
        
        for(Token point : allTokens) {
        	min = max;
            for(int i = 0; i < NUM_CLUSTERS; i++) {
            	TokenCluster c = clusters.get(i);
                distance = c.calculateDistance(point, c.getCentroid());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            clusters.get(cluster).addToken(point);
        }
    }
    
    private void calculateCentroids() {
        for(TokenCluster cluster : clusters) {
            cluster.calculateNewCentroid();
        }
    }

}
