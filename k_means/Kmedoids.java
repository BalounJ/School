package k_means;

import java.util.ArrayList;
import java.util.List;

public class Kmedoids extends Kmeans{

	public Kmedoids(List<Point> points, List<Point> centers) {
		super(points, centers);
		// TODO Auto-generated constructor stub
	}

	private double countCost(Point p, List<Point> group) {
		double sumDist = 0;
		
		for (Point point : group) {
			sumDist += p.distance(point);
		}
		
		return sumDist;
	}
	
	@Override
	protected List<Point> centering(List<List<Point>> groups) {
		//List<Point> centers = super.centering(groups);
		List<Point> medoids = new ArrayList<>();
				
		for (int i = 0; i < groups.size(); i++) {
			
			if(groups.get(i).size() == 0) {
				medoids.add(centers.get(i));
				continue;
			}
			
			List<Point> group = groups.get(i);
			
			int minIndex = 0;
			double minDist = countCost(group.get(0), group);
			
			for (int j = 1; j < group.size(); j++) {
				if (minDist > countCost(group.get(j), group)) {
					minDist = countCost(group.get(j), group);
					minIndex = j;
				}
			}
			
			medoids.add(group.get(minIndex));
			
		}
		
		return medoids;
	}
}
