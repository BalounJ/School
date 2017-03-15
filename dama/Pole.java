package dama;

import java.util.Arrays;

public class Pole {
	byte pole[][];
	
	public Pole(byte[][] pole) {
		this.pole = pole;
	}
	
	public byte[][] getPole() {
		return pole;
	}
	
	public byte[][] clonePole() {
		byte rtn[][] = new byte[pole.length][pole.length];
		
		for (int i = 0; i < pole.length; i++) {
			for (int j = 0; j < pole.length; j++) {
				rtn[i][j] = pole[i][j];
			}
		}
		return rtn;
	}

	void vypis()
	{
		for (int i = 0; i < pole.length; i++) {
			for (int j = 0; j < pole.length; j++) {
				if(pole[i][j] == 1)
					System.out.print("# ");
				else
					System.out.print("- ");
			}
			System.out.println();
		}
		System.out.println("***********");
	}
	
	void blockFor(int line, int i){
		
		for (int j = 0; j < pole.length; j++) {
			if(pole[line][j] == 0)
				pole[line][j] = -1;
		}
		for (int j = 0; j < pole.length; j++) {
			if(pole[j][i] == 0)
				pole[j][i] = -1;
		}
		
		int xl = line;
		int xr = i;
		
		while(xl >= 0 && xr >= 0) {
			if(pole[xl][xr] == 0)
				pole[xl][xr] = -1;
			
			xl--;
			xr--;
		}
		
		xl = line;
		xr = i;
		
		while(xl < pole.length && xr < pole.length) {
			if(pole[xl][xr] == 0)
				pole[xl][xr] = -1;
			
			xl++;
			xr++;
		}
		
		xl = line;
		xr = i;
		
		while(xl < pole.length && xr >= 0) {
			if(pole[xl][xr] == 0)
				pole[xl][xr] = -1;
			
			xl++;
			xr--;
		}
		
		xl = line;
		xr = i;
		
		while(xl >= 0 && xr < pole.length) {
			if(pole[xl][xr] == 0)
				pole[xl][xr] = -1;
			
			xl--;
			xr++;
		}
		
		
	}
	
	
	
			
}
