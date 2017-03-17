package generator_nahodnych_cisel;
/**
 * Třída pro lineárně kongruentní generátor v dokumentaci sub generátor vylepšeného generátoru.
 * @author Josef Baloun
 * */
public class SubGenerator implements IntGenerator {
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
	 * konstanta pro reinicializaci
	 * */
	private final int iterCountStart;
	/**
	 * počet iterací
	 * */
	private int iterCount;
	
	/**
	 * konstruktor generatoru nastavi konstanty
	 * @param a konstanta vzorce
	 * @param c konstanta vzorce
	 * @param m konstanta vzorce
	 * @param x startovací bod
	 * @param mod pro dělení modulo rozsah <0;mod)
	 * */
	public SubGenerator(long a, long c, long m, long x, int mod) {
		this.a = a;
		this.c = c;
		this.m = m;
		this.x = x;
		this.mod = mod;
		
		iterCountStart = mod/10;	//zajistí reinicializaci po cca 9/10 možných iterací
		
		this.iterCount = iterCountStart;
	}


	/**
	 * vrátí pseudo-náhodné číslo
	 * @return pseudo-náhodné číslo
	 * */
	@Override
	public int getRandomInt(){
		iterCount++;
		if(iterCount > mod){
			iterCount = iterCountStart;
			x = System.nanoTime();
		}
		
		//System.out.println("sub> "+x);	
		/*někdy u gen[2] = new SubGenerator(1140671485 , 12820163 , (long) Math.pow(2, 24), System.currentTimeMillis(), mod) 
		 * vrátí záporná čísla, problém se objevil až po dokončení a restartu proto jsem to ponechal pouze 
		 * ošetřené Math.abs
		*/
		x = Math.abs((a*x + c) % m);
		//System.out.println("  "+x);		
		return (int) (x%mod);
	}
}
