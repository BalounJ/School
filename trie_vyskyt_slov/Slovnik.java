package trie_vyskyt_slov;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
/**
 * Instance třídy {@code Slovnik} tvoří stromovou datovou strukturu pro uchování informace o slovech.
 * @author Josef Baloun, Petra Štumpfová
 * @version 1.3
 * */
public class Slovnik implements Serializable {
	
	/** Serial version UID pro serializaci objektu.
	 */
	private static final long serialVersionUID = 2L;
	/** kořen datové struktury 
	 * */
	private final Vrchol koren;
	/** proměná pro rekurzy obsahující slova ve slovníku
	 * */
	private String slova;
	
	/** Set pro slova přidaná uživatelem.
	 * */
	private final Set<String> pridano;
	
	/** Set pro slova ve slovníku.
	 * */
	private final Set<String> slovaSlovniku;
	
	/** Konstruktor vytvoří kořen.
	 * @param input Řetězec, kterým chceme naplnit slovník. Je oddělen mezeramy do slov a slova projdou normalizací {@code normalizujSlovo(String s)}.
	 * */
	public Slovnik(String input) {
		koren = new Vrchol(null, "");
		pridano = new TreeSet<String>();
		slovaSlovniku = new TreeSet<String>();
		
		String vstup = Lib.pripravText(input);
		
		String[] slova=vstup.split(" ");
		int index=0;
		for (int i = 0; i < slova.length; i++) {
			String slovo = Lib.normalizujSlovo(slova[i]);
			pridejSlovo(slovo, index);
			index+=slova[i].length()+1;
		}
		
		
		komprimujSlovnik();
	}
	
	/**	Zkomprimuje vrchol. Metoda pro rekurzy.
	 * 	
	 * */
	private void komprimujVrchol(Vrchol v){
		if(v == null){
			return;
		}
			
		if(v.getPocetChilds() != 1 || v.getPrvni() != null){//projdou jen vrcholy s jednim naslednikem a bez vyskytu v textu
			for (int i = 0; i < Lib.VELIKOST_ABECEDY; i++) {
				komprimujVrchol(v.getChild(i));
			}
			return;
		}
		
		//spojeni s jedinym naslednikem
		Vrchol d = v.getPrvniChild();
		
		v.setText(v.getText() + d.getText());
		v.setPrvni(d.getPrvni());
		
		for (int j = 0; j < Lib.VELIKOST_ABECEDY; j++) {
			v.setChild(j, d.getChild(j));
		}
		
		komprimujVrchol(v);
		
		
	}
	
	/**	Zkomprimuje slovník do komprimované trie.
	 * 	
	 * */
	private void komprimujSlovnik(){
		for (int i = 0; i < Lib.VELIKOST_ABECEDY; i++) {
			komprimujVrchol(koren.getChild(i));
		}
		
	}
	
	
	/**	Přidá slovo do slovníku.
	 * 	
	 * @param s Slovo, které chceme přidat.
	 * @param startIndex Počáteční index slova, které chceme přidat.
	 * @return Zda bylo slovo přidáno.
	 * */
	private boolean pridejSlovo(String s, int startIndex){
		String slovo = Lib.normalizujSlovo(s);
		
		if(slovo.length()==0){
			return false;
		}
		
		Vrchol vrchAktual=koren;
		
		for (int i = 0; i < slovo.length(); i++) {
			char c=slovo.charAt(i);
			if(vrchAktual.getChild(c)==null){
				vrchAktual.setChild(c, c+"");
			}
			vrchAktual=vrchAktual.getChild(c);
			
			
		}
		
		vrchAktual.incrementPocetVyskytu(startIndex);
		
		slovaSlovniku.add(slovo);
		
		return true;
	}
	
	/**	Přidá slovo do Setu pro slova pridana uzivatelem.
	 * 	
	 * @param s Slovo, které chceme přidat.
	 * @return Zda bylo slovo přidáno.
	 * */
	public boolean pridejSlovoUser(String s){
		String slovo = Lib.normalizujSlovo(s);
		
		if(slovo.length()==0){
			return false;
		}
		
		pridano.add(s);
		
		return true;
	}
	
	/**	Vrátí vrchol datové struktury podle hledaného slova.
	 * 	
	 * @param slovo Slovo, které má vést k vrcholu.
	 * @return Nalezený vrchol nebo {@code null}.
	 * */	
	private Vrchol getVrchol(String slovo){
		String vs = Lib.normalizujSlovo(slovo);
		//Vrchol v = koren;
	//uprava pro komprimovanou
		Vrchol tmp = koren;
		int vi=0;
		int tmpi=0;
		while(true){
			tmp = tmp.getChild(vs.charAt(vi));
			
			if (tmp == null) {
				// neni zde hledane slovo
				return null;
			}
			
			String tmps = tmp.getText();
			
			for (tmpi = 0; tmpi < tmps.length() && vi < vs.length(); tmpi++) {
				if(vs.charAt(vi) != tmps.charAt(tmpi)){
					return null;
				}	
				vi++;
			}
			
			//syso tmpi vi
			
			if(vs.length() - vi == 0){ 
				if(tmps.length() - tmpi == 0){	//shoda
					return tmp;
				}
				else{	//hledane hotovo ale na vrcholu jeste zbyva
					return null;
				}
			}
			//jinak pokracujeme dale
			
			
			
		}
		
		
		
	}
	
	/*	Vrátí vrchol datové struktury podle hledaného slova.
	 * 	
	 * @param slovo Slovo, které má vést k vrcholu.
	 * @return Nalezený vrchol nebo {@code null}.
	 * */	
	/*@Deprecated
	private Vrchol getVrcholNekompr(String slovo){
		String s = Lib.normalizujSlovo(slovo);
		Vrchol v = koren;

		for (int i = 0; i < s.length(); i++) {
			Vrchol child = v.getChild(s.charAt(i));
			if (child == null) {
				// neni zde hledane slovo
				return null;
			}
			
			
			
			v = child;
		}
		
		return v;
	}*/
	
	/**	Vyhledá zda je slovo ve slovníku.
	 * 	
	 * @param s Slovo, které hledáme.
	 * @return Pole {@code int[]} s počátečními indexy výskytů v textu. Pokud nenalezeno vrátí {@code null}.
	 * */
	private int[] hledejSlovoVeSlovniku(String s){
		String slovo = Lib.normalizujSlovo(s);
		
		Vrchol v=getVrchol(slovo);
		
		if(v==null){
			return null;
		}
		
		int pocetVyskytu=v.getPocetVyskytu();
		
		if(pocetVyskytu==0){
			return null;
		}
		
		int[] vyskyty=new int[pocetVyskytu];
		
		Vyskyt vyskyt=v.getPrvni();
		
		for (int j = 0; j < vyskyty.length; j++) {
			vyskyty[j]=vyskyt.getIndex();
			vyskyt=vyskyt.dalsi;
		}
		
		return vyskyty;
	}
	
	/**	Vyhledá zda je slovo obsaženo.
	 * 	
	 * @param s Slovo, které hledáme.
	 * @return Pole {@code int[]} s počátečními indexy výskytů v textu (-1 - přidáno uživatelem). Pokud nenalezeno vrátí {@code null}.
	 * */
	public int[] hledejSlovo(String s){		
		int[] vyskyty = hledejSlovoVeSlovniku(s);
		
		if(vyskyty == null && pridano.contains(s)){
			vyskyty = new int[1];
			vyskyty[0] = -1;		
		}
		
		return vyskyty;
	}
	
	
	
	
	
	/**	Vyhledá podobná slova.
	 * 	
	 * @param hledane Slovo, ke kterému hledáme podobná.
	 * @return Formátovaný řetězec slov.
	 * */
	public String podobnaSlova(String hledane){
		
		TreeMap<String, Integer> m = new TreeMap<String, Integer>();
		for (String s : slovaSlovniku) {
			m.put(s, Lib.levVzdal(hledane, s));
		}
		for (String s : pridano) {
			m.put(s, Lib.levVzdal(hledane, s));
		}

	/*	//This linked list defines the iteration ordering, which is normally the order in which keys were inserted into the map (insertion-order).
				LinkedHashMap<String, Integer> lm = m.entrySet()
		        .stream()
		        .sorted(Map.Entry.comparingByValue())
		        .collect(Collectors.toMap(
		          Map.Entry::getKey, 
		          Map.Entry::getValue, 
		          (e1, e2) -> e1, 
		          LinkedHashMap::new
		        ));
				
				System.out.println();
				
				for(String key: lm.keySet()){
					System.out.println(key + " - " + lm.get(key));
				}
				
		*/
		
		ArrayList<String> serazena = m.entrySet()
				        .stream()
				        .sorted(Map.Entry.comparingByValue())
				        .map(Map.Entry::getKey)
				        .collect(Collectors.toCollection(
				        		ArrayList::new
				        		));
			
		String rtn = "";
		int i = 0;
		for (String string : serazena) {
			rtn += string + System.lineSeparator();		
			i++;
			
			if(i >= Lib.MAX_PODOBNYCH){
				break;
			}
		}
		
		return rtn;
	}
	
	/*	Rekurzivně projde strom a zapíše nalezená slova.
	 * @param vrchol Vrchol kde se nachází.
	 * @param s Současná část řetězce.
	 * */
	private void slova(Vrchol vrchol, String s){		
		Vrchol[] v=vrchol.getChilds();
		for (int i=0;i<Lib.VELIKOST_ABECEDY;i++) {
			if(v[i]!=null){
				String s2=s+v[i].getText();
				int x;
				if((x=v[i].getPocetVyskytu())>0) {
					slova+=System.lineSeparator()+s2+" ["+x+"]";
					Vyskyt vyskyt=v[i].getPrvni();
					while(vyskyt!=null){
						slova+=", "+vyskyt.getIndex();
						vyskyt=vyskyt.dalsi;
					}	
				}
				slova(v[i], s2);
			}
		}
	}
	
	/**	Vrátí textovou prezentaci slovníku.
	 * @return Slova obsažená ve slovníku, počet a indexy.
	 * */
	public String slova(){
		slova="Obsah (slovo [počet výskytů], index1, index2...):";
		
		slova(koren, "");
		
		for (String str : pridano) {
			slova+=System.lineSeparator()+str+" - přidáno uživatelem";
		}
		
		return slova;
	}
	
	


	
	
	
	/**	Uloží slovník do souboru .dic pro případné znovupoužití. 
	 * 
	 * @param kam Soubor, kam se má uložit.
	 * @throws IOException Chyba se souborem.
	 * */
	public void ulozSlovnik(File kam) throws IOException  {
		OutputStream file = new FileOutputStream(kam);
		OutputStream buffer = new BufferedOutputStream( file );
		ObjectOutput output = new ObjectOutputStream( buffer );

		output.writeObject(this);
		
		output.close();
		buffer.close();
		file.close();	
	}
	
	/**	Načte dříve uložený soubor .dic a vytvoří z něj slovník.
	 * 
	 * @param odkud Soubor, odkud se má načíst.
	 * @return Instance na vytvořený slovník.
	 * @throws IOException Chyba se souborem.
	 * @throws ClassNotFoundException Jiná verze třídy.
	 * */
	public static Slovnik nactiSlovnik(File odkud) throws IOException, ClassNotFoundException {
			InputStream file = new FileInputStream( odkud );
			InputStream buffer = new BufferedInputStream( file );
			ObjectInput input = new ObjectInputStream ( buffer );

			Slovnik s = (Slovnik)input.readObject();
			
			input.close();
			buffer.close();
			file.close();

			return s;	
	}
	
}
