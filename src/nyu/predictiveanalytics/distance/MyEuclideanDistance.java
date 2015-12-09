package nyu.predictiveanalytics.distance;

import nyu.predictiveanalytics.Entity;

public class MyEuclideanDistance implements DistanceMeasure {

	public MyEuclideanDistance() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public double computeDistance(Entity entity_x, Entity entity_y) {
    	if( entity_x.m_featureDimension != entity_y.m_featureDimension)
    		throw new RuntimeException("[MyEuclideanDistance:computeDistance()] Entity_x and entity_y Should have same diemensions");

    	double sum = 0;
    	for (int i = 0; i < entity_x.m_featureDimension; i++) {
    		//ignore missing values
    		if (!Double.isNaN(entity_x.m_features[i]) && !Double.isNaN(entity_y.m_features[i]))
    			sum += Math.pow(entity_y.m_features[i] - entity_x.m_features[i], 2);
    	}
    	return Math.sqrt(sum);
	}

}
