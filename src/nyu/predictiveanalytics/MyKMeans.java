package nyu.predictiveanalytics;

import java.util.Random;
import java.util.Vector;

public class MyKMeans {
	private static boolean IS_DEBUG = false;
	
    private Random m_rand;
    private DistanceMeasure m_dm;
    
	private static double STANDARD_OF_CENTROID_CHANGE = 0.0001;
	
	private int m_numClusters = -1;
    private int m_numIterations = -1;
    private Entity[] m_centroids;
    private Entity[] m_entityArray;
    int m_dimensionOfEntityLength = -1;
    
    private Vector<Vector<Entity>> m_vecFinalClusters;
    private Vector<Vector<Integer>> m_vecFinalClustersUsingEntityIndex;
    
    
	public MyKMeans(int numClusters, int numIter, DistanceMeasure dm, Entity[] entityArray){
		m_numIterations = numIter;
		m_dm = dm;
		m_numClusters = numClusters;
		m_entityArray = entityArray;
		
		m_dimensionOfEntityLength = entityArray[0].m_featureDimension;
		m_rand = new Random();
		
		if(numClusters>entityArray.length)
			throw new RuntimeException("k > data sample size");
	}
	
	private void initCentroids(){
		m_centroids = new Entity[m_numClusters];
		int[] r_values = new int[m_numClusters];
		for (int j = 0; j < m_numClusters; j++) {
			r_values[j] = -1;
		}
		int it_while =0;
        while( it_while < m_numClusters){
            int number = m_rand.nextInt(m_entityArray.length - 1);
            boolean duplicateed_flag = false;
            for(int j = 0; j <= it_while; j++){
                if(number == r_values[j]) {
                	duplicateed_flag = true;
                	break;
                }
            }
            if(duplicateed_flag == false) {
            	r_values[it_while] = number;
            	it_while++;
            }
        }
        for(int i = 0; i < m_numClusters; i++){
        	this.m_centroids[i] = this.m_entityArray[r_values[i]];
        }
        //======For Debug (Print initial centoids)=======
		if (IS_DEBUG) {
			String str = "r_values[";
			for (int j = 0; j < m_numClusters; j++) {
				str += r_values[j] + ",";
			}
			str += "]";
			System.out.print(str + "\n");
			str = "Initial centroids:";
			for (int j = 0; j < m_numClusters; j++) {
				str += this.m_centroids[j].toString() + ",";
			}
			System.out.print(str + "\n");
		}
    	//=========================================================
	}
	
	public Vector<Vector<Entity>> doClustering() {
		initCentroids();
		
		int iterationCount = 0;
        boolean centroidsChanged = true;
        boolean randomCentroids = true;
        while (randomCentroids || (iterationCount < this.m_numIterations && centroidsChanged)){
        	iterationCount++;
            
            // find the nearest centroid for every entities 
        	int[] assignment = new int[m_entityArray.length];
            for (int i = 0; i < m_entityArray.length; i++) {
                int tmpCluster = 0;
                double minDistance = m_dm.computeDistance(m_centroids[0], m_entityArray[i]);
                for (int j = 0; j < this.m_numClusters; j++) {
                    double dist = m_dm.computeDistance(m_centroids[j], m_entityArray[i]);
                    if ( dist < minDistance) {
                        minDistance = dist;
                        tmpCluster = j;
                    }
                }
                assignment[i] = tmpCluster;
            }
            
            // calculate average_centroid for each cluster
            double[][] sumClusterFeatureValue = new double[this.m_numClusters][this.m_dimensionOfEntityLength];
            int[] countClusterMember = new int[this.m_numClusters];
            for (int i = 0; i < m_entityArray.length; i++) {
                Entity enti = m_entityArray[i]; // get entity[i]
                int clusterIndex = assignment[i]; // get entity[i]'s temp cluster tag
                for (int j = 0; j < m_dimensionOfEntityLength; j++) {
                	sumClusterFeatureValue[clusterIndex][j] += enti.m_features[j];
                }
                countClusterMember[clusterIndex]++;
            }
            centroidsChanged = false;
            randomCentroids = false;
            
            //======For Debug (Print cluster tags for entities)=======
            
			if (IS_DEBUG) {
				String str = "Iteration-" + iterationCount + ":";
				for (int j = 0; j < m_entityArray.length; j++) {
					str += assignment[j] + ",";
				}
				System.out.print(str + "\n");
			}
        	//=========================================================
            
            // Update centroids
            for (int i = 0; i < this.m_numClusters; i++) {
            	
            	//TODO: Bug fixing for (cluster member = 0)
                if (countClusterMember[i] <= 0)
                	throw new RuntimeException("[K-Means iteration] Cluster[" + i + "] gets empty");
                
                // create new virtual centroid[i]
                Entity newCentroid = new Entity(new float[m_dimensionOfEntityLength]);
                for (int j = 0; j < m_dimensionOfEntityLength; j++) {
                	newCentroid.m_features[j] = (float) sumClusterFeatureValue[i][j] / countClusterMember[i];
                }
                // compare with the standard of change
                if(m_dm.computeDistance(newCentroid, m_centroids[i]) > STANDARD_OF_CENTROID_CHANGE) {
                	centroidsChanged = true;
                	m_centroids[i] = newCentroid;
                }
            }
        }
        
        
        assemblyClusters();
        return m_vecFinalClusters;
		
	}
	
	public void assemblyClusters(){
		m_vecFinalClusters = new Vector<Vector<Entity>>(m_numClusters);
		m_vecFinalClustersUsingEntityIndex = new Vector<Vector<Integer>>(m_numClusters);
		
		for(int i = 0; i < this.m_numClusters; i++){
			m_vecFinalClusters.add(new Vector<Entity>());
            m_vecFinalClustersUsingEntityIndex.add(new Vector<Integer>());
		}
		
        // Final run to set cluster tag for every entity
        for (int i = 0; i < this.m_entityArray.length; i++) {
            int tmpCluster = 0;
            double minDistance = m_dm.computeDistance(m_centroids[0], this.m_entityArray[i]);
            for (int j = 0; j < m_centroids.length; j++) {
                double dist = m_dm.computeDistance(m_centroids[j], this.m_entityArray[i]);
                if (dist < minDistance) {
                    minDistance = dist;
                    tmpCluster = j;
                }
            }
            m_vecFinalClusters.get(tmpCluster).add(this.m_entityArray[i]);
            m_vecFinalClustersUsingEntityIndex.get(tmpCluster).add(i);
        }
	}
	
	public Vector<Vector<Integer>> getClusterByEntityIndex(){
		return m_vecFinalClustersUsingEntityIndex;
	}
}
