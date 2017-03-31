package geneticke_algoritmy.dama;



class Pole implements Comparable<Pole> {
	boolean pole[][];
	int ohodnoceni;
	
	public Pole(boolean[][] pole, int ohodnoceni) {
		this.pole = pole;
		this.ohodnoceni = ohodnoceni;
	}
	
	public boolean[][] getPole() {
		return pole;
	}
	
	public boolean[][] clonePole() {
		boolean rtn[][] = new boolean[pole.length][pole.length];
		
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
				if(pole[i][j])
					System.out.print("# ");
				else
					System.out.print("- ");
			}
			System.out.println();
		}
		System.out.println("***********");
	}
	
	@Override
	public int compareTo(Pole o) {
		// TODO Auto-generated method stub
		
		return ohodnoceni - o.ohodnoceni;
	}

	@Override
	public String toString() {
		return "Pole [ohodnoceni=" + ohodnoceni + "]";
	}
		
}