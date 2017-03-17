package NKA_na_DKA.AUTOMAT;
import java.util.concurrent.ConcurrentSkipListSet;

public class Mnozina extends ConcurrentSkipListSet<String> implements Comparable<Mnozina>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Mnozina() {
		// TODO Auto-generated constructor stub
	}
	

	
	@Override
	public int compareTo(Mnozina o) {
		if(this.equals(o)){
			return 0;
		}
		
		String ts="";
		String os="";
		
		for (String string : o) {
			if(string!=null){
				os+=string;
			}
		}
		
		for (String string : this) {
			if(string!=null){
				ts+=string;
			}
		}
		
		
		return ts.compareToIgnoreCase(os);
	}

}
