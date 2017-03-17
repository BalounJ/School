package generator_nahodnych_cisel;
/**
 * Třída pro lineárně kongruentní generátor v dokumentaci Optimální generátor.
 * @author Josef Baloun
 * */
public class RandomNumberConst implements IntGenerator{
	/**
	 *  konstanty pro lineárně kongruentní generátor
	 * */
	private final long a, c, m;
	/**
	 *  proměnná pro lineárně kongruentní generátor
	 * */
	private long x;
	/**
	 * pro dělení modulo rozsah <0;mod)
	 * */
	private final int mod;
	
	/**
	 * konstruktor generatoru nastavi konstanty
	 * @param mod pro dělení modulo rozsah <0;mod)
	 * */
	public RandomNumberConst(int mod) {
		this.a = 1664525;
		this.c = 1013904223;
		this.m = (long) Math.pow(2, 32);
		this.x = System.currentTimeMillis();
		this.mod = mod;
	}
	
	/**
	 * vrátí pseudo-náhodné číslo rozsah <0;mod)
	 * @return pseudo-náhodné číslo rozsah <0;mod)
	 * */
	@Override
	public int getRandomInt(){
		x = (a*x + c) % m;
				
		return (int) (x%mod);
	}
}
