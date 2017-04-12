package k_means;

import java.util.ArrayList;
import java.util.List;

public class Kmeans {

	List<Point> points;
	List<Point> centers;
	
	public Kmeans(List<Point> points, List<Point> centers) {
		this.points = points;
		this.centers = centers;
	}
	
	protected List<List<Point>> grouping(List<Point> points, List<Point> centers) {
		List<List<Point>> groups = new ArrayList<>();
		
		for (Point center : centers) {
			groups.add(new ArrayList<Point>());
		}
		
		for (Point point : points) {
			int n = nearest(centers, point);			
			groups.get(n).add(point);
		}
		
		return groups;
	}
	
	protected List<Point> centering(List<List<Point>> groups) {
		List<Point> centers = new ArrayList<>();
		
		int i = -1;
		for (List<Point> group : groups) {
			
			i++;
			
			if(group.size() == 0) {
				centers.add(this.centers.get(i));
				continue;
			}
			
			double sumX = 0;
			double sumY = 0;
			
			for (Point p : group) {
				sumX += p.getX();
				sumY += p.getY();
			}
			
			double cX = sumX / group.size();
			double cY = sumY / group.size();
			
			centers.add(new Point(cX, cY));
			
			
		}
		
		
		return centers;
	}
	
	protected int nearest(List<Point> centers, Point p) {
		int index = 0;
		double minDist = p.distance(centers.get(0));
		
		for (int i = 1; i < centers.size(); i++) {
			if(minDist > p.distance(centers.get(i))) {
				minDist = p.distance(centers.get(i));
				index = i;
			}
		}
		
		return index;
	}
	
	public List<List<Point>> getGroups() {
		List<List<Point>> groups = null;
		List<Point> lastCenters = null;
		
		
		do {
			lastCenters = centers;
			groups = grouping(points, centers);
			centers = centering(groups);
		} while (!lastCenters.equals(centers));
		
		
	/*	while (!lastCenters.equals(centers)) {
			lastCenters = centers;
			groups = grouping(points, centers);
			centers = centering(groups);
		}*/
		
		return groups;
	}
	
	
}
