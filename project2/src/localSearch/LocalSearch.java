package localSearch;

import models.Solution;
import neighbourhood.INeighbourhood;
import neighbourhood.NeighbourhoodEdge;
import util.NeighbourhoodStructureEnum;
import util.StepFunctionEnum;
import util.Utilities;

public class LocalSearch implements ILocalSearch {

	private Solution currentSolution;
	// private int currentSolutionValue;
	private NeighbourhoodStructureEnum neighbourhoodType;
	private StepFunctionEnum stepFunctionType;
	private INeighbourhood neighbourhood;

	public LocalSearch(Solution solution, NeighbourhoodStructureEnum neighbourhoodType,
			StepFunctionEnum stepFunctionType) {
		this.currentSolution = solution;
		// this.currentSolutionValue = currentSolution.getTotalCrossings();
		this.neighbourhoodType = neighbourhoodType;
		this.stepFunctionType = stepFunctionType;
		if (neighbourhoodType == NeighbourhoodStructureEnum.EDGE) {
			this.neighbourhood = new NeighbourhoodEdge(stepFunctionType);
		}
		if (neighbourhoodType == NeighbourhoodStructureEnum.VERTEX) {
			this.neighbourhood = null; //new NeighbourhoodVertex(stepFunctionType);
		}
	}

	public Solution search() {
		if(stepFunctionType == StepFunctionEnum.RANDOM){
			if(neighbourhoodType == NeighbourhoodStructureEnum.EDGE) {
				return searchRandomEdge();	
			} else {
				return searchRandomVertex();
			}
		} else {
			if(neighbourhoodType == NeighbourhoodStructureEnum.EDGE) {
				//return searchDeterministicEdge();	
				return null;
			}else{
				return searchDeterministicVertex();
			}
		}
	}
	
	// RANDOM EDGE
	private Solution searchRandomEdge() {
		timeout:
		for (int i = 0; i < currentSolution.getEdgeNumber(); i++) {
			if(Utilities.isTimeOver()){
				System.out.println("Local Search time is up!");
				break timeout;
			}
			Solution solutionNew = neighbourhood.move(currentSolution);
			int v1 = neighbourhood.getSelectedV1();
			int v2 = neighbourhood.getSelectedV2();
			int fromPage = currentSolution.getAdjacencyMatrix()[v1][v2];
			int toPage = neighbourhood.getSelectedPage();

			int edgeRemovalCost = currentSolution.calculateCrossingIncrease(v1, v2, fromPage);
			if (edgeRemovalCost > 0) {
				int edgeAdditionCost = currentSolution.calculateCrossingIncrease(v1, v2, toPage);
				if (edgeAdditionCost < edgeRemovalCost) {
					currentSolution = solutionNew;
					int fromPageCrossings = currentSolution.getCrossingsList().get(fromPage);
					int toPageCrossings = currentSolution.getCrossingsList().get(toPage);
					currentSolution.getCrossingsList().set(fromPage, fromPageCrossings - edgeRemovalCost);
					currentSolution.getCrossingsList().set(toPage, toPageCrossings + edgeAdditionCost);
					// System.out.println("UPDATE! " + i);
				}
			}
		}
		return currentSolution;
	}
	
	// DETERMINISTIC EDGE
	/*
	private Solution searchDeterministicEdge() {
		Solution solutionNew;
		timeout:
		do{
			if(Utilities.isTimeOver()){
				System.out.println("Local Search time is up!");
				break timeout;
			}
			
			// long startTimeNano = System.nanoTime();
			// long startTime = System.currentTimeMillis();
					
			solutionNew = neighbourhood.move(currentSolution);
			
			// long endTimeNano = System.nanoTime();
			// long endTime = System.currentTimeMillis();
			// double diffSec = ((double) endTime - startTime)/1000;
			// System.out.println("DETERMINISTIC IMPROVEMENT EDGE STEP TOOK: " + diffSec + " sec " + (endTimeNano - startTimeNano));
			
			if(solutionNew.getTotalCrossings() < currentSolution.getTotalCrossings()){
				currentSolution = solutionNew;
			}
		} while(neighbourhood.isSolutionUpdated());
		return currentSolution;
	}
	*/
	
	// RANDOM VERTEX
	private Solution searchRandomVertex() {
		return null;
	}
	
	// DETERMINISTIC VERTEX
	private Solution searchDeterministicVertex() {
		return null; 
	}

	public Solution getBestSolution() {
		return currentSolution;
	}

	public NeighbourhoodStructureEnum getNeighbourhoodType() {
		return neighbourhoodType;
	}

	public StepFunctionEnum getStepFunctionType() {
		return stepFunctionType;
	}

}
