package trie_vyskyt_slov;
import java.text.Normalizer;

/**
 * Knihovní třída.
 * @author Josef Baloun, Petra Štumpfová
 * @version 1.4
 * */
public class Lib {
	
	/**	Počet prvků v používané abecedě.
	 * */
	public static final int VELIKOST_ABECEDY=26;
	/** Titulek okna aplikace.
	 * */
	public static final String TITLE="Asi slovník";
	/** Šířka tlačítek.
	 * */
	public static final int BUTTON_WIDTH=100;
	
	/** Řetězec pro prázdný vrchol pro ukládání a načítání.
	 * */
	public static final String VRCHOL_NULL="null";
	
	/** Cesta k souboru pro výpis hledání.
	 * */
	public static final String CESTA_VYPIS="vysledek_hledani.txt";
	
	/**	Počet prvků v používané abecedě.
	 * */
	public static final int MAX_PODOBNYCH=10;
	
	
	
	
	/**	Najde minimum.
	 * 
	 * 	@param x První číslo pro porovnání.
	 * 	@param y Druhý číslo pro porovnání.
	 *  @param z Třetí číslo pro porovnání.
	 * 	@return Nejmenší ze zadaných.
	 * */
	public static int min3(int x, int y, int z){
		return Math.min(x, Math.min(y, z));
	}
	
	
	/**	Výpočet Levensteinovy vzdálenosti.
	 * 
	 * 	@param a První řetězec pro porovnání.
	 * 	@param b Druhý řetězec pro porovnání.
	 * 	@return Levensteinova vydálenost.
	 * */
	public static int levVzdal(String a, String b){
		String xs = a.toLowerCase();
		String ys = b.toLowerCase();
		int tmp=0;

		int[][] tab = new int[xs.length()+1][ys.length()+1];
		
		for(int i = 0; i <= xs.length(); i++){
			tab[i][0] = i;
		}
		for(int i = 0; i <= ys.length(); i++){
			tab[0][i] = i;
		}
		
		
		for(int y = 1; y <= ys.length(); y++){
			for(int x = 1; x <= xs.length(); x++){
				
				if(xs.charAt(x-1) == ys.charAt(y-1)){
					tmp = 0;
				}
				else{
					tmp = 1;
				}
				
				tab[x][y] = min3(tab[x-1][y]+1, tab[x][y-1]+1, tab[x-1][y-1] + tmp);
			}
		}
		
		return tab[xs.length()][ys.length()];
	}
	
	/**	Normalizuje vstupní slovo. 
	 * <br>V případě, že je více slov (oddělené mezerou ' '), vezme první z nich.
	 * <br>Ve slově nahradí znaky s diakritikou na znaky bez diakritiky a převede na malá písmena.
	 * 	
	 * @param str Řetězec, který chceme normalizovat.
	 * @return Normalizované slovo, skládající se pouze ze znaků a-z.
	 * */
	public static String normalizujSlovo(String str){
		String s=str.split(" ")[0];
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[^\\p{ASCII}]", "").toLowerCase().replaceAll("[^a-z]", "");
		
		return s;
	}

	/**	Připraví vstupní řetězec pro následné zpracování. 
	 * 
	 * @param str Řetězec, který chceme připravit.
	 * @return Řetězec obsahující slova oddělená mezerami.
	 * */
	public static String pripravText(String str){
		String s = Normalizer.normalize(str, Normalizer.Form.NFD);
		s = s.replaceAll("[^\\p{ASCII}]", "");
		s = s.replaceAll("[^A-Za-z ]", " ");
		
		return s;
	}
	
}
