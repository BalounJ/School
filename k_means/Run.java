package k_means;

import java.util.Arrays;
import java.util.List;


public class Run {
	
	public static void main(String[] args) {
		
	/*	{2, 4, 10, 12, 3, 20, 30, 11, 25}, 
	 * M2 = {2, 4, 15, 18, 5, 50, 30, 34, 65}	*/
		
		List<Point> points = Arrays.asList(
				new Point(2, 0),
				new Point(4, 0),
				new Point(10, 0),
				new Point(12, 0),
				new Point(3, 0),
				new Point(20, 0),
				new Point(30, 0),
				new Point(11, 0),
				new Point(25, 0)
				
				
				);
		List<Point> centers = Arrays.asList(
				//new Point(0, 0),
				new Point(2, 0),
				new Point(4, 0)
				);
		
		Kmeans kmeans = new Kmeans(points, centers);
		
		Kmedoids kmedoids = new Kmedoids(points, centers);
		
		List<List<Point>> gl1 = kmeans.getGroups();
		
		print(gl1);
		
		System.out.println("centers: "+kmeans.centers.toString());
		
		System.out.println("****************************");
		
		List<List<Point>> gl2 = kmedoids.getGroups();
		
		print(gl2);
		System.out.println("centers: "+kmedoids.centers.toString());
	}

	private static void print(List<List<Point>> gl) {
		int i = 1;
		for (List<Point> list : gl) {
			System.out.println("Group "+(i++)+": "+ list.toString());
		}
	}
}
