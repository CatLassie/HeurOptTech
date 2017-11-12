package localSearch;

import models.SolutionAlternate;
import util.NeighbourhoodStructureEnum;
import util.StepFunctionEnum;

public class LocalSearch implements ILocalSearch {

	private SolutionAlternate bestSolution;
	private int bestSolutionValue;
	private NeighbourhoodStructureEnum neighbourhoodType;
	private StepFunctionEnum stepFunctionType;
	
	public LocalSearch(SolutionAlternate solution, NeighbourhoodStructureEnum neighbourhoodType, StepFunctionEnum stepFunctionType) {
		this.bestSolution = solution;
		this.bestSolutionValue = bestSolution.getTotalCrossings();
		this.neighbourhoodType = neighbourhoodType;
		this.stepFunctionType = stepFunctionType;
	}
	
	public SolutionAlternate search() {
		return bestSolution;
	}
		
}
