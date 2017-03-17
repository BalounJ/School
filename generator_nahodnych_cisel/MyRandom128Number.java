package generator_nahodnych_cisel;
/**
 * Třída pro pokus s hodnotami pro mod 128 v dokumentaci generátor na míru / generátor128 .
 * @author Josef Baloun
 * */
public class MyRandom128Number implements IntGenerator{
	/**
	 *  konstanty pro lineárně kongruentní generátor
	 * */
	private final int a, c, m;
	/**
	 *  proměnná pro lineárně kongruentní generátor
	 * */
	private int x;
	
	/**
	 * konstruktor generatoru nastavi konstanty
	 * */
	public MyRandom128Number() {
		this.a = 13;
		this.c = 63;
		this.m = 128;
		this.x = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);

	}
	

	/**
	 * vrátí pseudo-náhodné číslo
	 * @return pseudo-náhodné číslo
	 * */
	@Override
	public int getRandomInt(){
		x = (a*x + c) % m;		
		return x;
	}
	
}
