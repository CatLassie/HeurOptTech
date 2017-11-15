package localSearch;

import models.Solution;
import neighbourhood.INeighbourhood;
import neighbourhood.NeighbourhoodVertex;
import neighbourhood.NeighbourhoodEdge;
import util.NeighbourhoodStructureEnum;
import util.StepFunctionEnum;

public class LocalSearch implements ILocalSearch {

	private Solution currentSolution;
	private int currentSolutionValue;
	private NeighbourhoodStructureEnum neighbourhoodType;
	private StepFunctionEnum stepFunctionType;
	private INeighbourhood neighbourhood;

	public LocalSearch(Solution solution, NeighbourhoodStructureEnum neighbourhoodType,
			StepFunctionEnum stepFunctionType) {
		this.currentSolution = solution;
		this.currentSolutionValue = currentSolution.getTotalCrossings();
		this.neighbourhoodType = neighbourhoodType;
		this.stepFunctionType = stepFunctionType;
		if (neighbourhoodType == NeighbourhoodStructureEnum.EDGE) {
			this.neighbourhood = new NeighbourhoodEdge(stepFunctionType);
		}
		if (neighbourhoodType == NeighbourhoodStructureEnum.VERTEX) {
			this.neighbourhood = new NeighbourhoodVertex(stepFunctionType);
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
				return searchDeterministicEdge();	
			}else{
				return searchDeterministicVertex();
			}
		}
	}
	
	private Solution searchRandomEdge() {
		for (int i = 0; i < currentSolution.getEdgeNumber(); i++) {
			Solution solutionNew = neighbourhood.move(currentSolution);
			int v1 = neighbourhood.getSelectedV1();
			int v2 = neighbourhood.getSelectedV2();
			int fromPage = currentSolution.getAdjacencyMatrix()[v1][v2];
			int toPage = neighbourhood.getSelectedPage();

			// System.out.println("Edge [" + (v1 + 1) + "," + (v2 + 1) + "] moved from " + fromPage + " to " + toPage);
			// System.out.println(bestSolution.calculateCrossingIncrease(v1, v2,
			// fromPage));
			// System.out.println(bestSolution.calculateCrossingIncrease(v1, v2,
			// toPage));

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
	
	private Solution searchDeterministicEdge() {
		Solution solutionNew;
		do{
			solutionNew = neighbourhood.move(currentSolution);
			// System.out.println("UPDATE! " + solutionNew.getTotalCrossings() + " " + currentSolution.getTotalCrossings());
			if(solutionNew.getTotalCrossings() < currentSolution.getTotalCrossings()){
				currentSolution = solutionNew;
			}
		} while(neighbourhood.isSolutionUpdated());
		return currentSolution;
	}
	
	private Solution searchRandomVertex() {
		return currentSolution;
	}
	
	private Solution searchDeterministicVertex() {
		return currentSolution;
	}

	public Solution getBestSolution() {
		return currentSolution;
	}

}
