package util;

import java.util.List;

public class Utilities {
	
	public static long timer;
	
	public static void startTimer() {
		timer = System.currentTimeMillis();
	}
	
	public static boolean isTimeOver () {
		long currentTime = System.currentTimeMillis();
		double diffSec = ((double) currentTime - timer)/1000;
		return diffSec > 10;
	}
	
	// UNUSED (as far as I remember)
	public static List<List<Integer>> sortAdjacencyList(List<List<Integer>> OGList) {
		long startTimeNano = System.nanoTime();
		long startTime = System.currentTimeMillis();
		
		for(int i = 0; i < OGList.size(); i++) {
			OGList.get(i).sort((a,b) -> a - b);
		}
		
		long endTimeNano = System.nanoTime();
		long endTime = System.currentTimeMillis();
		double diffSec = ((double) endTime - startTime)/1000;
		System.out.println("SORTING TOOK: " + diffSec + " sec " + (endTimeNano - startTimeNano)); 
		
		return OGList;
	}
	
}
