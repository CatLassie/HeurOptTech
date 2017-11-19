package gvns;

import java.util.List;

import models.Solution;
import util.Utilities;
import vnd.VND;

public class GVNS {
	
	private Solution solution;
	
	// poor mans GVNS (only uses 1 neighbourhood structure (randomly shuffling the spine order)
	public GVNS(Solution solution) {
		this.solution = solution;
	}
	
	public Solution search() {
		VND vnd;
		Solution currentBestSolution = solution;
		
		timeout:
		for(int i = 0; i < solution.getAdjacencyMatrix().length; i++){
			if(Utilities.isTimeOver()){
				System.out.println("GVNS time is up!");
				break timeout;
			}
			// make a copy of the current best solution and shuffle its spine order
			Solution currentBestSolutionCopy = currentBestSolution.copy();
			if(i > 0) {
				currentBestSolutionCopy.shuffleSpineOrder();
				List<Integer> newCrossingsList = currentBestSolutionCopy.calculateTotalCrossingArray();
				currentBestSolutionCopy.setCrossingsList(newCrossingsList);
			}
			vnd = new VND(currentBestSolutionCopy);
			
			System.out.println("step #"+(i+1)+":");
			long startTimeNano = System.nanoTime();
			long startTime = System.currentTimeMillis();
			Solution currentSolution =  vnd.search();
			long endTimeNano = System.nanoTime();
			long endTime = System.currentTimeMillis();
			double diffSec = ((double) endTime - startTime)/1000;
			System.out.println("VND TOOK: " + diffSec + " sec " + (endTimeNano - startTimeNano)+"\n");
			// System.out.println(currentSolution.getTotalCrossings());
			
			if(currentSolution.getTotalCrossings() < currentBestSolution.getTotalCrossings()){
				currentBestSolution = currentSolution;
			}
				
		}

		return currentBestSolution;
	}

}
