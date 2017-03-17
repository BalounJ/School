package NKA_na_DKA.AUTOMAT;
import java.util.ArrayList;

/**Třída pro reprezentaci pravidla, odpovídá jedné řádce tabulky.
 * */
public class Pravidlo implements Comparable<Pravidlo> {
	
	protected ArrayList<Mnozina> sloupce;
	private boolean vstupni, koncovy;
	
	/**pravidlo pro tab. e-nasledniku
	 * */
	protected Pravidlo(Mnozina nazev, Mnozina eNasl){
		this.vstupni = false;
		this.koncovy = false;

		this.sloupce=new ArrayList<Mnozina>(2);
		sloupce.add(nazev);
		sloupce.add(eNasl);
		
	}
	/**pravidlo pro tab. determin. prav. vytvarenych v {@code class Automat}
	 * */
	protected Pravidlo(ArrayList<Mnozina> sloupce, boolean vstupni, boolean koncovy) {
		this.sloupce = sloupce;
		this.vstupni = vstupni;
		this.koncovy = koncovy;
	}



	/**Vytvoří pravidlo (jedna řádka tabulky).
	 * @param sloupcePravidla 	Pole sloupců pravidla. Pro jeden Automat musí být všechny stejně dlouhé({@code sloupcePravidla.length}).
	 * 							První sloupec brán jako název vrcholu ({@code sloupcePravidla[0]}), poslední sloupec jako e-hrana.
	 * @param vstupni	Zda je vrchol ({@code sloupcePravidla[0]}) vstupní.
	 * @param vystupni	Zda je vrchol ({@code sloupcePravidla[0]}) výstupní.
	 * */
	public Pravidlo(String[] sloupcePravidla, boolean vstupni, boolean koncovy) {
		this.vstupni = vstupni;
		this.koncovy = koncovy;
		
		this.sloupce=new ArrayList<Mnozina>(sloupcePravidla.length);
		String[] str;
		for (String d : sloupcePravidla) {
			if(d!=null && !d.equals("")){
				d=d.replaceAll(" ", "");
				str=d.split(",");
			
				Mnozina sls=new Mnozina();
				for (String s : str) {
					sls.add(s);
				}
				this.sloupce.add(sls);		
			}
			else{
				this.sloupce.add(new Mnozina());
			}
		}
	}
	
	/*protected ArrayList<Mnozina> getMnoziny() {
		return sloupce;
	}*/
	/*
	protected boolean jePrvni(Mnozina m) {
		return sloupce.get(0).equals(m);
	}*/
	
	protected Mnozina getPrvni() {
		return sloupce.get(0);
	}
	
	protected Mnozina getEHrana() {
		return sloupce.get(sloupce.size()-1);
	}

	/**	Vrátí sloupce v poli Stringů. Sloupce jsou seřazené přirozeným řazením.
	 * @return	 Sloupce v poli Stringů. Sloupce jsou seřazené přirozeným řazením.
	 * */
	public String[] getSloupce() {
		String[] rtn = new String[sloupce.size()];
		int i=0;
		for (Mnozina sl : sloupce) {
			if(sl.size()==0){
				rtn[i]="{}";
			}
			else {
				rtn[i]="{";
				for (String string : sl) {
					rtn[i]+=string+", ";
					
				}
				rtn[i]=rtn[i].substring(0, rtn[i].length()-2);
				rtn[i]+="}";
			}
			i++;
		}		
		return rtn;
	}
	/**	Vrátí zda je vrchol ({@code sloupcePravidla[0]}) vstupní.
	 * @return	 Zda je vrchol ({@code sloupcePravidla[0]}) vstupní.
	 * */
	public boolean isVstupni() {
		return vstupni;
	}
	/**	Vrátí zda je vrchol ({@code sloupcePravidla[0]}) výstupní.
	 * @return	 Zda je vrchol ({@code sloupcePravidla[0]}) výstupní.
	 * */
	public boolean isKoncovy() {
		return koncovy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sloupce == null) ? 0 : sloupce.get(0).hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pravidlo other = (Pravidlo) obj;
		if (sloupce == null) {
			if (other.sloupce != null)
				return false;
		} else if (!sloupce.get(0).equals(other.sloupce.get(0)))
			return false;
		return true;
	}

	@Override
	public int compareTo(Pravidlo p) {
		return this.sloupce.get(0).compareTo(p.sloupce.get(0));
	}

}
