package generator_nahodnych_cisel;
/**
 * Třída pro lineárně kongruentní generátor v dokumentaci vylepšený generátor.
 * @author Josef Baloun
 * */
public class ImprovedGenerator implements IntGenerator{
	/**
	 * pole generátorů mezi kterými se přepíná
	 * */
	private IntGenerator[] gen;
	/**
	 * počet generátorů
	 * */
	private final int GEN_COUNT = 3;
	/**
	 * poslední náhodné číslo
	 */
	private int lastX;

	/**
	 * konstruktor generatoru nastavi konstanty svým subgenerátorům
	 * @param mod pro dělení modulo rozsah <0;mod)
	 * */
	public ImprovedGenerator(int mod) {
		
		gen = new IntGenerator[GEN_COUNT];
		
		//konstanty z wiki viz dokumentace
		
		gen[0] = new SubGenerator(1664525, 1013904223, (long) Math.pow(2, 32), System.currentTimeMillis(), mod);
		gen[1] = new SubGenerator(33797, 1, (long) Math.pow(2, 32), System.currentTimeMillis(), mod);
		gen[2] = new SubGenerator(1140671485 , 12820163 , (long) Math.pow(2, 24), System.currentTimeMillis(), mod);
		
		
		lastX = (int) System.currentTimeMillis();
		
	}

	/**
	 * vrátí pseudo-náhodné číslo
	 * @return pseudo-náhodné číslo
	 * */
	@Override
	public int getRandomInt() {
		
		lastX = gen[Math.abs(lastX) % GEN_COUNT].getRandomInt();
		
		return lastX;
	}

	
	
	
}
