package nyu.predictiveanalytics.flock;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;

import nyu.predictiveanalytics.Entity;
import nyu.predictiveanalytics.tfidf.DocParser;

public class FlockByLeader {
	private Entity[] entityArray;
	private Vector<Entity> vect_entity;
	
	private Vector<int[]> vect_boidPos = new Vector<int[]>();
	
	private Vector<double[]> vect_boidVelocity = new Vector<double[]>();
	private double velocity_max = 30.0;
	private double velocity_min = 5.0;
	
	private double distanceMatrix[][];
	//TODO: Change to find the best outcome
	private double distance_max = 200;
	private double distance_min = 0.0;
	
	private Random rd = new Random();	
	private int space_range_2d = 500;
	
	JFrame jf = new JFrame("FlockByLeader");
	BoidSpaceCanvas bsCanvas = new BoidSpaceCanvas();
	
	FlockByLeader(Entity[] dataset) {
		entityArray = dataset;
		this.intiEntityVector();
		this.initBoidPosition();
		this.initBoidVelocity();
		distanceMatrix = new double[vect_entity.size()][vect_entity.size()];
		this.refreshDistanceMatrix();
		this.initDrawFrame();
		
	}
	
	private void initDrawFrame() {
		jf.setSize(500, 500);
		jf.setContentPane(bsCanvas);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		jf.setVisible(true);
		
		bsCanvas.setDotsAndRepaint(vect_boidPos);
	}
	
	private void intiEntityVector(){
		vect_entity = new Vector<Entity>();
		for(int i = 0; i < entityArray.length; i++){
			vect_entity.add(entityArray[i]);
		}
	}
	
	private void initBoidPosition(){
		System.out.println("========= Position Initialize =========");
		for(int i = 0; i< entityArray.length; i++){
			int x = rd.nextInt(space_range_2d);
			int y = rd.nextInt(space_range_2d);
			int[] pos = {x, y};
			vect_boidPos.add(pos);
			System.out.println("Boid[" + i + "](" + pos[0] + "," + pos[1] + ")");
		}
	}
	
	private void initBoidVelocity(){
		System.out.println("========= Velocity Initialize =========");
		for(int i = 0; i< entityArray.length; i++){
			double v_x;
			double v_y;
//			int temp_v_x = rd.nextInt((int)velocity_max);
//			int temp_v_y = rd.nextInt((int)velocity_max);
			
//			v_x = velocity_max * temp_v_x / Math.sqrt(Math.pow(temp_v_x, 2) + Math.pow(temp_v_y, 2));
//			v_y = velocity_max * temp_v_y / Math.sqrt(Math.pow(temp_v_x, 2) + Math.pow(temp_v_y, 2));
			
			v_x = velocity_max * rd.nextDouble();
			v_y = velocity_max * rd.nextDouble();
			double[] velocity = {v_x, v_y};
			vect_boidVelocity.add(velocity);
			System.out.println("Boid[" + i + "](" + v_x + "," + v_y + ")");
		}
	}
	
	private void refreshDistanceMatrix(){
		double ecludiean_distance;
		for(int i = 0; i < vect_boidPos.size(); i++){
			for(int j = i; j < vect_boidPos.size(); j ++){
				if (i == j){
					ecludiean_distance = 0.0;
					continue;
				}
				ecludiean_distance = Math.sqrt( 
										Math.pow((vect_boidPos.get(i)[0] - vect_boidPos.get(j)[0]),2) 
									  + Math.pow((vect_boidPos.get(i)[1]-vect_boidPos.get(j)[1]),2)
									  );
				distanceMatrix[i][j] = distanceMatrix[j][i] = ecludiean_distance;
			}
		}
	}
	
	private int[] findKNearest(){
		int[] indexOfKNearest = null;
		for (int i = 0; i < vect_boidPos.size(); i ++){
			// If apply kNN, implement this 
		}
		return indexOfKNearest;
	}
	
	private Vector<Entity> getNeighbors(int e_index){
		Vector<Entity> vect_neighbor = new Vector<Entity>();
		for(int i = 0 ; i < vect_entity.size(); i ++){
			if (i == e_index){				
				continue;
			} else if ( distanceMatrix[e_index][i] <= distance_max){
				vect_neighbor.addElement(vect_entity.get(i));
			}
		}
		return vect_neighbor;
	}
	
	/*
	 * compute similarity in original space, using Euclidean distance
	 */
	private double computeSimilarity(Entity entity_b, Entity entity_neibor){
		double sum = 0;
    	for (int i = 0; i < entity_b.m_featureDimension; i++) {
    		//ignore missing values
    		if (!Double.isNaN(entity_b.m_features[i]) && !Double.isNaN(entity_neibor.m_features[i]))
    			sum += Math.pow(entity_b.m_features[i] - entity_neibor.m_features[i], 2);
    	}
    	return 1/(1+Math.sqrt(sum));
	}
	
	/*
	 * compute velocity  
	 */
	private double[] computeVelocity(Entity entity_b, Vector<Entity> vect_neighbors){
		
		int b_index = vect_entity.indexOf(entity_b);
		double[] vOfb = {vect_boidVelocity.get(b_index)[0], vect_boidVelocity.get(b_index)[1]};
		double[] posOfb = {vect_boidPos.get(b_index)[0], vect_boidPos.get(b_index)[1]};
		
		// I use v_ar, v_sr, v_cr to decide the direction of velocity
		double[] v_ar = {0.0, 0.0};
		double[] v_sr = {0.0, 0.0};
		double[] v_sr_delta = {0.0, 0.0};
		double[] v_cr = {posOfb[0], posOfb[1]};
		// then use v_ds, v_dd to decide the value of the velocity
		double v_ds = 0.0;
		double v_dd = 0.0;
		
		for(int i = 0 ; i < vect_neighbors.size(); i++){
			int neighbor_index = vect_entity.indexOf(vect_neighbors.get(i));

			// neigbors' velocity influence on entity_b's velocity
			v_ar[0] = v_ar[0] + vect_boidVelocity.get(neighbor_index)[0];
			v_ar[1] = v_ar[1] + vect_boidVelocity.get(neighbor_index)[1];
			
			// entity_b's doesn't want to crashing on its neigbors.
//			v_sr[0] = v_sr[0] + (vOfb[0] + vect_boidVelocity.get(neighbor_index)[0]) / distanceMatrix[b_index][neighbor_index];
//			v_sr[1] = v_sr[1] + (vOfb[1] + vect_boidVelocity.get(neighbor_index)[1]) / distanceMatrix[b_index][neighbor_index];
			double tmp_delta[] = {0.0, 0.0};
			tmp_delta[0] = (vect_boidPos.get(neighbor_index)[0] - posOfb[0]) * vOfb[0] > 0 ? (vOfb[0] / (vect_boidPos.get(neighbor_index)[0] - posOfb[0])) : tmp_delta[0];
			tmp_delta[1] = (vect_boidPos.get(neighbor_index)[1] - posOfb[1]) * vOfb[1] > 0 ? (vOfb[1] / (vect_boidPos.get(neighbor_index)[1] - posOfb[1])) : tmp_delta[1];
			v_sr_delta[0] = Math.max(v_sr_delta[0], tmp_delta[0]);
			v_sr_delta[1] = Math.max(v_sr_delta[1], tmp_delta[1]);
			v_sr[0] = vOfb[0] - tmp_delta[0];
			v_sr[1] = vOfb[1] - tmp_delta[1];

			
//			// TODO: There is problem, v_cr will dominate if using the sum of every rules.
//			v_cr[0] = v_cr[0] + (posOfb[0] - vect_boidPos.get(neighbor_index)[0]);
//			v_cr[1] = v_cr[1] + (posOfb[1] - vect_boidPos.get(neighbor_index)[1]);
			
			// entity_b has intention to fly into the center of his neighbor
			v_cr[0] = v_cr[0] + vect_boidPos.get(neighbor_index)[0];
			v_cr[1] = v_cr[1] + vect_boidPos.get(neighbor_index)[1];
			
		}
		
		//============= compute final v_ar ====================
		v_ar[0] = v_ar[0] / vect_neighbors.size();
		v_ar[1] = v_ar[1] / vect_neighbors.size();
		
		//============= compute final v_sr ====================
		// we've already done during the for-loop above
		
		//============= compute final v_cr ====================
		// here we get the center of entity_b's neighbors (including entity_b)
		double center[] = {0.0, 0.0};
		center[0] = v_cr[0] / ( 1 + vect_neighbors.size());
		center[1] = v_cr[1] / ( 1 + vect_neighbors.size());
		// now entity_b would like to change the direciton of its velocity withou changing the value of the velocity
		double temp_x;
		double temp_y;
		double norm_v = Math.sqrt(vOfb[0]*vOfb[0] + vOfb[1]*vOfb[1]);
		temp_x = center[0] - posOfb[0];
		temp_y = center[1] - posOfb[1];
		v_cr[0] = (norm_v * temp_x ) / Math.sqrt( Math.pow(temp_x, 2) + Math.pow(temp_y, 2));
		v_cr[1] = (norm_v * temp_y )  / Math.sqrt( Math.pow(temp_x, 2) + Math.pow(temp_y, 2));

//		// We do normalization
//		temp_x = v_ar[0];
//		temp_y = v_ar[1];
//		v_ar[0] = temp_x / Math.sqrt( Math.pow(temp_x, 2) + Math.pow(temp_y, 2));
//		v_ar[1] = temp_y / Math.sqrt( Math.pow(temp_x, 2) + Math.pow(temp_y, 2));
		
//		temp_x = v_sr[0];
//		temp_y = v_sr[1];
//		v_sr[0] = temp_x / Math.sqrt( Math.pow(temp_x, 2) + Math.pow(temp_y, 2));
//		v_sr[1] = temp_y / Math.sqrt( Math.pow(temp_x, 2) + Math.pow(temp_y, 2));
		

//		temp_x = v_cr[0];
//		temp_y = v_cr[1];
//		v_cr[0] = temp_x / Math.sqrt( Math.pow(temp_x, 2) + Math.pow(temp_y, 2));
//		v_cr[1] = temp_y / Math.sqrt( Math.pow(temp_x, 2) + Math.pow(temp_y, 2));
		
		//============= compute final v's direction ====================
		double[] v = {0.0, 0.0};
		v[0] = (v_ar[0] + v_sr[0] + v_cr[0]) / 3;
		v[1] = (v_ar[1] + v_sr[1] + v_cr[1]) / 3;
		// normalize v
//		temp_x = v[0];
//		temp_y = v[1];
//		v[0] = temp_x / Math.sqrt( Math.pow(temp_x, 2) + Math.pow(temp_y, 2));
//		v[1] = temp_y / Math.sqrt( Math.pow(temp_x, 2) + Math.pow(temp_y, 2));
		
		double double_v_ds = 0.0, double_v_dd = 0.0;
		for(int i = 0 ; i < vect_neighbors.size(); i++){
			int neighbor_index = vect_entity.indexOf(vect_neighbors.get(i));
			double b_x_similarity = computeSimilarity(entity_b, vect_entity.get(i));
			
			double_v_ds = double_v_ds + b_x_similarity * distanceMatrix[b_index][neighbor_index];
			double_v_dd = double_v_dd + 1 / (b_x_similarity * distanceMatrix[b_index][neighbor_index]);
		}
		double_v_dd = double_v_dd;
//		double double_v, double_v_ar, double_v_sr, double_v_cr;
//		double_v_ar = Math.sqrt(v_ar[0]*v_ar[0] + v_ar[1]*v_ar[1]); 
//		double_v_sr = Math.sqrt(v_sr[0]*v_sr[0] + v_sr[1]*v_sr[1]); 
//		double_v_cr = Math.sqrt(v_cr[0]*v_cr[0] + v_cr[1]*v_cr[1]); 
//		double_v = double_v_ar + double_v_sr + double_v_cr + double_v_ds + double_v_dd;
//		double norm_v = Math.sqrt(v[0]*v[0] + v[1]*v[1]);
		
//		v[0] = double_v * v[0] / norm_v;
//		v[1] = double_v * v[1] / norm_v;
		
		return v;
	}
	
	private void updatePositionAndVelocity(Vector<double[]> vect_updatedVelocity){
		for(int i = 0; i < vect_entity.size(); i++){
			vect_boidPos.get(i)[0] += (int)vect_updatedVelocity.get(i)[0];
			vect_boidPos.get(i)[1] += (int)vect_updatedVelocity.get(i)[1];
			
			vect_boidVelocity.get(i)[0] = vect_updatedVelocity.get(i)[0];
			vect_boidVelocity.get(i)[1] = vect_updatedVelocity.get(i)[1];
		}
	}
	
	public void doClustring(int numOfIteration){

		
		for (int iter = 0; iter < numOfIteration; iter++) {
			Vector<double[]> vect_updatedVelocity = new Vector<double[]>(vect_entity.size());
			//============== Initialize =================
			for (int i = 0; i < vect_entity.size(); i++) {
				vect_updatedVelocity.add(new double[2]);
			}
			
			for (int i = 0; i < vect_entity.size(); i++) {
				Vector<Entity> vect_neighbors = getNeighbors(i);
				double[] v = computeVelocity(vect_entity.get(i), vect_neighbors);
				
				vect_updatedVelocity.set(i, v);
			}
			this.refreshDistanceMatrix();
			updatePositionAndVelocity(vect_updatedVelocity);
			for (int i = 0; i < vect_entity.size(); i++) {
				System.out.print("Iteration:" + iter + ", Boid:" + i 
						+ ", Position(" + vect_boidPos.get(i)[0] + ", " + vect_boidPos.get(i)[1] + ")" 
						+ ", Velocity:(" + vect_boidVelocity.get(i)[0] + ", " + vect_boidVelocity.get(i)[1] + ")" + "\n");
			}
			
			bsCanvas.setDotsAndRepaint(vect_boidPos);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		
		
	}


	public static void main(String[] args) {
		//===== Preprocess (remove stop words, stemming) & Transform docs into TF-IDF matix =======
		String docPath = "C:\\Users\\DC-IT-Dev\\Desktop\\NYU2015FALL-Course-Material\\PA\\homework-2\\articles";
		DocParser docParser = new DocParser(docPath);
		ArrayList<double[]> tfidfMatrix = docParser.tfIdfCalculator();

		// ===== Transform tf-idf matrix into universal data type (entity)
		// ======
		Entity[] my_doc_set = new Entity[tfidfMatrix.size()];
		for (int i = 0; i < tfidfMatrix.size(); i++) {
			double[] temp = new double[tfidfMatrix.get(i).length];
			System.arraycopy(tfidfMatrix.get(i), 0, temp, 0, tfidfMatrix.size());
			Entity temEntity = new Entity(temp);
			my_doc_set[i] = temEntity;
		}
		FlockByLeader fbl = new FlockByLeader(my_doc_set);
		fbl.doClustring(50);
		
	}

}
