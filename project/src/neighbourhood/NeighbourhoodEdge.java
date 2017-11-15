package neighbourhood;

import java.util.concurrent.ThreadLocalRandom;

import models.Solution;
import util.StepFunctionEnum;

public class NeighbourhoodEdge implements INeighbourhood {
	private StepFunctionEnum stepFunctionType;
	private int selectedV1 = -1;
	private int selectedV2 = -1;
	// public int fromPage = -1;
	private int selectedPage = -1;
	private boolean isSolutionUpdated = false;

	public NeighbourhoodEdge(StepFunctionEnum stepFunctionType) {
		this.stepFunctionType = stepFunctionType;
	}

	public Solution move(Solution solution) {
		switch (stepFunctionType) {
		case RANDOM:
			return moveRandom(solution);
		case FIRST_IMPROVEMENT:
			return moveFirstImprovement(solution);
		case BEST_IMPROVEMENT:
			return moveBestImprovement(solution);
		default: {
			System.out.println("Something has gone pretty bad here, fellas!");
			return solution;
		}
		}
	}

	// RANDOM STEP FUNCTION
	Solution moveRandom(Solution solution) {
		int[][] matrix = solution.getAdjacencyMatrix();
		int vertexN = matrix.length;
		int pageN = solution.getPageNumber();
		int randomV1 = -1;
		int randomV2 = -1;
		int randomPage = -1;
		do {
			randomV1 = ThreadLocalRandom.current().nextInt(0, vertexN);
			randomV2 = ThreadLocalRandom.current().nextInt(0, vertexN);
		} while (matrix[randomV1][randomV2] == -1);
		do {
			randomPage = ThreadLocalRandom.current().nextInt(0, pageN);
		} while (randomPage == matrix[randomV1][randomV2]);

		Solution solutionNew = solution.copy();
		solutionNew.getAdjacencyMatrix()[randomV1][randomV2] = randomPage;

		this.selectedV1 = randomV1;
		this.selectedV2 = randomV2;
		this.selectedPage = randomPage;
		// this.fromPage = matrix[v1][v2];

		return solutionNew;
	}

	// FIRST IMPROVEMENT STEP FUNCTION
	Solution moveFirstImprovement(Solution solution) {
		int[][] matrix = solution.getAdjacencyMatrix();
		int pageN = solution.getPageNumber();
		Solution solutionNew = solution;
		isSolutionUpdated = false;

		firstImprovement: for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix[i].length; j++) {
				if (matrix[i][j] > -1) {
					for (int k = 0; k < pageN; k++) {
						if (k != matrix[i][j]) {
							int fromPage = matrix[i][j];
							int toPage = k;
							int edgeRemovalCost = solution.calculateCrossingIncrease(i, j, fromPage);
							if (edgeRemovalCost > 0) {
								int edgeAdditionCost = solution.calculateCrossingIncrease(i, j, toPage);
								if (edgeAdditionCost < edgeRemovalCost) {
									int fromPageCrossings = solution.getCrossingsList().get(fromPage);
									int toPageCrossings = solution.getCrossingsList().get(toPage);
									solutionNew = solution.copy();
									solutionNew.getAdjacencyMatrix()[i][j] = toPage;
									solutionNew.getCrossingsList().set(fromPage, fromPageCrossings - edgeRemovalCost);
									solutionNew.getCrossingsList().set(toPage, toPageCrossings + edgeAdditionCost);
									this.selectedV1 = i;
									this.selectedV2 = j;
									this.selectedPage = toPage;
									isSolutionUpdated = true;
									break firstImprovement;
								}
							}
						}
					}
				}
			}
		}
		
		return solutionNew;
	}

	// BEST IMPROVEMENT STEP FUNCTION
	Solution moveBestImprovement(Solution solution) {
		int[][] matrix = solution.getAdjacencyMatrix();
		int pageN = solution.getPageNumber();
		int bestV1 = -1;
		int bestV2 = -1;
		int bestFromPage = -1;
		int bestToPage = -1;
		int bestRemovalCost = 0;
		int bestAdditionCost = 0;
		Solution solutionNew = solution;
		isSolutionUpdated = false;

		for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix[i].length; j++) {
				if (matrix[i][j] > -1) {
					if (bestV1 == -1) {
						bestV1 = i;
						bestV2 = j;
						bestFromPage = matrix[i][j];
						bestToPage = matrix[i][j];
					}
					for (int k = 0; k < pageN; k++) {
						if (k != matrix[i][j]) {
							int fromPage = matrix[i][j];
							int toPage = k;
							int edgeRemovalCost = solution.calculateCrossingIncrease(i, j, fromPage);
							if (edgeRemovalCost > 0) {
								int edgeAdditionCost = solution.calculateCrossingIncrease(i, j, toPage);
								if (edgeAdditionCost < edgeRemovalCost) {
									if ((edgeAdditionCost - edgeRemovalCost) < (bestAdditionCost - bestRemovalCost)) {
										bestV1 = i;
										bestV2 = j;
										bestFromPage = fromPage;
										bestToPage = toPage;
										bestRemovalCost = edgeRemovalCost;
										bestAdditionCost = edgeAdditionCost;
									}
								}
							}
						}
					}
				}
			}
		}

		if (bestAdditionCost < bestRemovalCost) {
			int fromPageCrossings = solution.getCrossingsList().get(bestFromPage);
			int toPageCrossings = solution.getCrossingsList().get(bestToPage);
			solutionNew = solution.copy();
			solutionNew.getAdjacencyMatrix()[bestV1][bestV2] = bestToPage;
			solutionNew.getCrossingsList().set(bestFromPage, fromPageCrossings - bestRemovalCost);
			solutionNew.getCrossingsList().set(bestToPage, toPageCrossings + bestAdditionCost);
			this.selectedV1 = bestV1;
			this.selectedV2 = bestV2;
			this.selectedPage = bestToPage;
			isSolutionUpdated = true;
		}

		return solutionNew;
	}

	public int getSelectedV1() {
		return selectedV1;
	}

	public int getSelectedV2() {
		return selectedV2;
	}

	public int getSelectedPage() {
		return selectedPage;
	}

	public boolean isSolutionUpdated() {
		return isSolutionUpdated;
	}

}
