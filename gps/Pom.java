package gps;

public class Pom implements Comparable<Pom>{
	int index;
	int dist;
	int heur;
	Pom parent;
	
	
	public Pom(int index, int dist, int heur, Pom parent) {
		this.index = index;
		this.dist = dist;
		this.heur = heur;
		this.parent = parent;
	}
	

	
	@Override
	public int compareTo(Pom o) {
		// TODO Auto-generated method stub
		/*
If the Integer is equal to the argument then 0 is returned.
If the Integer is less than the argument then -1 is returned.
If the Integer is greater than the argument then 1 is returned.*/
		
		int d1 = dist + heur;
		int d2 = o.dist + o.heur;
		
		return d1 - d2;
	
	}
	
	
}
