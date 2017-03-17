package hanoi_bfs_dfs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class HanoiSolve {
	
	private static HanoiState solveDFS(final int count, final int maxDepth)
	{
		HanoiState root = new HanoiState(count);
		Stack<HanoiState> stack = new Stack<>();
		
		stack.push(root);
		while(!stack.isEmpty()){
			HanoiState actual = stack.pop();
			
			if(actual.isSollution()){
				return actual;
			}
			
			if(actual.getDepth() >= maxDepth){
				continue;
			}
			
			for (HanoiState next : actual.getPossibilities()) {
				stack.push(next);
			}
		}
		
		return null;		
	}
	
	private static HanoiState solveBFS(final int count)
	{
		HanoiState root = new HanoiState(count);
		Queue<HanoiState> queue = new LinkedList<>();
		
		queue.add(root);
		while(!queue.isEmpty()){
			HanoiState actual = queue.poll();
			
			if(actual.isSollution()){
				return actual;
			}
			
			for (HanoiState next : actual.getPossibilities()) {
				queue.add(next);
			}
		}
				
		return null;
	}
	
	private static void printResult(HanoiState result)
	{
		if(result == null)
			return;
		
		printResult(result.getParent());
		result.printState();
	}
	
	public static void DFS(int count, int maxDepth)
	{
		HanoiState result = solveDFS(count, maxDepth);
		if(result != null){
			printResult(result);
			System.out.println("-------------------------------------------------------");
			System.out.println("Solved by DFS in "+result.getDepth()+" depth.");
		}else{
			System.out.println("Couldn't solve in "+maxDepth+" depth.");
		}
	}
	
	public static void BFS(int count)
	{
		HanoiState result = solveBFS(count);
		if(result != null){
			printResult(result);
			System.out.println("-------------------------------------------------------");
			System.out.println("Solved by BFS in "+result.getDepth()+" depth.");
		}else{
			System.out.println("Couldn't solve.");
		}
	}
}
