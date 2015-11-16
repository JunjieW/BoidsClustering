package nyu.predictiveanalytics;

public class MyCosineDistance implements DistanceMeasure {

	public MyCosineDistance() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public double computeDistance(Entity entity_x, Entity entity_y) {
		if (entity_x.m_featureDimension != entity_y.m_featureDimension) {
			throw new RuntimeException("Both instances should contain the same number of values.");
		}
		return 1 - computeSimilarity(entity_x, entity_y);
	}
	
	public double computeSimilarity(Entity entity_x, Entity entity_y){
		double dot_product = 0;
		double normsqure_x = 0;
		double normsqure_y = 0;
		for (int i = 0; i < entity_x.m_featureDimension; i++) {
			dot_product += entity_x.m_features[i] * entity_y.m_features[i];
			normsqure_x += Math.pow(entity_x.m_features[i], 2);
			normsqure_y += Math.pow(entity_y.m_features[i], 2);
		}
		double cosSim = dot_product / (Math.sqrt(normsqure_x) * Math.sqrt(normsqure_y));
		// handle rounding errors
		if (cosSim < 0)
			cosSim = 0;
		return cosSim;
	}

}
