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
	// private NeighbourhoodStructureEnum neighbourhoodType;
	// private StepFunctionEnum stepFunctionType;
	private INeighbourhood neighbourhood;

	public LocalSearch(SolutionAlternate solution, NeighbourhoodStructureEnum neighbourhoodType,
			StepFunctionEnum stepFunctionType) {
		this.bestSolution = solution;
		this.bestSolutionValue = bestSolution.getTotalCrossings();
		// this.neighbourhoodType = neighbourhoodType;
		// this.stepFunctionType = stepFunctionType;
		if (neighbourhoodType == NeighbourhoodStructureEnum.XOR) {
			this.neighbourhood = new NeighbourhoodSmall(stepFunctionType);
		}
		if (neighbourhoodType == NeighbourhoodStructureEnum.OR) {
			this.neighbourhood = new NeighbourhoodLarge(stepFunctionType);
		}
	}

	public SolutionAlternate search() {

		for (int i = 0; i < 1000; i++) {
			SolutionAlternate solutionNew = neighbourhood.move(bestSolution);
			int v1 = neighbourhood.getCurrentV1();
			int v2 = neighbourhood.getCurrentV2();
			int fromPage = bestSolution.getAdjacencyMatrix()[v1][v2];
			int toPage = neighbourhood.getNewPage();

			// System.out.println("Edge [" + (v1 + 1) + "," + (v2 + 1) + "] moved from " + fromPage + " to " + toPage);
			// System.out.println(bestSolution.calculateCrossingIncrease(v1, v2,
			// fromPage));
			// System.out.println(bestSolution.calculateCrossingIncrease(v1, v2,
			// toPage));

			int edgeRemovalCost = bestSolution.calculateCrossingIncrease(v1, v2, fromPage);
			if (edgeRemovalCost > 0) {
				int edgeAdditionCost = bestSolution.calculateCrossingIncrease(v1, v2, toPage);
				if (edgeAdditionCost < edgeRemovalCost) {
					bestSolution = solutionNew;
					bestSolution.getCrossingsList().set(fromPage,
							bestSolution.getCrossingsList().get(fromPage) - edgeRemovalCost);
					bestSolution.getCrossingsList().set(toPage,
							bestSolution.getCrossingsList().get(toPage) + edgeAdditionCost);
					// System.out.println("UPDATE! " + i);
				}
			}
		}

		return bestSolution;
	}

	public SolutionAlternate getBestSolution() {
		return bestSolution;
	}

}
