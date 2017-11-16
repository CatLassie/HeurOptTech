package util;

import models.Solution;

public class BestImprovementRunnable implements Runnable {

	private Solution solution;
	private int v1, v2, pageN;
	private int crossingIncrease = -999;
	private int[][] matrix;
	private int fromPage = -1;
	private int bestToPage = -1;
	private int bestRemovalCost = 0;
	private int bestAdditionCost = 0;
	private boolean isSolutionUpdated = false;

	public BestImprovementRunnable(Solution solution, int v1, int v2) {
		this.solution = solution;
		this.v1 = v1;
		this.v2 = v2;
		this.pageN = solution.getPageNumber();
		this.matrix = solution.getAdjacencyMatrix();
		this.fromPage = matrix[v1][v2];
	}

	public void run() {
		// System.out.println("thread " + v1 + " " + v2);

		int edgeRemovalCost = solution.calculateCrossingIncrease(v1, v2, fromPage);

		if (edgeRemovalCost > 0) {
			for (int k = 0; k < pageN; k++) {
				if (k != fromPage) {
					// int fromPage = matrix[i][j];
					int toPage = k;

					// if (edgeRemovalCost > 0) {

					int edgeAdditionCost = solution.calculateCrossingIncrease(v1, v2, toPage);
					if (edgeAdditionCost < edgeRemovalCost) {
						if ((edgeAdditionCost - edgeRemovalCost) < (bestAdditionCost - bestRemovalCost)) {
							// bestV1 = i;
							// bestV2 = j;
							// bestFromPage = fromPage;
							bestToPage = toPage;
							bestRemovalCost = edgeRemovalCost;
							bestAdditionCost = edgeAdditionCost;
							isSolutionUpdated = true;
						}
					}

					// }
				}
			}
		}

		crossingIncrease = bestAdditionCost - bestRemovalCost;
	}

	public int getCurrentCrossingIncrease() {
		return crossingIncrease;
	}

	public int getV1() {
		return v1;
	}

	public int getV2() {
		return v2;
	}

	public int getFromPage() {
		return fromPage;
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
