package util;

import java.util.List;

public class Utilities {
	
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
