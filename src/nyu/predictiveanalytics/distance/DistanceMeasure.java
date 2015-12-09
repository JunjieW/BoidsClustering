package nyu.predictiveanalytics.distance;

import nyu.predictiveanalytics.Entity;

public interface DistanceMeasure {
	public double computeDistance(Entity entity_x, Entity entity_y);
	
}
