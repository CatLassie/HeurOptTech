package localSearch;

import models.SolutionAlternate;
import neighbourhood.INeighbourhood;
import neighbourhood.NeighbourhoodLarge;
import neighbourhood.NeighbourhoodSmall;
import util.NeighbourhoodStructureEnum;
import util.StepFunctionEnum;

public class LocalSearch implements ILocalSearch {

	private SolutionAlternate bestSolution;
	private int bestSolutionValue;
	//private NeighbourhoodStructureEnum neighbourhoodType;
	//private StepFunctionEnum stepFunctionType;
	private INeighbourhood neighbourhood;

	public LocalSearch(SolutionAlternate solution, NeighbourhoodStructureEnum neighbourhoodType,
			StepFunctionEnum stepFunctionType) {
		this.bestSolution = solution;
		this.bestSolutionValue = bestSolution.getTotalCrossings();
		//this.neighbourhoodType = neighbourhoodType;
		//this.stepFunctionType = stepFunctionType;
		if(neighbourhoodType == NeighbourhoodStructureEnum.XOR){
			this.neighbourhood = new NeighbourhoodSmall(stepFunctionType);
		}
		if(neighbourhoodType == NeighbourhoodStructureEnum.OR){
			this.neighbourhood = new NeighbourhoodLarge(stepFunctionType);
		}
	}

	public SolutionAlternate search() {
		// System.out.println(neighbourhood);
		return neighbourhood.move(bestSolution);
	}	
	
}
