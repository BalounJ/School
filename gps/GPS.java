package gps;

import java.util.PriorityQueue;

public class GPS {
	
	public static int sous[][] = {
			/*S*/		{0,36,11,-1,-1},
			/*A*/		{36,0,-1,13,43},
			/*B*/		{11,-1,0,31,-1},
			/*C*/		{-1,13,31,0,19},
			/*G*/		{-1,43,-1,19,0}			
	};
	public static int vzd[] = {
			/*S*/		47,
			/*A*/		26,
			/*B*/		47,
			/*C*/		13,
			/*G*/		0		
	};
	
	public static String names[] = {"S","A","B","C","G"};
	
	public static Pom solve(int sous[][], int vzd[]){
	
		PriorityQueue<Pom> pq = new PriorityQueue<>();
		pq.add(new Pom(0, 0, 0, null));
		
		while(!pq.isEmpty()) {
			Pom p = pq.poll();
			
			for (int i = 0; i < vzd.length; i++) {
				if(sous[p.index][i]>0){
					Pom pom = new Pom(i, p.dist + sous[p.index][i], vzd[i], p);
					if(vzd[i]==0)
						return pom;
					else
						pq.add(pom);
				}
			}
		}
		return null;
	}
	
	
	public static void print(Pom res){
		if(res == null)
			return;
		
		print(res.parent);
		System.out.println(names[res.index]);
	}
	
	
	public static void main(String[] args) {
		
		Pom res = solve(sous, vzd);
		
		print(res);
		System.out.println("distance: "+res.dist);
	}

}
