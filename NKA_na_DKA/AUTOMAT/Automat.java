package NKA_na_DKA.AUTOMAT;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentSkipListSet;


/**Třída pro převod NKA na DKA.
 * */
public class Automat {
	private final ConcurrentSkipListSet<Pravidlo> vstupPrav;
	private ArrayList<Pravidlo> dkaPrav;
	private ArrayList<Pravidlo> eNasl;


	/**Vytvoří instanci a vyplní tabulky eNasl a DKA, které lze získat přes metody.
	 * @param pravidla	Seznam neduplicitních pravidel NKA.
	 * @throws IllegalArgumentException Pokud není stejná dálka pravidel, nebo nastal problém při validaci názvů. Info ve výjimce.
	 * */
	public Automat(ConcurrentSkipListSet<Pravidlo> pravidla) throws IllegalArgumentException{
		this.vstupPrav = pravidla;
		dkaPrav = new ArrayList<Pravidlo>();
		
		//validace stejné délky pravidel a načtení vrcholů do množiny pro validaci názvů
		Mnozina vrcholy=new Mnozina();
		final int pocSloupcu = pravidla.first().sloupce.size();
		for (Pravidlo pravidlo : pravidla) {
			if(pravidlo.sloupce.size()!=pocSloupcu){
				throw new IllegalArgumentException("Zadané řádky nemají stejný počet sloupců.");
			}
			vrcholy.addAll(pravidlo.getPrvni());
		}
		
		//validace názvů
		int pCislo=1;
		for (Pravidlo p : pravidla) {
			for (int i = 1; i < pocSloupcu; i++) { // 1. sloupec je načtený ve vrcholech, zbytečné testovat
				if(!vrcholy.containsAll(p.sloupce.get(i))){					
					throw new IllegalArgumentException("Při validaci názvů vrcholů došlo k chybě: "+System.lineSeparator()+
							"min. k jednomu z "+p.sloupce.get(i).toString()+" nebyl nalezen odpovídající vrchol."+System.lineSeparator()+
							p.sloupce.get(i).toString()+" byl nalezen v "+(i+1)+". sloupci a pravděpodobně na "+pCislo+". řádku (pořadí pravidel může být přeházeno datovou strukturou).");
				}
			}
			pCislo++;
		}
		
		
		
		
		prepareTabNasl();
		
		NKAtoDKA();
	}

	private void prepareTabNasl() {
		
		eNasl = new ArrayList<Pravidlo>(vstupPrav.size());
		
		for (Pravidlo p : vstupPrav) {
			Mnozina nazev = p.getPrvni();
			Mnozina nasl = new Mnozina();
			nasl.addAll(nazev);
			
			Queue<Mnozina> fronta=new LinkedList<Mnozina>();
			fronta.add(p.getEHrana());
			
			
			//prida vsechny nasledniky
			while(!fronta.isEmpty()){
				Mnozina m=fronta.poll();
								
				if(m.size()>0 && !nasl.containsAll(m) /* m.equals(nazev)*/){	//pokud obsahuje min. 1 vrchol a neni shodna se zpracovavanou
					nasl.addAll(m);
					for (Pravidlo prav : vstupPrav) {
						if(m.containsAll(prav.getPrvni())){	//pokud je pravidlo naslednik
							fronta.add(prav.getEHrana());						
						}
					}
					
					
				}
			}

			eNasl.add(new Pravidlo(nazev, nasl));
		}		
	}

	/**	Vrátí pole pravidel, které tvoří NKA.
	 * @return	Pole pravidel, které tvoří NKA.
	 * */
	public Pravidlo[] getVstupniPravidla() {	
		return getPravidla(vstupPrav);
	}
	
	/**	Vrátí pole pravidel, které tvoří DKA.
	 * @return	Pole pravidel, které tvoří DKA.
	 * */
	public Pravidlo[] getDkaPravidla() {	
		return getPravidla(dkaPrav);
	}
	
	/**	Vrátí pole pravidel, které tvoří tabulku e-následníků.
	 * @return	Pole pravidel, které tvoří tabulku e-následníků.
	 * */
	public Pravidlo[] getTabE_Nasl() {	
		return getPravidla(eNasl);
	}
	
	
	private Pravidlo[] getPravidla(Collection<Pravidlo> prav) {
		Pravidlo[] rtn=new Pravidlo[prav.size()];
		
		int i=0;
		for (Pravidlo pravidlo : prav) {
			rtn[i++]=pravidlo;
		}
		
		return rtn;
	}
	

	private Mnozina getNasl(Mnozina m){
		Mnozina rtn = new Mnozina();
		
		for (Pravidlo p : eNasl) {
			if(m.containsAll(p.getPrvni())){
				rtn.addAll(p.getEHrana());
			}
		}
		
		return rtn;
	}

	
	
	
	
	
	
	private void NKAtoDKA() {
		Mnozina vstupni=new Mnozina();		
		
		for (Pravidlo p : vstupPrav) {
			if(p.isVstupni()){
				vstupni.addAll(getNasl(p.getPrvni()));				
			}
		}
		
		//-----------------------------------nactena vstupni mnozina
			
		final int pocSloupcu = vstupPrav.first().sloupce.size()-1; //e hranu nekontroluju
		
		Queue<Mnozina> fronta=new LinkedList<Mnozina>();
		fronta.add(vstupni);
		boolean jeVstupni=true;
		boolean jeKoncovy=false;
			
		
		while(!fronta.isEmpty()){
			Mnozina m=fronta.poll();
			//testovani pokud je v pravidlech
			boolean exists=false;
			for (Pravidlo p : dkaPrav) {
				if(m.equals(p.getPrvni())){	//pokud pravidlo odpovida 
					exists=true;
					break;					
				}
			}
			if(exists){
				continue;
			}

			
			//--------------------------pokud neni v pravidlech
			ArrayList<Mnozina> sloupce=new ArrayList<Mnozina>(pocSloupcu);
			sloupce.add(m);
			
			for (int i = 1; i < pocSloupcu; i++) { //naplnim sloupec zatim prazdnymi mnozina potom se prida
				sloupce.add(new Mnozina());
			}
			//--------------------pridavani do mnozin
			for (Pravidlo p : vstupPrav) {
				if(m.containsAll(p.getPrvni())){	//pokud pravidlo odpovida 
					for (int i = 1; i < pocSloupcu; i++) { 
						sloupce.get(i).addAll(getNasl(p.sloupce.get(i)));
					}
					
				/*	if(p.isVstupni()){ nesmysl vstupni je pouze 1. vstupni mnozina ostatni uz ne
						jeVstupni=true;
					}*/
					if(p.isKoncovy()){
						jeKoncovy=true;
					}
				}
			}
			//----------------hotove pravidlo
			dkaPrav.add(new Pravidlo(sloupce, jeVstupni, jeKoncovy));
			
			for (int i = 1; i < pocSloupcu; i++) { 
				if(sloupce.get(i).size()>0){			//prazdnou mnozinu nepridavam
					fronta.add(sloupce.get(i));
				}
			}
			
			jeVstupni=false;
			jeKoncovy=false;
		}	
	}
	
	
	
	
	

}
