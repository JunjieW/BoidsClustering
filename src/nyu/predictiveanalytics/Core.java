package nyu.predictiveanalytics;

import java.util.Vector;

import nyu.predictiveanalytics.MyKMeans;
import nyu.predictiveanalytics.MyMinHash;


public class Core {
	private static Entity[] iris_entitySet;
	private static Entity[] test_docSet;
	
	public static void main(String[] args) {
		initIrisSet();
		initDocSet();
		
		MyEuclideanDistance dm_eculidea = new MyEuclideanDistance();
		MyMinHash dm_minhash = new MyMinHash();
		
		MyKMeans myKmeans = new MyKMeans(3, 20, dm_minhash, test_docSet);
		
		myKmeans.doClustering();
		Vector<Vector<Integer>> result = myKmeans.getClusterByEntityIndex();
		int index_cluster = 0;
		for(Vector<Integer> cluster : result){
			System.out.print("Cluser " + index_cluster + " :");
			for(int entity_index : cluster){
				System.out.print(entity_index + ",");
			}
			System.out.print("\n");
			index_cluster++;
		}
	}
	
	private static void initDocSet(){
		test_docSet = new Entity[] {
				new Entity(new float[] { (float) 1, (float) 0, (float) 0, (float) 1, (float) 0 }),
				new Entity(new float[] { (float) 0, (float) 0, (float) 1, (float) 0, (float) 0 }),
				new Entity(new float[] { (float) 0, (float) 1, (float) 0, (float) 1, (float) 1 }),
				new Entity(new float[] { (float) 1, (float) 0, (float) 1, (float) 1, (float) 0 }),
		};
	}
	
	private static void initIrisSet(){
		iris_entitySet = new Entity[] { new Entity(new float[] { (float) 5.1, (float) 3.5, (float) 1.4, (float) 0.2 }),
				new Entity(new float[] { (float) 4.9, (float) 3, (float) 1.4, (float) 0.2 }),
				new Entity(new float[] { (float) 4.7, (float) 3.2, (float) 1.3, (float) 0.2 }),
				new Entity(new float[] { (float) 4.6, (float) 3.1, (float) 1.5, (float) 0.2 }),
				new Entity(new float[] { (float) 5, (float) 3.6, (float) 1.4, (float) 0.2 }),
				new Entity(new float[] { (float) 5.4, (float) 3.9, (float) 1.7, (float) 0.4 }),
				new Entity(new float[] { (float) 4.6, (float) 3.4, (float) 1.4, (float) 0.3 }),
				new Entity(new float[] { (float) 5, (float) 3.4, (float) 1.5, (float) 0.2 }),
				new Entity(new float[] { (float) 4.4, (float) 2.9, (float) 1.4, (float) 0.2 }),
				new Entity(new float[] { (float) 4.9, (float) 3.1, (float) 1.5, (float) 0.1 }),
				new Entity(new float[] { (float) 5.4, (float) 3.7, (float) 1.5, (float) 0.2 }),
				new Entity(new float[] { (float) 4.8, (float) 3.4, (float) 1.6, (float) 0.2 }),
				new Entity(new float[] { (float) 4.8, (float) 3, (float) 1.4, (float) 0.1 }),
				new Entity(new float[] { (float) 4.3, (float) 3, (float) 1.1, (float) 0.1 }),
				new Entity(new float[] { (float) 5.8, (float) 4, (float) 1.2, (float) 0.2 }),
				new Entity(new float[] { (float) 5.7, (float) 4.4, (float) 1.5, (float) 0.4 }),
				new Entity(new float[] { (float) 5.4, (float) 3.9, (float) 1.3, (float) 0.4 }),
				new Entity(new float[] { (float) 5.1, (float) 3.5, (float) 1.4, (float) 0.3 }),
				new Entity(new float[] { (float) 5.7, (float) 3.8, (float) 1.7, (float) 0.3 }),
				new Entity(new float[] { (float) 5.1, (float) 3.8, (float) 1.5, (float) 0.3 }),
				new Entity(new float[] { (float) 5.4, (float) 3.4, (float) 1.7, (float) 0.2 }),
				new Entity(new float[] { (float) 5.1, (float) 3.7, (float) 1.5, (float) 0.4 }),
				new Entity(new float[] { (float) 4.6, (float) 3.6, (float) 1, (float) 0.2 }),
				new Entity(new float[] { (float) 5.1, (float) 3.3, (float) 1.7, (float) 0.5 }),
				new Entity(new float[] { (float) 4.8, (float) 3.4, (float) 1.9, (float) 0.2 }),
				new Entity(new float[] { (float) 5, (float) 3, (float) 1.6, (float) 0.2 }),
				new Entity(new float[] { (float) 5, (float) 3.4, (float) 1.6, (float) 0.4 }),
				new Entity(new float[] { (float) 5.2, (float) 3.5, (float) 1.5, (float) 0.2 }),
				new Entity(new float[] { (float) 5.2, (float) 3.4, (float) 1.4, (float) 0.2 }),
				new Entity(new float[] { (float) 4.7, (float) 3.2, (float) 1.6, (float) 0.2 }),
				new Entity(new float[] { (float) 4.8, (float) 3.1, (float) 1.6, (float) 0.2 }),
				new Entity(new float[] { (float) 5.4, (float) 3.4, (float) 1.5, (float) 0.4 }),
				new Entity(new float[] { (float) 5.2, (float) 4.1, (float) 1.5, (float) 0.1 }),
				new Entity(new float[] { (float) 5.5, (float) 4.2, (float) 1.4, (float) 0.2 }),
				new Entity(new float[] { (float) 4.9, (float) 3.1, (float) 1.5, (float) 0.1 }),
				new Entity(new float[] { (float) 5, (float) 3.2, (float) 1.2, (float) 0.2 }),
				new Entity(new float[] { (float) 5.5, (float) 3.5, (float) 1.3, (float) 0.2 }),
				new Entity(new float[] { (float) 4.9, (float) 3.1, (float) 1.5, (float) 0.1 }),
				new Entity(new float[] { (float) 4.4, (float) 3, (float) 1.3, (float) 0.2 }),
				new Entity(new float[] { (float) 5.1, (float) 3.4, (float) 1.5, (float) 0.2 }),
				new Entity(new float[] { (float) 5, (float) 3.5, (float) 1.3, (float) 0.3 }),
				new Entity(new float[] { (float) 4.5, (float) 2.3, (float) 1.3, (float) 0.3 }),
				new Entity(new float[] { (float) 4.4, (float) 3.2, (float) 1.3, (float) 0.2 }),
				new Entity(new float[] { (float) 5, (float) 3.5, (float) 1.6, (float) 0.6 }),
				new Entity(new float[] { (float) 5.1, (float) 3.8, (float) 1.9, (float) 0.4 }),
				new Entity(new float[] { (float) 4.8, (float) 3, (float) 1.4, (float) 0.3 }),
				new Entity(new float[] { (float) 5.1, (float) 3.8, (float) 1.6, (float) 0.2 }),
				new Entity(new float[] { (float) 4.6, (float) 3.2, (float) 1.4, (float) 0.2 }),
				new Entity(new float[] { (float) 5.3, (float) 3.7, (float) 1.5, (float) 0.2 }),
				new Entity(new float[] { (float) 5, (float) 3.3, (float) 1.4, (float) 0.2 }),
				new Entity(new float[] { (float) 7, (float) 3.2, (float) 4.7, (float) 1.4 }),
				new Entity(new float[] { (float) 6.4, (float) 3.2, (float) 4.5, (float) 1.5 }),
				new Entity(new float[] { (float) 6.9, (float) 3.1, (float) 4.9, (float) 1.5 }),
				new Entity(new float[] { (float) 5.5, (float) 2.3, (float) 4, (float) 1.3 }),
				new Entity(new float[] { (float) 6.5, (float) 2.8, (float) 4.6, (float) 1.5 }),
				new Entity(new float[] { (float) 5.7, (float) 2.8, (float) 4.5, (float) 1.3 }),
				new Entity(new float[] { (float) 6.3, (float) 3.3, (float) 4.7, (float) 1.6 }),
				new Entity(new float[] { (float) 4.9, (float) 2.4, (float) 3.3, (float) 1.0 }),
				new Entity(new float[] { (float) 6.6, (float) 2.9, (float) 4.6, (float) 1.3 }),
				new Entity(new float[] { (float) 5.2, (float) 2.7, (float) 3.9, (float) 1.4 }),
				new Entity(new float[] { (float) 5, (float) 2, (float) 3.5, (float) 1.0 }),
				new Entity(new float[] { (float) 5.9, (float) 3, (float) 4.2, (float) 1.5 }),
				new Entity(new float[] { (float) 6, (float) 2.2, (float) 4, (float) 1.0 }),
				new Entity(new float[] { (float) 6.1, (float) 2.9, (float) 4.7, (float) 1.4 }),
				new Entity(new float[] { (float) 5.6, (float) 2.9, (float) 3.6, (float) 1.3 }),
				new Entity(new float[] { (float) 6.7, (float) 3.1, (float) 4.4, (float) 1.4 }),
				new Entity(new float[] { (float) 5.6, (float) 3, (float) 4.5, (float) 1.5 }),
				new Entity(new float[] { (float) 5.8, (float) 2.7, (float) 4.1, (float) 1.0 }),
				new Entity(new float[] { (float) 6.2, (float) 2.2, (float) 4.5, (float) 1.5 }),
				new Entity(new float[] { (float) 5.6, (float) 2.5, (float) 3.9, (float) 1.1 }),
				new Entity(new float[] { (float) 5.9, (float) 3.2, (float) 4.8, (float) 1.8 }),
				new Entity(new float[] { (float) 6.1, (float) 2.8, (float) 4, (float) 1.3 }),
				new Entity(new float[] { (float) 6.3, (float) 2.5, (float) 4.9, (float) 1.5 }),
				new Entity(new float[] { (float) 6.1, (float) 2.8, (float) 4.7, (float) 1.2 }),
				new Entity(new float[] { (float) 6.4, (float) 2.9, (float) 4.3, (float) 1.3 }),
				new Entity(new float[] { (float) 6.6, (float) 3, (float) 4.4, (float) 1.4 }),
				new Entity(new float[] { (float) 6.8, (float) 2.8, (float) 4.8, (float) 1.4 }),
				new Entity(new float[] { (float) 6.7, (float) 3, (float) 5, (float) 1.7 }),
				new Entity(new float[] { (float) 6, (float) 2.9, (float) 4.5, (float) 1.5 }),
				new Entity(new float[] { (float) 5.7, (float) 2.6, (float) 3.5, (float) 1.0 }),
				new Entity(new float[] { (float) 5.5, (float) 2.4, (float) 3.8, (float) 1.1 }),
				new Entity(new float[] { (float) 5.5, (float) 2.4, (float) 3.7, (float) 1.0 }),
				new Entity(new float[] { (float) 5.8, (float) 2.7, (float) 3.9, (float) 1.2 }),
				new Entity(new float[] { (float) 6, (float) 2.7, (float) 5.1, (float) 1.6 }),
				new Entity(new float[] { (float) 5.4, (float) 3, (float) 4.5, (float) 1.5 }),
				new Entity(new float[] { (float) 6, (float) 3.4, (float) 4.5, (float) 1.6 }),
				new Entity(new float[] { (float) 6.7, (float) 3.1, (float) 4.7, (float) 1.5 }),
				new Entity(new float[] { (float) 6.3, (float) 2.3, (float) 4.4, (float) 1.3 }),
				new Entity(new float[] { (float) 5.6, (float) 3, (float) 4.1, (float) 1.3 }),
				new Entity(new float[] { (float) 5.5, (float) 2.5, (float) 4, (float) 1.3 }),
				new Entity(new float[] { (float) 5.5, (float) 2.6, (float) 4.4, (float) 1.2 }),
				new Entity(new float[] { (float) 6.1, (float) 3, (float) 4.6, (float) 1.4 }),
				new Entity(new float[] { (float) 5.8, (float) 2.6, (float) 4, (float) 1.2 }),
				new Entity(new float[] { (float) 5, (float) 2.3, (float) 3.3, (float) 1.0 }),
				new Entity(new float[] { (float) 5.6, (float) 2.7, (float) 4.2, (float) 1.3 }),
				new Entity(new float[] { (float) 5.7, (float) 3, (float) 4.2, (float) 1.2 }),
				new Entity(new float[] { (float) 5.7, (float) 2.9, (float) 4.2, (float) 1.3 }),
				new Entity(new float[] { (float) 6.2, (float) 2.9, (float) 4.3, (float) 1.3 }),
				new Entity(new float[] { (float) 5.1, (float) 2.5, (float) 3, (float) 1.1 }),
				new Entity(new float[] { (float) 5.7, (float) 2.8, (float) 4.1, (float) 1.3 }),
				new Entity(new float[] { (float) 6.3, (float) 3.3, (float) 6, (float) 2.5 }),
				new Entity(new float[] { (float) 5.8, (float) 2.7, (float) 5.1, (float) 1.9 }),
				new Entity(new float[] { (float) 7.1, (float) 3, (float) 5.9, (float) 2.1 }),
				new Entity(new float[] { (float) 6.3, (float) 2.9, (float) 5.6, (float) 1.8 }),
				new Entity(new float[] { (float) 6.5, (float) 3, (float) 5.8, (float) 2.2 }),
				new Entity(new float[] { (float) 7.6, (float) 3, (float) 6.6, (float) 2.1 }),
				new Entity(new float[] { (float) 4.9, (float) 2.5, (float) 4.5, (float) 1.7 }),
				new Entity(new float[] { (float) 7.3, (float) 2.9, (float) 6.3, (float) 1.8 }),
				new Entity(new float[] { (float) 6.7, (float) 2.5, (float) 5.8, (float) 1.8 }),
				new Entity(new float[] { (float) 7.2, (float) 3.6, (float) 6.1, (float) 2.5 }),
				new Entity(new float[] { (float) 6.5, (float) 3.2, (float) 5.1, (float) 2.0 }),
				new Entity(new float[] { (float) 6.4, (float) 2.7, (float) 5.3, (float) 1.9 }),
				new Entity(new float[] { (float) 6.8, (float) 3, (float) 5.5, (float) 2.1 }),
				new Entity(new float[] { (float) 5.7, (float) 2.5, (float) 5, (float) 2.0 }),
				new Entity(new float[] { (float) 5.8, (float) 2.8, (float) 5.1, (float) 2.4 }),
				new Entity(new float[] { (float) 6.4, (float) 3.2, (float) 5.3, (float) 2.3 }),
				new Entity(new float[] { (float) 6.5, (float) 3, (float) 5.5, (float) 1.8 }),
				new Entity(new float[] { (float) 7.7, (float) 3.8, (float) 6.7, (float) 2.2 }),
				new Entity(new float[] { (float) 7.7, (float) 2.6, (float) 6.9, (float) 2.3 }),
				new Entity(new float[] { (float) 6, (float) 2.2, (float) 5, (float) 1.5 }),
				new Entity(new float[] { (float) 6.9, (float) 3.2, (float) 5.7, (float) 2.3 }),
				new Entity(new float[] { (float) 5.6, (float) 2.8, (float) 4.9, (float) 2.0 }),
				new Entity(new float[] { (float) 7.7, (float) 2.8, (float) 6.7, (float) 2.0 }),
				new Entity(new float[] { (float) 6.3, (float) 2.7, (float) 4.9, (float) 1.8 }),
				new Entity(new float[] { (float) 6.7, (float) 3.3, (float) 5.7, (float) 2.1 }),
				new Entity(new float[] { (float) 7.2, (float) 3.2, (float) 6, (float) 1.8 }),
				new Entity(new float[] { (float) 6.2, (float) 2.8, (float) 4.8, (float) 1.8 }),
				new Entity(new float[] { (float) 6.1, (float) 3, (float) 4.9, (float) 1.8 }),
				new Entity(new float[] { (float) 6.4, (float) 2.8, (float) 5.6, (float) 2.1 }),
				new Entity(new float[] { (float) 7.2, (float) 3, (float) 5.8, (float) 1.6 }),
				new Entity(new float[] { (float) 7.4, (float) 2.8, (float) 6.1, (float) 1.9 }),
				new Entity(new float[] { (float) 7.9, (float) 3.8, (float) 6.4, (float) 2.0 }),
				new Entity(new float[] { (float) 6.4, (float) 2.8, (float) 5.6, (float) 2.2 }),
				new Entity(new float[] { (float) 6.3, (float) 2.8, (float) 5.1, (float) 1.5 }),
				new Entity(new float[] { (float) 6.1, (float) 2.6, (float) 5.6, (float) 1.4 }),
				new Entity(new float[] { (float) 7.7, (float) 3, (float) 6.1, (float) 2.3 }),
				new Entity(new float[] { (float) 6.3, (float) 3.4, (float) 5.6, (float) 2.4 }),
				new Entity(new float[] { (float) 6.4, (float) 3.1, (float) 5.5, (float) 1.8 }),
				new Entity(new float[] { (float) 6, (float) 3, (float) 4.8, (float) 1.8 }),
				new Entity(new float[] { (float) 6.9, (float) 3.1, (float) 5.4, (float) 2.1 }),
				new Entity(new float[] { (float) 6.7, (float) 3.1, (float) 5.6, (float) 2.4 }),
				new Entity(new float[] { (float) 6.9, (float) 3.1, (float) 5.1, (float) 2.3 }),
				new Entity(new float[] { (float) 5.8, (float) 2.7, (float) 5.1, (float) 1.9 }),
				new Entity(new float[] { (float) 6.8, (float) 3.2, (float) 5.9, (float) 2.3 }),
				new Entity(new float[] { (float) 6.7, (float) 3.3, (float) 5.7, (float) 2.5 }),
				new Entity(new float[] { (float) 6.7, (float) 3, (float) 5.2, (float) 2.3 }),
				new Entity(new float[] { (float) 6.3, (float) 2.5, (float) 5, (float) 1.9 }),
				new Entity(new float[] { (float) 6.5, (float) 3, (float) 5.2, (float) 2.0 }),
				new Entity(new float[] { (float) 6.2, (float) 3.4, (float) 5.4, (float) 2.3 }),
				new Entity(new float[] { (float) 5.9, (float) 3, (float) 5.1, (float) 1.8 }) };
	}

	
}
