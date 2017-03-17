package NKA_na_DKA;
import java.util.concurrent.ConcurrentSkipListSet;

import NKA_na_DKA.AUTOMAT.Automat;
import NKA_na_DKA.AUTOMAT.Pravidlo;

public class Apk {
	
	private static void vypis(Pravidlo[] cp){
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");

		for (Pravidlo pravidlo : cp) {
			if(pravidlo.isKoncovy())
				System.out.print("<-");
			
			if(pravidlo.isVstupni())
				System.out.print("->");
			
			System.out.print("\t");
			
				for (String s : pravidlo.getSloupce()) {
					System.out.format("%25s", s);
				}
				System.out.println();
			
		}
		
		//System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	
	public static void main(String[] args) {
				
		/* Příklady ze cviček a co jsem počítal pro testování.
		 * 
		 * a2 - automat bez e-hran je třeba poslední sloupec nechat prázdnej
		 * a5 - zadanej už DKA
		 * a6 - když se nezadá vstupní vrchol
		 * a7 - nejsou stejně dlouhý pravidla
		 * a8 - když nejsou shodný názvy vrcholů
		 * 
		 * ostatní jsou NKA s e-hranami
		 * */
		
		ConcurrentSkipListSet<Pravidlo> pravidla1 = new ConcurrentSkipListSet<Pravidlo>();		
		pravidla1.add(new Pravidlo(new String[]{"1",	"2" ,	"" ,	 "" ,	 "4"}, true, false));
		pravidla1.add(new Pravidlo(new String[]{"2",	"" ,	"3" ,	 "" ,	 ""}, false, false));
		pravidla1.add(new Pravidlo(new String[]{"3",	"" ,	"" ,	 "" ,	 ""}, false, true));
		pravidla1.add(new Pravidlo(new String[]{"4",	"" ,	"" ,	 "4" ,	 "3"}, false, false));
		Automat a1=new Automat(pravidla1);
		
		ConcurrentSkipListSet<Pravidlo> pravidla2 = new ConcurrentSkipListSet<Pravidlo>();
		pravidla2.add(new Pravidlo(new String[]{"q1" ,	"q1, q2",	"q3", 	""}, true, true));
		pravidla2.add(new Pravidlo(new String[]{"q2" ,	"q3" ,		"q4", 	""}, true, false));
		pravidla2.add(new Pravidlo(new String[]{"q3" ,	"q1, q3",	"q3", 	""}, false, false));
		pravidla2.add(new Pravidlo(new String[]{"q4" ,	"q1",		"q3", 	""}, false, true));
		Automat a2=new Automat(pravidla2);
		
		
		ConcurrentSkipListSet<Pravidlo> pravidla3 = new ConcurrentSkipListSet<Pravidlo>();
		pravidla3.add(new Pravidlo(new String[]{"A",	"" ,	"" ,	"" ,	"B,F"}, true, false));
		pravidla3.add(new Pravidlo(new String[]{"B",	"" ,	"" ,	"" ,	"C"}, false, false));
		pravidla3.add(new Pravidlo(new String[]{"C",	"" ,	"" ,	"" ,	"D"}, false, true));
		pravidla3.add(new Pravidlo(new String[]{"D",   "C,E,I",	"" ,	"" ,	"C"}, false, false));
		pravidla3.add(new Pravidlo(new String[]{"E",	"" ,	"" ,	"" ,	""}, false, false));
		pravidla3.add(new Pravidlo(new String[]{"F",	"" ,	"G" ,	"" ,	""}, false, false));
		pravidla3.add(new Pravidlo(new String[]{"G",	"" ,	"" ,	"H" ,	""}, false, false));
		pravidla3.add(new Pravidlo(new String[]{"H",	"" ,	"" ,	"" ,	"I"}, false, false));
		pravidla3.add(new Pravidlo(new String[]{"I",	"" ,	"J" ,	"" ,	""}, false, false));
		pravidla3.add(new Pravidlo(new String[]{"J",	"J" ,	"J" ,	"J" ,	""}, false, true));
		pravidla3.add(new Pravidlo(new String[]{"K",	"" ,	"" ,	"" ,	"J"}, true, false));
		Automat a3=new Automat(pravidla3);	
		
		ConcurrentSkipListSet<Pravidlo> pravidla4 = new ConcurrentSkipListSet<Pravidlo>();
		pravidla4.add(new Pravidlo(new String[]{"A",	"B" ,	"" ,	"" ,	""}, false, false));
		pravidla4.add(new Pravidlo(new String[]{"B",	"" ,	"" ,	"C" ,	""}, false, true));
		pravidla4.add(new Pravidlo(new String[]{"C",	"" ,	"A" ,	"" ,	""}, false, false));
		pravidla4.add(new Pravidlo(new String[]{"D",	"" ,	"A,B,C","" ,	"F"}, false, false));
		pravidla4.add(new Pravidlo(new String[]{"E",	"D" ,	"" ,	"" ,	""}, true, false));
		pravidla4.add(new Pravidlo(new String[]{"F",	"" ,	"" ,	"" ,	"C,D"}, true, false));
		Automat a4=new Automat(pravidla4);	
		
		
		
		ConcurrentSkipListSet<Pravidlo> pravidla5 = new ConcurrentSkipListSet<Pravidlo>();		
		pravidla5.add(new Pravidlo(new String[]{"A",	"B" ,	""}, true, false));
		pravidla5.add(new Pravidlo(new String[]{"B",	"B" ,	""}, false, true));
		Automat a5=new Automat(pravidla5);	
		
		
		
		
		ConcurrentSkipListSet<Pravidlo> pravidla6 = new ConcurrentSkipListSet<Pravidlo>();
		pravidla6.add(new Pravidlo(new String[]{"A",	"" ,	"" ,	"" ,	"B,F"}, false, false));
		pravidla6.add(new Pravidlo(new String[]{"B",	"" ,	"" ,	"" ,	"C"}, false, false));
		pravidla6.add(new Pravidlo(new String[]{"C",	"" ,	"" ,	"" ,	"D"}, false, true));
		pravidla6.add(new Pravidlo(new String[]{"D",   "C,E,I",	"" ,	"" ,	"C"}, false, false));
		pravidla6.add(new Pravidlo(new String[]{"E",	"" ,	"" ,	"" ,	""}, false, false));
		pravidla6.add(new Pravidlo(new String[]{"F",	"" ,	"G" ,	"" ,	""}, false, false));
		pravidla6.add(new Pravidlo(new String[]{"G",	"" ,	"" ,	"H" ,	""}, false, false));
		pravidla6.add(new Pravidlo(new String[]{"H",	"" ,	"" ,	"" ,	"I"}, false, false));
		pravidla6.add(new Pravidlo(new String[]{"I",	"" ,	"J" ,	"" ,	""}, false, false));
		pravidla6.add(new Pravidlo(new String[]{"J",	"J" ,	"J" ,	"J" ,	""}, false, true));
		pravidla6.add(new Pravidlo(new String[]{"K",	"" ,	"" ,	"" ,	"J"}, false, false));
		Automat a6=new Automat(pravidla6);	
		
		

		ConcurrentSkipListSet<Pravidlo> pravidla7 = new ConcurrentSkipListSet<Pravidlo>();
		pravidla7.add(new Pravidlo(new String[]{"B",	"B" ,	""}, false, true));
		pravidla7.add(new Pravidlo(new String[]{"A",	"B" ,	"skdjbg" ,	"" ,	""}, true, false));
	try {
		Automat a7=new Automat(pravidla7);
	} catch (IllegalArgumentException e) {
		System.out.println("a7: "+e);
		System.out.println();
	}
	
		
		ConcurrentSkipListSet<Pravidlo> pravidla8 = new ConcurrentSkipListSet<Pravidlo>();
		pravidla8.add(new Pravidlo(new String[]{"A",	"B" ,	""}, true, false));
		pravidla8.add(new Pravidlo(new String[]{"B",	"B" ,	"F"}, false, true));
	try {
		Automat a8=new Automat(pravidla8);
	} catch (IllegalArgumentException e) {
		System.out.println("a8: "+e);
		System.out.println();
	}	
		
		/*vypis(a1.getVstupniPravidla());
		vypis(a1.getTabE_Nasl());
		vypis(a1.getDkaPravidla());
		System.out.println();System.out.println();
		vypis(a2.getVstupniPravidla());
		vypis(a2.getTabE_Nasl());
		vypis(a2.getDkaPravidla());
		System.out.println();System.out.println();
		vypis(a3.getVstupniPravidla());
		vypis(a3.getTabE_Nasl());
		vypis(a3.getDkaPravidla());
		System.out.println();System.out.println();
		vypis(a4.getVstupniPravidla());
		vypis(a4.getTabE_Nasl());
		vypis(a4.getDkaPravidla());
		System.out.println();System.out.println();*/
		vypis(a5.getVstupniPravidla());
		vypis(a5.getTabE_Nasl());
		vypis(a5.getDkaPravidla());
	/*	System.out.println();System.out.println();
		vypis(a6.getVstupniPravidla());
		vypis(a6.getTabE_Nasl());
		vypis(a6.getDkaPravidla());
		*/
		
		
		
		
		ConcurrentSkipListSet<Pravidlo> pravidla06 = new ConcurrentSkipListSet<Pravidlo>();
		pravidla06.add(new Pravidlo(new String[]{"1",	"2" ,	"" ,	"5"}, true, false));
		pravidla06.add(new Pravidlo(new String[]{"2",	"" ,	"" ,	"3"}, false, false));
		pravidla06.add(new Pravidlo(new String[]{"3",	"3" ,	"3" ,	"4"}, false, false));
		pravidla06.add(new Pravidlo(new String[]{"4",	"" ,	"" ,	""}, false, true));
		pravidla06.add(new Pravidlo(new String[]{"5",	"5" ,	"5" ,	"6"}, false, false));
		pravidla06.add(new Pravidlo(new String[]{"6",	"7" ,	"" ,	""}, false, false));
		pravidla06.add(new Pravidlo(new String[]{"7",	"8" ,	"" ,	""}, false, false));
		pravidla06.add(new Pravidlo(new String[]{"8",	"" ,	"" ,	"9"}, false, false));
		pravidla06.add(new Pravidlo(new String[]{"9",	"9" ,	"9" ,	"4"}, false, false));
		
		
		Automat a06=new Automat(pravidla06);	
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		vypis(a06.getVstupniPravidla());
		vypis(a06.getTabE_Nasl());
		vypis(a06.getDkaPravidla());
		
		
		
		
		
		
		
		
		
	}
}
