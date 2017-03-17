package generator_nahodnych_cisel;
import java.util.Arrays;
import java.util.Random;
/**
 * Třída {@code Run} slouží pro testování.
 * @author Josef Baloun
 * */
public class Run {
	/**
	 * pro rozsah od <0;MOD)
	 * */
	public static final int MOD = 10;
	/**
	 * počet iterací / generovaných prvků
	 * */
	public static final int ITER_COUNT = 300;
		
	/**
	 * porovnává masku s polem a vrátí počet výskytů masky
	 * @param mask Maska pro porovnání.
	 * @param mask Pole pro porovnání. 
	 * */
	public static int Mask(int[] mask, int[] p){
		int count = 0;
		
		int[] cmp = new int[mask.length];
		
		for (int i = 0; i <= p.length - cmp.length; i++) {
			
			
			for (int j = 0; j < cmp.length; j++) {
				cmp[j] = p[i+j];
			}			
			
			if(Arrays.equals(cmp, mask)){
				count++;
			}
			
		}
		return count;
	}
	
	/**
	 * najde (brute-force) nejdelší vzorec a vypíše info
	 * @param s vygenerovaná posloupnost
	 * 
	 * */
	public static void LongestSubsequence(String s){
		String[] sp = s.split(" ");
		int[] p = new int[sp.length];
		
		for (int i = 0; i < p.length; i++) {
			p[i] = Integer.parseInt(sp[i]);	
		}
		
		
		int len = p.length-1;
		
		int[] mask;
		int start;
		int end;
		
		int count = 0;
		
		boolean lastMask = false;
		int mLen = 0;
		int mCount = 0;
		int[] mArr = null;
		
		System.out.print("nejdelsi vzorce: ");
		
		while(len > 1){
			start = 0;
			end = len-1;
			
			while(end < p.length){
				mask = new int[len];
				
				int j = 0;
				for(int i = start; i<= end; i++){
					mask[j++]=p[i];
				}
				
				count = Mask(mask, p);
				
				if(count > 1){
					
					if(count > mCount){
						mLen = mask.length;
						mCount = count;
						mArr = mask;
					
						lastMask = true;
					}
				}
								
				start++;
				end++;
			}
			
			if(lastMask){
				System.out.println(Arrays.toString(mArr));
				System.out.println("delka: "+mLen+" pocet: "+mCount);
				
				return;
			}
	
			len--;
		}

		System.out.println("nenalezen (pouze délky 1)");
	}
	
	/**
	 * vypíše info z testování čas, výstup, min a max četnost, případně info o vyskytnutých vzorcích
	 * @param nadpis nadpis pro vypis
	 * @param time cas pro vypis
	 * @param output vygenerovaná čísla
	 * @param p pole četností
	 * 
	 * */
	public static void Vypis(String nadpis, long time, String output, int[] p){
		
		System.out.println("--------------------------------------------------------------------");
		
		System.out.println(nadpis);
		
		System.out.println(output);
		
		LongestSubsequence(output);
		
		System.out.println("Čas> "+time);
		
		Arrays.sort(p);
		System.out.println("Min> "+p[0]);
		System.out.println("Max> "+p[MOD-1]);
		
	}
	
	
	/**
	 * metoda pro testování
	 * @param r1 1. generator pro testování
	 * @param r2 2. generator pro testování
	 * @param popisekR1 popisek 1. generatoru
	 * @param popisekR2 popisek 2. generatoru
	 */
	public static void Test(IntGenerator r1, String popisekR1, IntGenerator r2, String popisekR2){
		
		System.out.println("Test("+popisekR1+", "+popisekR2+")> MOD = "+MOD+", ITER = "+ITER_COUNT);
		
		long time1 = 0;
		long time2 = 0;
		long start, end;
		int x;
		
		String output1 = "";
		String output2 = "";
		String outputAPI = "";
		
		
		Random rnAPI = new Random();
		long timeAPI = 0;
		
		int[] pole1 = new int[MOD];
		int[] pole2 = new int[MOD];
		int[] poleAPI = new int[MOD];
		
		Arrays.fill(pole1, 0);
		Arrays.fill(pole2, 0);
		Arrays.fill(poleAPI, 0);
		
		


		
		for (int i = 0; i < ITER_COUNT; i++) {
			//System.out.print((i+1)+". číslo");
			
			start = System.nanoTime();
			x = r1.getRandomInt();
			end = System.nanoTime();
			time1 += (end-start);
			
			pole1[x]++;
			output1 += x + " ";
			
			//System.out.print("&\t"+x);
			
			start = System.nanoTime();
			x = r2.getRandomInt();
			end = System.nanoTime();
			time2 += (end-start);
			
			pole2[x]++;
			output2 += x + " ";
			//System.out.print("&\t"+x);
			start = System.nanoTime();
			x = rnAPI.nextInt(MOD);
			end = System.nanoTime();
			timeAPI += (end-start);
			
			poleAPI[x]++;
			outputAPI += x + " ";
			//System.out.println("&\t"+x+"\\\\");
		}
		//System.out.println(Arrays.toString(pole1));
		//System.out.println(Arrays.toString(pole2));
		//System.out.println(Arrays.toString(poleAPI));
		
		Vypis(popisekR1, time1, output1, pole1);	
		
		Vypis(popisekR2, time2, output2, pole2);
		
		Vypis("API generator", timeAPI, outputAPI, poleAPI);
		
		
	}
	
	public static void main(String[] args) {
	//	MyRandom128Number my128 = new MyRandom128Number();
	//	RandomNumberConst myConst = new RandomNumberConst(MOD);
		
		SubGenerator sub = new SubGenerator(1664525, 1013904223, (long) Math.pow(2, 32), System.currentTimeMillis(), MOD);
		
		ImprovedGenerator imp = new ImprovedGenerator(MOD);
		
		//Test(my128, "Generator128", myConst, "Optimální");
		Test(imp, "Vylepšený", sub, "SubGenerátor");
		
	
	
	
	}

}
