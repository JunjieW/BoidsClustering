package nyu.predictiveanalytics;

import java.util.ArrayList;
import java.util.Vector;

import net.sf.javaml.distance.DistanceMeasure;
import nyu.predictiveanalytics.MyKMeans;
import nyu.predictiveanalytics.distance.MyCosineDistance;
import nyu.predictiveanalytics.distance.MyEuclideanDistance;
import nyu.predictiveanalytics.distance.MyMinHash;
import nyu.predictiveanalytics.tfidf.DocParser;


public class PA_HW_2 {
	private static Entity[] iris_entitySet;
	private static Entity[] test_docSet;
	
	public static void main(String[] args) {
		PA_HW_2 executer = new PA_HW_2();
		
		executer.initIrisSet();
		executer.initDocSet();
		
		//===== Preprocess (remove stop words, stemming) & Transform docs into TF-IDF matix =======
		String docPath = "C:\\Users\\DC-IT-Dev\\Desktop\\NYU2015FALL-Course-Material\\PA\\homework-2\\articles";
    	DocParser docParser = new DocParser(docPath);
    	ArrayList<double[]> tfidfMatrix = docParser.tfIdfCalculator();
    	
    	//===== Transform tf-idf matrix into universal data type (entity) ======
    	Entity[] my_doc_set = new Entity[tfidfMatrix.size()];
    	for(int i = 0; i < tfidfMatrix.size(); i++){
    		double[] temp = new double[tfidfMatrix.get(i).length];
    		System.arraycopy( tfidfMatrix.get(i), 0, temp, 0, tfidfMatrix.size());
    		Entity temEntity = new Entity(temp);
    		my_doc_set[i] = temEntity;
    	}
    	
    	ExportManager.exportToCsv(my_doc_set);
    	
    	//=========== Execute KMeans with various methods of distance measurement ===========
		MyEuclideanDistance dm_eculidea = new MyEuclideanDistance();
		MyCosineDistance dm_cosine = new MyCosineDistance();
		MyMinHash dm_minhash = new MyMinHash();
		
		int numOfClusters = 3;
		int numOfIterations = 20;
		MyKMeans myKmeans_euclidean = new MyKMeans(numOfClusters, numOfIterations, dm_eculidea, my_doc_set);
		executer.runKmeans(myKmeans_euclidean);
		
		MyKMeans myKmeans_cosine = new MyKMeans(numOfClusters, numOfIterations, dm_cosine, my_doc_set);
		executer.runKmeans(myKmeans_cosine);
		
		MyKMeans myKmeans_minhash = new MyKMeans(numOfClusters, numOfIterations, dm_minhash, my_doc_set);
		executer.runKmeans(myKmeans_minhash);
		
	}
	
	private void runKmeans(MyKMeans myKmeans){
		if (myKmeans.m_dm.getClass() == MyEuclideanDistance.class){
			System.out.print("=========== K-means with Euclidean Distance ==============\n");
		}else if (myKmeans.m_dm.getClass() == MyCosineDistance.class) {
			System.out.print("=========== K-means with Cosine Distance =================\n");
		}else if (myKmeans.m_dm.getClass() == MyMinHash.class) {
			System.out.print("=========== K-means with Minhash/Jarcard Distance =========\n");
		}
		
		myKmeans.doClustering();
		Vector<Vector<Integer>> result = myKmeans.getClusterByEntityIndex();
		int index_cluster = 0;
		for(Vector<Integer> cluster : result){
			System.out.print("Cluser " + index_cluster + ": ");
			for(int entity_index : cluster){
				if (cluster.indexOf(entity_index) == 0)
					System.out.print(entity_index);
				else
					System.out.print("," + entity_index);
			}
			System.out.print("\n");
			index_cluster++;
		}
	}
	
	private void initDocSet(){
		test_docSet = new Entity[] {
				new Entity(new double[] { (double) 1, (double) 0, (double) 0, (double) 1, (double) 0 }),
				new Entity(new double[] { (double) 0, (double) 0, (double) 1, (double) 0, (double) 0 }),
				new Entity(new double[] { (double) 0, (double) 1, (double) 0, (double) 1, (double) 1 }),
				new Entity(new double[] { (double) 1, (double) 0, (double) 1, (double) 1, (double) 0 }),
		};
	}
	
	private void initIrisSet(){
		iris_entitySet = new Entity[] { new Entity(new double[] { (double) 5.1, (double) 3.5, (double) 1.4, (double) 0.2 }),
				new Entity(new double[] { (double) 4.9, (double) 3, (double) 1.4, (double) 0.2 }),
				new Entity(new double[] { (double) 4.7, (double) 3.2, (double) 1.3, (double) 0.2 }),
				new Entity(new double[] { (double) 4.6, (double) 3.1, (double) 1.5, (double) 0.2 }),
				new Entity(new double[] { (double) 5, (double) 3.6, (double) 1.4, (double) 0.2 }),
				new Entity(new double[] { (double) 5.4, (double) 3.9, (double) 1.7, (double) 0.4 }),
				new Entity(new double[] { (double) 4.6, (double) 3.4, (double) 1.4, (double) 0.3 }),
				new Entity(new double[] { (double) 5, (double) 3.4, (double) 1.5, (double) 0.2 }),
				new Entity(new double[] { (double) 4.4, (double) 2.9, (double) 1.4, (double) 0.2 }),
				new Entity(new double[] { (double) 4.9, (double) 3.1, (double) 1.5, (double) 0.1 }),
				new Entity(new double[] { (double) 5.4, (double) 3.7, (double) 1.5, (double) 0.2 }),
				new Entity(new double[] { (double) 4.8, (double) 3.4, (double) 1.6, (double) 0.2 }),
				new Entity(new double[] { (double) 4.8, (double) 3, (double) 1.4, (double) 0.1 }),
				new Entity(new double[] { (double) 4.3, (double) 3, (double) 1.1, (double) 0.1 }),
				new Entity(new double[] { (double) 5.8, (double) 4, (double) 1.2, (double) 0.2 }),
				new Entity(new double[] { (double) 5.7, (double) 4.4, (double) 1.5, (double) 0.4 }),
				new Entity(new double[] { (double) 5.4, (double) 3.9, (double) 1.3, (double) 0.4 }),
				new Entity(new double[] { (double) 5.1, (double) 3.5, (double) 1.4, (double) 0.3 }),
				new Entity(new double[] { (double) 5.7, (double) 3.8, (double) 1.7, (double) 0.3 }),
				new Entity(new double[] { (double) 5.1, (double) 3.8, (double) 1.5, (double) 0.3 }),
				new Entity(new double[] { (double) 5.4, (double) 3.4, (double) 1.7, (double) 0.2 }),
				new Entity(new double[] { (double) 5.1, (double) 3.7, (double) 1.5, (double) 0.4 }),
				new Entity(new double[] { (double) 4.6, (double) 3.6, (double) 1, (double) 0.2 }),
				new Entity(new double[] { (double) 5.1, (double) 3.3, (double) 1.7, (double) 0.5 }),
				new Entity(new double[] { (double) 4.8, (double) 3.4, (double) 1.9, (double) 0.2 }),
				new Entity(new double[] { (double) 5, (double) 3, (double) 1.6, (double) 0.2 }),
				new Entity(new double[] { (double) 5, (double) 3.4, (double) 1.6, (double) 0.4 }),
				new Entity(new double[] { (double) 5.2, (double) 3.5, (double) 1.5, (double) 0.2 }),
				new Entity(new double[] { (double) 5.2, (double) 3.4, (double) 1.4, (double) 0.2 }),
				new Entity(new double[] { (double) 4.7, (double) 3.2, (double) 1.6, (double) 0.2 }),
				new Entity(new double[] { (double) 4.8, (double) 3.1, (double) 1.6, (double) 0.2 }),
				new Entity(new double[] { (double) 5.4, (double) 3.4, (double) 1.5, (double) 0.4 }),
				new Entity(new double[] { (double) 5.2, (double) 4.1, (double) 1.5, (double) 0.1 }),
				new Entity(new double[] { (double) 5.5, (double) 4.2, (double) 1.4, (double) 0.2 }),
				new Entity(new double[] { (double) 4.9, (double) 3.1, (double) 1.5, (double) 0.1 }),
				new Entity(new double[] { (double) 5, (double) 3.2, (double) 1.2, (double) 0.2 }),
				new Entity(new double[] { (double) 5.5, (double) 3.5, (double) 1.3, (double) 0.2 }),
				new Entity(new double[] { (double) 4.9, (double) 3.1, (double) 1.5, (double) 0.1 }),
				new Entity(new double[] { (double) 4.4, (double) 3, (double) 1.3, (double) 0.2 }),
				new Entity(new double[] { (double) 5.1, (double) 3.4, (double) 1.5, (double) 0.2 }),
				new Entity(new double[] { (double) 5, (double) 3.5, (double) 1.3, (double) 0.3 }),
				new Entity(new double[] { (double) 4.5, (double) 2.3, (double) 1.3, (double) 0.3 }),
				new Entity(new double[] { (double) 4.4, (double) 3.2, (double) 1.3, (double) 0.2 }),
				new Entity(new double[] { (double) 5, (double) 3.5, (double) 1.6, (double) 0.6 }),
				new Entity(new double[] { (double) 5.1, (double) 3.8, (double) 1.9, (double) 0.4 }),
				new Entity(new double[] { (double) 4.8, (double) 3, (double) 1.4, (double) 0.3 }),
				new Entity(new double[] { (double) 5.1, (double) 3.8, (double) 1.6, (double) 0.2 }),
				new Entity(new double[] { (double) 4.6, (double) 3.2, (double) 1.4, (double) 0.2 }),
				new Entity(new double[] { (double) 5.3, (double) 3.7, (double) 1.5, (double) 0.2 }),
				new Entity(new double[] { (double) 5, (double) 3.3, (double) 1.4, (double) 0.2 }),
				new Entity(new double[] { (double) 7, (double) 3.2, (double) 4.7, (double) 1.4 }),
				new Entity(new double[] { (double) 6.4, (double) 3.2, (double) 4.5, (double) 1.5 }),
				new Entity(new double[] { (double) 6.9, (double) 3.1, (double) 4.9, (double) 1.5 }),
				new Entity(new double[] { (double) 5.5, (double) 2.3, (double) 4, (double) 1.3 }),
				new Entity(new double[] { (double) 6.5, (double) 2.8, (double) 4.6, (double) 1.5 }),
				new Entity(new double[] { (double) 5.7, (double) 2.8, (double) 4.5, (double) 1.3 }),
				new Entity(new double[] { (double) 6.3, (double) 3.3, (double) 4.7, (double) 1.6 }),
				new Entity(new double[] { (double) 4.9, (double) 2.4, (double) 3.3, (double) 1.0 }),
				new Entity(new double[] { (double) 6.6, (double) 2.9, (double) 4.6, (double) 1.3 }),
				new Entity(new double[] { (double) 5.2, (double) 2.7, (double) 3.9, (double) 1.4 }),
				new Entity(new double[] { (double) 5, (double) 2, (double) 3.5, (double) 1.0 }),
				new Entity(new double[] { (double) 5.9, (double) 3, (double) 4.2, (double) 1.5 }),
				new Entity(new double[] { (double) 6, (double) 2.2, (double) 4, (double) 1.0 }),
				new Entity(new double[] { (double) 6.1, (double) 2.9, (double) 4.7, (double) 1.4 }),
				new Entity(new double[] { (double) 5.6, (double) 2.9, (double) 3.6, (double) 1.3 }),
				new Entity(new double[] { (double) 6.7, (double) 3.1, (double) 4.4, (double) 1.4 }),
				new Entity(new double[] { (double) 5.6, (double) 3, (double) 4.5, (double) 1.5 }),
				new Entity(new double[] { (double) 5.8, (double) 2.7, (double) 4.1, (double) 1.0 }),
				new Entity(new double[] { (double) 6.2, (double) 2.2, (double) 4.5, (double) 1.5 }),
				new Entity(new double[] { (double) 5.6, (double) 2.5, (double) 3.9, (double) 1.1 }),
				new Entity(new double[] { (double) 5.9, (double) 3.2, (double) 4.8, (double) 1.8 }),
				new Entity(new double[] { (double) 6.1, (double) 2.8, (double) 4, (double) 1.3 }),
				new Entity(new double[] { (double) 6.3, (double) 2.5, (double) 4.9, (double) 1.5 }),
				new Entity(new double[] { (double) 6.1, (double) 2.8, (double) 4.7, (double) 1.2 }),
				new Entity(new double[] { (double) 6.4, (double) 2.9, (double) 4.3, (double) 1.3 }),
				new Entity(new double[] { (double) 6.6, (double) 3, (double) 4.4, (double) 1.4 }),
				new Entity(new double[] { (double) 6.8, (double) 2.8, (double) 4.8, (double) 1.4 }),
				new Entity(new double[] { (double) 6.7, (double) 3, (double) 5, (double) 1.7 }),
				new Entity(new double[] { (double) 6, (double) 2.9, (double) 4.5, (double) 1.5 }),
				new Entity(new double[] { (double) 5.7, (double) 2.6, (double) 3.5, (double) 1.0 }),
				new Entity(new double[] { (double) 5.5, (double) 2.4, (double) 3.8, (double) 1.1 }),
				new Entity(new double[] { (double) 5.5, (double) 2.4, (double) 3.7, (double) 1.0 }),
				new Entity(new double[] { (double) 5.8, (double) 2.7, (double) 3.9, (double) 1.2 }),
				new Entity(new double[] { (double) 6, (double) 2.7, (double) 5.1, (double) 1.6 }),
				new Entity(new double[] { (double) 5.4, (double) 3, (double) 4.5, (double) 1.5 }),
				new Entity(new double[] { (double) 6, (double) 3.4, (double) 4.5, (double) 1.6 }),
				new Entity(new double[] { (double) 6.7, (double) 3.1, (double) 4.7, (double) 1.5 }),
				new Entity(new double[] { (double) 6.3, (double) 2.3, (double) 4.4, (double) 1.3 }),
				new Entity(new double[] { (double) 5.6, (double) 3, (double) 4.1, (double) 1.3 }),
				new Entity(new double[] { (double) 5.5, (double) 2.5, (double) 4, (double) 1.3 }),
				new Entity(new double[] { (double) 5.5, (double) 2.6, (double) 4.4, (double) 1.2 }),
				new Entity(new double[] { (double) 6.1, (double) 3, (double) 4.6, (double) 1.4 }),
				new Entity(new double[] { (double) 5.8, (double) 2.6, (double) 4, (double) 1.2 }),
				new Entity(new double[] { (double) 5, (double) 2.3, (double) 3.3, (double) 1.0 }),
				new Entity(new double[] { (double) 5.6, (double) 2.7, (double) 4.2, (double) 1.3 }),
				new Entity(new double[] { (double) 5.7, (double) 3, (double) 4.2, (double) 1.2 }),
				new Entity(new double[] { (double) 5.7, (double) 2.9, (double) 4.2, (double) 1.3 }),
				new Entity(new double[] { (double) 6.2, (double) 2.9, (double) 4.3, (double) 1.3 }),
				new Entity(new double[] { (double) 5.1, (double) 2.5, (double) 3, (double) 1.1 }),
				new Entity(new double[] { (double) 5.7, (double) 2.8, (double) 4.1, (double) 1.3 }),
				new Entity(new double[] { (double) 6.3, (double) 3.3, (double) 6, (double) 2.5 }),
				new Entity(new double[] { (double) 5.8, (double) 2.7, (double) 5.1, (double) 1.9 }),
				new Entity(new double[] { (double) 7.1, (double) 3, (double) 5.9, (double) 2.1 }),
				new Entity(new double[] { (double) 6.3, (double) 2.9, (double) 5.6, (double) 1.8 }),
				new Entity(new double[] { (double) 6.5, (double) 3, (double) 5.8, (double) 2.2 }),
				new Entity(new double[] { (double) 7.6, (double) 3, (double) 6.6, (double) 2.1 }),
				new Entity(new double[] { (double) 4.9, (double) 2.5, (double) 4.5, (double) 1.7 }),
				new Entity(new double[] { (double) 7.3, (double) 2.9, (double) 6.3, (double) 1.8 }),
				new Entity(new double[] { (double) 6.7, (double) 2.5, (double) 5.8, (double) 1.8 }),
				new Entity(new double[] { (double) 7.2, (double) 3.6, (double) 6.1, (double) 2.5 }),
				new Entity(new double[] { (double) 6.5, (double) 3.2, (double) 5.1, (double) 2.0 }),
				new Entity(new double[] { (double) 6.4, (double) 2.7, (double) 5.3, (double) 1.9 }),
				new Entity(new double[] { (double) 6.8, (double) 3, (double) 5.5, (double) 2.1 }),
				new Entity(new double[] { (double) 5.7, (double) 2.5, (double) 5, (double) 2.0 }),
				new Entity(new double[] { (double) 5.8, (double) 2.8, (double) 5.1, (double) 2.4 }),
				new Entity(new double[] { (double) 6.4, (double) 3.2, (double) 5.3, (double) 2.3 }),
				new Entity(new double[] { (double) 6.5, (double) 3, (double) 5.5, (double) 1.8 }),
				new Entity(new double[] { (double) 7.7, (double) 3.8, (double) 6.7, (double) 2.2 }),
				new Entity(new double[] { (double) 7.7, (double) 2.6, (double) 6.9, (double) 2.3 }),
				new Entity(new double[] { (double) 6, (double) 2.2, (double) 5, (double) 1.5 }),
				new Entity(new double[] { (double) 6.9, (double) 3.2, (double) 5.7, (double) 2.3 }),
				new Entity(new double[] { (double) 5.6, (double) 2.8, (double) 4.9, (double) 2.0 }),
				new Entity(new double[] { (double) 7.7, (double) 2.8, (double) 6.7, (double) 2.0 }),
				new Entity(new double[] { (double) 6.3, (double) 2.7, (double) 4.9, (double) 1.8 }),
				new Entity(new double[] { (double) 6.7, (double) 3.3, (double) 5.7, (double) 2.1 }),
				new Entity(new double[] { (double) 7.2, (double) 3.2, (double) 6, (double) 1.8 }),
				new Entity(new double[] { (double) 6.2, (double) 2.8, (double) 4.8, (double) 1.8 }),
				new Entity(new double[] { (double) 6.1, (double) 3, (double) 4.9, (double) 1.8 }),
				new Entity(new double[] { (double) 6.4, (double) 2.8, (double) 5.6, (double) 2.1 }),
				new Entity(new double[] { (double) 7.2, (double) 3, (double) 5.8, (double) 1.6 }),
				new Entity(new double[] { (double) 7.4, (double) 2.8, (double) 6.1, (double) 1.9 }),
				new Entity(new double[] { (double) 7.9, (double) 3.8, (double) 6.4, (double) 2.0 }),
				new Entity(new double[] { (double) 6.4, (double) 2.8, (double) 5.6, (double) 2.2 }),
				new Entity(new double[] { (double) 6.3, (double) 2.8, (double) 5.1, (double) 1.5 }),
				new Entity(new double[] { (double) 6.1, (double) 2.6, (double) 5.6, (double) 1.4 }),
				new Entity(new double[] { (double) 7.7, (double) 3, (double) 6.1, (double) 2.3 }),
				new Entity(new double[] { (double) 6.3, (double) 3.4, (double) 5.6, (double) 2.4 }),
				new Entity(new double[] { (double) 6.4, (double) 3.1, (double) 5.5, (double) 1.8 }),
				new Entity(new double[] { (double) 6, (double) 3, (double) 4.8, (double) 1.8 }),
				new Entity(new double[] { (double) 6.9, (double) 3.1, (double) 5.4, (double) 2.1 }),
				new Entity(new double[] { (double) 6.7, (double) 3.1, (double) 5.6, (double) 2.4 }),
				new Entity(new double[] { (double) 6.9, (double) 3.1, (double) 5.1, (double) 2.3 }),
				new Entity(new double[] { (double) 5.8, (double) 2.7, (double) 5.1, (double) 1.9 }),
				new Entity(new double[] { (double) 6.8, (double) 3.2, (double) 5.9, (double) 2.3 }),
				new Entity(new double[] { (double) 6.7, (double) 3.3, (double) 5.7, (double) 2.5 }),
				new Entity(new double[] { (double) 6.7, (double) 3, (double) 5.2, (double) 2.3 }),
				new Entity(new double[] { (double) 6.3, (double) 2.5, (double) 5, (double) 1.9 }),
				new Entity(new double[] { (double) 6.5, (double) 3, (double) 5.2, (double) 2.0 }),
				new Entity(new double[] { (double) 6.2, (double) 3.4, (double) 5.4, (double) 2.3 }),
				new Entity(new double[] { (double) 5.9, (double) 3, (double) 5.1, (double) 1.8 }) };
	}

	
}
