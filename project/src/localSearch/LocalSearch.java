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
	// private NeighbourhoodStructureEnum neighbourhoodType;
	// private StepFunctionEnum stepFunctionType;
	private INeighbourhood neighbourhood;

	public LocalSearch(Solution solution, NeighbourhoodStructureEnum neighbourhoodType,
			StepFunctionEnum stepFunctionType) {
		this.currentSolution = solution;
		this.currentSolutionValue = currentSolution.getTotalCrossings();
		// this.neighbourhoodType = neighbourhoodType;
		// this.stepFunctionType = stepFunctionType;
		if (neighbourhoodType == NeighbourhoodStructureEnum.EDGE) {
			this.neighbourhood = new NeighbourhoodEdge(stepFunctionType);
		}
		if (neighbourhoodType == NeighbourhoodStructureEnum.VERTEX) {
			this.neighbourhood = new NeighbourhoodVertex(stepFunctionType);
		}
	}

	public Solution search() {

		for (int i = 0; i < 1000; i++) {
			Solution solutionNew = neighbourhood.move(currentSolution);
			int v1 = neighbourhood.getCurrentV1();
			int v2 = neighbourhood.getCurrentV2();
			int fromPage = currentSolution.getAdjacencyMatrix()[v1][v2];
			int toPage = neighbourhood.getNewPage();

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
					currentSolution.getCrossingsList().set(fromPage,
							currentSolution.getCrossingsList().get(fromPage) - edgeRemovalCost);
					currentSolution.getCrossingsList().set(toPage,
							currentSolution.getCrossingsList().get(toPage) + edgeAdditionCost);
					// System.out.println("UPDATE! " + i);
				}
			}
		}

		return currentSolution;
	}

	public Solution getBestSolution() {
		return currentSolution;
	}

}
