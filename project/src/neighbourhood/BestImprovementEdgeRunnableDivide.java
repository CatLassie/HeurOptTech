package neighbourhood;

import models.Solution;

public class BestImprovementEdgeRunnableDivide implements Runnable {

	private Solution solution;
	public int bestV1, bestV2, pageN, fromI, fromJ, iterationN;
	private int crossingIncrease = -999;
	private int[][] matrix;
	private int bestFromPage = -1;
	private int bestToPage = -1;
	private int bestRemovalCost = 0;
	private int bestAdditionCost = 0;
	private boolean isSolutionUpdated = false;

	public BestImprovementEdgeRunnableDivide(Solution solution, int fromI, int fromJ, int iterationN) {
		this.solution = solution;
		this.fromI = fromI;
		this.fromJ = fromJ;
		this.iterationN = iterationN;
		this.pageN = solution.getPageNumber();
		this.matrix = solution.getAdjacencyMatrix();
		// this.fromPage = matrix[v1][v2];
	}

	public void run() {
		// System.out.println("Running with: " + fromI +" "+ fromJ);
		
		valueInitLoop:
		for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix[i].length; j++) {
				if (matrix[i][j] > -1) {
						bestV1 = i;
						bestV2 = j;
						bestFromPage = matrix[i][j];
						bestToPage = matrix[i][j];
						break valueInitLoop;
				}
			}
		}
		
		int l = 0;
		int j = fromJ;
		outerLoop:
		for (int i = fromI; i < matrix.length && l < iterationN; i++) {
			for (; j < matrix[i].length && l < iterationN; j++) {
				// System.out.println("HARR! "+ i +" "+ j);
				if (matrix[i][j] > -1) {
					if (bestV1 == -1) {
						bestV1 = i;
						bestV2 = j;
						bestFromPage = matrix[i][j];
						bestToPage = matrix[i][j];
					}
					int fromPage = matrix[i][j];
					int edgeRemovalCost = solution.calculateCrossingIncrease(i, j, fromPage);
					if (edgeRemovalCost > 0) {
						for (int k = 0; k < pageN; k++) {
							if (k != matrix[i][j]) {
								int toPage = k;
								int edgeAdditionCost = solution.calculateCrossingIncrease(i, j, toPage);
								if (edgeAdditionCost < edgeRemovalCost) {
									if ((edgeAdditionCost - edgeRemovalCost) < (bestAdditionCost - bestRemovalCost)) {
										bestV1 = i;
										bestV2 = j;
										bestFromPage = fromPage;
										bestToPage = toPage;
										bestRemovalCost = edgeRemovalCost;
										bestAdditionCost = edgeAdditionCost;
										isSolutionUpdated = true;
									}
								}
							}
						}
					}
				}
				l++;
				//if(l >= iterationN) {break outerLoop;}
			}
			j = i+2;
		}
		// System.out.println("Counster is: " + l);
		crossingIncrease = bestAdditionCost - bestRemovalCost;
	}
	
	public int getCurrentCrossingIncrease() {
		return crossingIncrease;
	}

	public int getV1() {
		return bestV1;
	}

	public int getV2() {
		return bestV2;
	}

	public int getFromPage() {
		return bestFromPage;
	}

	public int getBestToPage() {
		return bestToPage;
	}

	public int getBestRemovalCost() {
		return bestRemovalCost;
	}

	public int getBestAdditionCost() {
		return bestAdditionCost;
	}

	public boolean isSolutionUpdated() {
		return isSolutionUpdated;
	}

}
