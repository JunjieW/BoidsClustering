package nyu.predictiveanalytics;

public class Entity {
	
	int m_featureDimension;
	//int[] m_features;
	double[] m_features;
	
	
	public Entity(int dimension){
		m_featureDimension = dimension;
	}
	
	public Entity(double[] entity_features){
		m_features = entity_features;
		m_featureDimension = entity_features.length;
	}
	
	@Override
	public String toString() {
		String str = "[";
		for (int i = 0; i < m_featureDimension; i++)
			str += m_features[i] + ",";
		str += "]";
		return str;
	}
	
}
