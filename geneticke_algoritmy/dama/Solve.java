package geneticke_algoritmy.dama;

import java.util.Arrays;
import java.util.Random;

class Solve {
	final int size;
	final int population;
	Pole[] pop;
	Random rn;
	
	public Solve(int size, int population) {
		this.size = size;
		this.population = population;
		//rn = new Random(1293456);
		rn = new Random();
		
		pop = new Pole[population];
		
		pocatecniPopulace();
	}
	
	public void pocatecniPopulace() {
		for (int i = 0; i < population; i++) {
			boolean[][] pole = new boolean[size][size];
			
			for (int j = 0; j < size; j++) {
				pole[j][rn.nextInt(size)] = true;
			}
			
			pop[i] = new Pole(pole, ohodnot(pole));
		}
		Arrays.sort(pop);
	}
	
	public Pole findResult() {
		Pole p = pop[0];
		
		int i = 0;
		while(pop[0].ohodnoceni != 0) {
			pridejPole(mutujPole(p.clonePole()));
			//System.out.println(i + " " + Arrays.toString(pop));
			
			if(i > 2000 && pop[0].ohodnoceni != 0) {
				pocatecniPopulace();
				i=0;
			}
			
			p = pop[rn.nextInt(pop.length/4 +1)];
			i++;
		}
		
		
		return pop[0];
	}
	
	
	
	
	
	private void pridejPole(boolean[][] pole) {
		pop[pop.length -1] = new Pole(pole, ohodnot(pole));
		Arrays.sort(pop);
	}
	
	private boolean[][] mutujPole(boolean[][] pole) {
		
		int pocet = rn.nextInt(pole.length-1)+1;
		
		for (int x = 0; x < pocet; x++) {
			int i = rn.nextInt(pole.length);
		
			for (int j = 0; j < pole.length; j++) {
				if(pole[i][j]) {
					pole[i][j]=false;
					pole[i][rn.nextInt(size)] = true;
					return pole;
				}
			}
		}
		return pole;
	}
	
	private int ohodnot(boolean[][] pole) {
		int coll = 0;
		
		for (int i = 0; i < pole.length; i++) {
			int count = -1; 
			for (int j = 0; j < pole.length; j++) {
				if(pole[i][j])
					count++;
			}
			if(count>0)
				coll += count*count;
		}
		
		for (int i = 0; i < pole.length; i++) {
			int count = -1; 
			for (int j = 0; j < pole.length; j++) {
				if(pole[j][i])
					count++;
			}
			if(count>0)
				coll += count*count;
		}
		
		for (int i = 0; i < pole.length; i++) { // /
			int j = i;
			int k = 0;
			int count1 = -1; 
			int count2 = -1;
			
			while (j >= 0) {
				if(pole[j][k])
					count1++;
				
				if(pole[pole.length - 1 - j][pole.length - 1 - k])
					count2++;
		
				j--;
				k++;
			}
				
			
			if(count1 > 0)
				coll += count1*count1;
			
			if(count2 > 0 && i != pole.length - 1)
				coll += count2*count2;
		}
		
		
		
		for (int i = 0; i < pole.length; i++) { // \
			int j = i;
			int k = 0;
			int count1 = -1; 
			int count2 = -1;
			
			while (j < pole.length ) {
				if(pole[j][k])
					count1++;
				
				if(pole[pole.length - 1 - j][pole.length - 1 - k])
					count2++;
		
				j++;
				k++;
			}
				
			if(count1 > 0)
				coll += count1*count1;
			
			if(count2 > 0 && i != 0)
				coll += count2*count2;
		}
		
		return coll;
	}
	
	public void test() {
		boolean[][] pole = new boolean[][]{
			{true,	false,	false,	false},
			{true,	false,	false,	false},
			{true,	false,	false,	false},
			{true,	false,	false,	false}			
		};
		
		System.out.println(ohodnot(pole));
		
		pole = new boolean[][]{
			{true,	false,	false,	false},
			{false,	true,	false,	false},
			{false,	false,	true,	false},
			{false,	false,	false,	true}			
		};
		
		System.out.println(ohodnot(pole));
		
		pole = new boolean[][]{
			{false,	false,	false,	true},
			{false,	false,	true,	false},
			{false,	true,	false,	false},
			{true,	false,	false,	false}			
		};
		
		System.out.println(ohodnot(pole));
		
		pole = new boolean[][]{
			{true,	true,	true,	true},
			{false,	false,	false,	false},
			{false,	false,	false,	false},
			{false,	false,	false,	false}			
		};
		
		System.out.println(ohodnot(pole));
		
		pole = new boolean[][]{
			{false,	true,	false,	false},
			{false,	false,	false,	true},
			{true,	false,	false,	false},
			{false,	false,	true,	false}			
		};
		
		System.out.println(ohodnot(pole));
	}
	
	
}
