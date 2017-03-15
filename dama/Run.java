package dama;

import java.util.ArrayList;
import java.util.Arrays;

public class Run {
	
	static ArrayList<Pole> res;
	
	
	private static void solve(Pole p, int line){
		
		if(line >= p.getPole().length) {
			res.add(p);
			return;
		}
		
		for (int i = 0; i < p.getPole().length; i++) {
			if(p.getPole()[line][i]==0){
				byte pole[][] = p.clonePole();
				pole[line][i] = 1;
				Pole x = new Pole(pole);
				x.blockFor(line, i);
				solve(x, line+1);
			}
		}
	}
	

	public static void main(String[] args) {
		byte pole[][] = new byte[4][4];
		
		for (int i = 0; i < pole.length; i++) {
			Arrays.fill(pole[i], (byte)0);
		}
		Pole p = new Pole(pole);
		
		res = new ArrayList<>();
		
		solve(p, 0);
		
		
		for (Pole r : res) {
			r.vypis();
		}
		
		
	}
}
