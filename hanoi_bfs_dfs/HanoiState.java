package hanoi_bfs_dfs;

import java.util.ArrayList;
import java.util.Arrays;

public class HanoiState {
	/**
	 * 0 is the smallest, count-1 is the biggest
	 * */
	private int placeOf[];
	private HanoiState parent;
	private int depth;
	
	public HanoiState(int count) {
		this.placeOf = new int[count];
		Arrays.fill(placeOf, 1);	//on the left
		this.parent = null;
		depth = 0;
	}
	
	private HanoiState(int[] placeOf, HanoiState parent) {
		this.placeOf = placeOf;
		this.parent = parent;
		this.depth = parent.depth + 1;
	}



	public ArrayList<HanoiState> getPossibilities()
	{
		ArrayList<HanoiState> possibilities = new ArrayList<>();
		
		int smallest[] = new int[4];
		Arrays.fill(smallest, Integer.MAX_VALUE); //is empty
		
		for (int i = placeOf.length - 1; i >=0 ; i--) {
			smallest[placeOf[i]] = i;
		}
		
		if(smallest[1] < smallest[2]){	//move from 1 to 2
			int newPlaceOf[] = placeOf.clone();
			newPlaceOf[smallest[1]] = 2;
			possibilities.add(new HanoiState(newPlaceOf, this));
		}else if(smallest[2] < placeOf.length){ //move from 2 to 1
			int newPlaceOf[] = placeOf.clone();
			newPlaceOf[smallest[2]] = 1;
			possibilities.add(new HanoiState(newPlaceOf, this));
		}
		
		if(smallest[1] < smallest[3]){
			int newPlaceOf[] = placeOf.clone();
			newPlaceOf[smallest[1]] = 3;
			possibilities.add(new HanoiState(newPlaceOf, this));
		}else if(smallest[3] < placeOf.length){
			int newPlaceOf[] = placeOf.clone();
			newPlaceOf[smallest[3]] = 1;
			possibilities.add(new HanoiState(newPlaceOf, this));
		}
		
		if(smallest[2] < smallest[3]){
			int newPlaceOf[] = placeOf.clone();
			newPlaceOf[smallest[2]] = 3;
			possibilities.add(new HanoiState(newPlaceOf, this));
		}else if(smallest[3] < placeOf.length){
			int newPlaceOf[] = placeOf.clone();
			newPlaceOf[smallest[3]] = 2;
			possibilities.add(new HanoiState(newPlaceOf, this));
		}
		
		
		return possibilities;
	}
	
	public boolean isSollution()
	{
		for (int i = placeOf.length - 1; i >=0 ; i--) {
			if(placeOf[i] != 3)
				return false;
		}
		return true;
	}
	
	public void printState()
	{
		char c = (char) ('A' + placeOf.length - 1);
		
		if(depth == 0){
			System.out.println("-------------------- Initial step ---------------------");
		}else{
			System.out.println("----------------------- "+depth+". step -----------------------");
		}
		System.out.print("-> ");
		for (int i = placeOf.length - 1; i >=0 ; i--) {
			if(placeOf[i] == 1)
				System.out.print((char)(c-i));
		}
		System.out.println();System.out.print("-> ");
		for (int i = placeOf.length - 1; i >=0 ; i--) {
			if(placeOf[i] == 2)
				System.out.print((char)(c-i));
		}
		System.out.println();System.out.print("-> ");
		for (int i = placeOf.length - 1; i >=0 ; i--) {
			if(placeOf[i] == 3)
				System.out.print((char)(c-i));
		}		
		System.out.println();
	}

	public int getDepth() {
		return depth;
	}

	public HanoiState getParent() {
		return parent;
	}
	
	
	
/*
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		HanoiState other = (HanoiState) obj;
		
		if(!this.parent.equals(other.parent)){
			return false;
		}
		
	}
	
		*/
}
