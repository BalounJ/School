package trie_vyskyt_slov;
import java.io.Serializable;

/**
 * Instance třídy {@code Vyskyt} tvoří prvky spojového seznamu reprezentující výskyty v textu třídy {@code Vrchol}.
 * @author Josef Baloun, Petra Štumpfová
 * @version 1.0
 * */
public class Vyskyt implements Serializable{
	/** Serial version UID pro serializaci objektu.
	 */
	private static final long serialVersionUID = 2L;
	/** Počáteční index výskytu.
	 * */
	private final int index;
	/** Ukazatel na další výskyt.
	 * */
	public Vyskyt dalsi;
	
	/**	Vytvoří {@code Vyskyt}, koncový prvek spojového seznamu.
	 *  @param index Počáteční index výskytu.
	 * */
	public Vyskyt(int index) {
		this.index=index;
		dalsi=null;
	}

	/** Getter pro {@code index}.
	 * @return Počáteční index výskytu.
	 */
	public int getIndex() {
		return index;
	}
}
