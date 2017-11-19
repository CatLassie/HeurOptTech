package vnd;

import localSearch.LocalSearch;
import models.Solution;
import util.NeighbourhoodStructureEnum;
import util.StepFunctionEnum;

public class VND {
	
	private Solution solution;
	
	public VND(Solution solution) {
		this.solution = solution;
	}
	
	public Solution search() {
		// because 2 we have neighbourhood structures :P
		LocalSearch localSearch;
		Solution currentBestSolution = solution;
		
		for(int i = 0; i < 2; i++){
			localSearch = i == 0 ? 
					new LocalSearch(currentBestSolution, NeighbourhoodStructureEnum.EDGE, StepFunctionEnum.BEST_IMPROVEMENT) :
					new LocalSearch(currentBestSolution, NeighbourhoodStructureEnum.VERTEX, StepFunctionEnum.BEST_IMPROVEMENT);	
					
			long startTimeNano = System.nanoTime();
			long startTime = System.currentTimeMillis();
			Solution currentSolution =  localSearch.search();
			long endTimeNano = System.nanoTime();
			long endTime = System.currentTimeMillis();
			double diffSec = ((double) endTime - startTime)/1000;
			System.out.println("LOCAL SEARCH ("+localSearch.getNeighbourhoodType()+" "+localSearch.getStepFunctionType()+") TOOK: "
					+ diffSec + " sec " + (endTimeNano - startTimeNano));
			
			if(currentSolution.getTotalCrossings() < currentBestSolution.getTotalCrossings()){
				currentBestSolution = currentSolution;
				i = -1;
			}
		}
		
		return currentBestSolution;
	}

}
