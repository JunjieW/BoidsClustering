package nyu.predictiveanalytics;

public class Entity {
	
	int m_featureDimension;
	//int[] m_features;
	float[] m_features;
	
	
	public Entity(int dimension){
		m_featureDimension = dimension;
	}
	
	public Entity(float[] entity_features){
		m_features = entity_features;
		m_featureDimension = entity_features.length;
	}
	
}
