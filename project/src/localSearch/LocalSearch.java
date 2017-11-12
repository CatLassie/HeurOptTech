package localSearch;

import models.SolutionAlternate;

public class LocalSearch implements ILocalSearch {

	private SolutionAlternate bestSolution;
	private int bestSolutionValue;
	
	public LocalSearch(SolutionAlternate solution) {
		this.bestSolution = solution;
		this.bestSolutionValue = bestSolution.getTotalCrossings();
	}
	
	public SolutionAlternate search() {
		return bestSolution;
	}
		
}
