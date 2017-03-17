package trie_vyskyt_slov;
import java.io.Serializable;

/**
 * Instance třídy {@code Vrchol} tvoří vrcholy pro stromovou datovou strukturu {@code Slovnik}.
 * @author Josef Baloun, Petra Štumpfová
 * @version 1.2
 * */
public class Vrchol implements Serializable {

	/** Serial version UID pro serializaci objektu.
	 */
	private static final long serialVersionUID = 2L;
	/**	Předek daného vrcholu.
	 * */
	private final Vrchol parent;
	/**	Potomci daného vrcholu.
	 * */
	private Vrchol[] child;
	/** Text vrcholu pro budoucí komprimaci.
	 * */
	private String text;
	
	/** Ukazatel na první výskyt v textu.
	 * */
	private Vyskyt prvni;
	/** Ukazatel na poslední výskyt v textu.
	 * */
	private Vyskyt posledni;
	
	/** Vytvoří {@code Vrchol} a zařadí ho do stromu.
	 *  @param parent Předek nového vrcholu.
	 *  @param text Text vrcholu.
	 * */
	public Vrchol(Vrchol parent, String text) {
		this.parent=parent;
		child = new Vrchol[Lib.VELIKOST_ABECEDY];
		for (int i = 0; i < child.length; i++) {
			child[i]=null;
		}		
		this.text=text;
		prvni=null;
		posledni=null;
	}
	
	/** Getter pro {@code parent}.
	 * @return Předek vrcholu.
	 */
	public Vrchol getParent() {
		return parent;
	}
	
	/** Vrátí index odpovídající znakům a-z na základě ASCII kódu.
	 * @param znak Znak pro výpočet indexu.
	 * @return Index odpovídající znakům a-z.
	 */
	private int getIndex(char znak){
		int index=(int)znak-(int)'a';
		return index;
	}
	
	/** Vrátí potomka vrcholu dle daného znaku a-z.
	 * @param znak Znak a-z podle kterého chceme potomka.
	 * @return Potomek vrcholu.
	 */
	public Vrchol getChild(char znak) {		
		return child[getIndex(znak)];
	}
	
	/** Vrátí potomka vrcholu dle daného indexu.
	 * @param index index potomka.
	 * @return Potomek vrcholu.
	 */
	public Vrchol getChild(int index) {		
		return child[index];
	}



	/**Nastaví potomka vrcholu podle indexu 0-25.
	 * @param index Index 0-25 odpovidajici a-z.
	 * @param v {@code Vrchol} potomka pro přiřazení.
	 * 
	 */
	public void setChild(int index, Vrchol v) {
		this.child[index] = v;
	}
	
	/**Nastaví potomka vrcholu podle znaku a-z.
	 * @param znak Znak a-z podle kterého chceme nastavit potomka.
	 * @param text Text pro {@code Vrchol} potomka.
	 * 
	 */
	public void setChild(char znak, String text) {
		this.child[getIndex(znak)] = new Vrchol(this, text);
	}



	/** Getter pro {@code text}.
	 * @return Text vrcholu.
	 */
	public String getText() {
		return text;
	}

	/** Setter pro {@code text}.
	 * @param text Text pro nastavení.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/** Vrátí počet výskytů.
	 * @return Počet výskytů.
	 */
	public int getPocetVyskytu() {
		int i=0;
		Vyskyt v = prvni;	
		while(v!=null){
			i++;
			v=v.dalsi;
		}
		
		return i;
	}
	
	/** Vrátí počet childs.
	 * @return Počet childs.
	 */
	public int getPocetChilds() {
		int i=0;
		
		for (int j = 0; j < Lib.VELIKOST_ABECEDY; j++) {
			if(child[j] != null){
				i++;
			}
		}
		
		return i;
	}
	
	/** Vrátí 1. potomka.
	 * @return prvni potomek.
	 */
	public Vrchol getPrvniChild() {
		for (int j = 0; j < Lib.VELIKOST_ABECEDY; j++) {
			if(child[j] != null){
				return child[j];
			}
		}
		
		return null;
	}
	
	/** Přidé nový výskyt do spojového seznamu.
	 * @param index Počáteční index výskytu.
	 */
	public void incrementPocetVyskytu(int index) {
		if(prvni==null){
			prvni=new Vyskyt(index);
			posledni=prvni;
		}else{
			Vyskyt v=new Vyskyt(index);
			posledni.dalsi=v;
			posledni=v;
		}
	}

	/** Getter pro {@code prvni}.
	 * @return Ukazatel na první výskyt.
	 */
	public Vyskyt getPrvni() {
		return prvni;
	}
	
	/** Setter pro {@code prvni}.
	 * @param v Ukazatel na první výskyt.
	 */
	public void setPrvni(Vyskyt v) {
		prvni = v;
	}
	
	
	public Vrchol[] getChilds() {		
		return child;
	}

}
