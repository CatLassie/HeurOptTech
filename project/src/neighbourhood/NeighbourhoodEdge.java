package neighbourhood;

import java.util.ArrayList;
import java.util.List;
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
			return moveBestImprovementDivide(solution);
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
					int fromPage = matrix[i][j];
					int edgeRemovalCost = solution.calculateCrossingIncrease(i, j, fromPage);
					if (edgeRemovalCost > 0) {
						for (int k = 0; k < pageN; k++) {
							if (k != matrix[i][j]) {
								int toPage = k;
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

	// CONCURRENT BEST IMPROVEMENT STEP FUNCTION
	Solution moveBestImprovementConcurrent(Solution solution) {
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

		int edgeN = solution.getEdgeNumber();
		// int remainingSize = edgeN; (remainingSize > 100) ? 100 :
		// remainingSize;
		int threadListSize = (edgeN > 500) ? 500 : edgeN;
		int remainingSize = edgeN - threadListSize;
		/*
		 * for(int i = 0; i < threadListSize; i++) { }
		 */
		List<BestImprovementEdgeRunnableSlow> runnableList = new ArrayList<>();
		List<Thread> workers = new ArrayList<>();

		// int count = 0;
		int l = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix[i].length; j++) {
				if (matrix[i][j] > -1) {
					if (bestV1 == -1) {
						bestV1 = i;
						bestV2 = j;
						bestFromPage = matrix[i][j];
						bestToPage = matrix[i][j];
					}

					// System.out.println("count " + count++);
					// concurrent part should go here

					// System.out.println("l " + l);
					BestImprovementEdgeRunnableSlow b = new BestImprovementEdgeRunnableSlow(solution, i, j);
					Thread t = new Thread(b);
					t.start();
					runnableList.add(b);
					workers.add(t);
					l++;
					if (l >= threadListSize) {
						// System.out.println("else");
						for (int m = 0; m < threadListSize; m++) {
							try {
								workers.get(m).join();
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
							BestImprovementEdgeRunnableSlow r = runnableList.get(m);
							// System.out.println(r.getCurrentCrossingIncrease());
							if (r.getCurrentCrossingIncrease() < 0) {
								if ((r.getCurrentCrossingIncrease()) < (bestAdditionCost - bestRemovalCost)) {
									bestV1 = r.getV1();
									bestV2 = r.getV2();
									bestFromPage = r.getFromPage();
									bestToPage = r.getBestToPage();
									bestRemovalCost = r.getBestRemovalCost();
									bestAdditionCost = r.getBestAdditionCost();

								}
							}
						}
						// System.out.println("TS "+threadListSize+ " RS
						// "+remainingSize);
						l = 0;
						workers = new ArrayList<>();
						runnableList = new ArrayList<>();
						threadListSize = (remainingSize - threadListSize > 0) ? threadListSize : remainingSize;
						remainingSize = remainingSize - threadListSize;
					}
				}
			}
		}
		// System.out.println("TS "+threadListSize+ " RS "+remainingSize);

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

	// CONCURRENT DIVIDE AND CONQUER STYLE BEST IMPROVEMENT STEP FUNCTION
	Solution moveBestImprovementDivide(Solution solution) {
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

		valueInitLoop: for (int i = 0; i < matrix.length; i++) {
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

		int edgeN = solution.getEdgeNumber();
		// int threadListSize = (edgeN > 500) ? 500 : edgeN;
		// int remainingSize = edgeN - threadListSize;

		// thread list size is the square root of traveresed vertices (possible
		// edge number, including empty edges)
		int possibleEdgeN = (matrix.length * (matrix.length - 1)) / 2;
		// int threadListSize = (int) Math.sqrt(possibleEdgeN);
		//int iterationN = possibleEdgeN / threadListSize + 1; // +1 is NOT gonna work with a constant threadListSize!!!!
		int iterationN = (int) Math.sqrt(possibleEdgeN);
		
		//     System.out.println("thread list size: " + threadListSize);
		//     System.out.println("vertex number: " + matrix.length);
		System.out.println("possible edge N: " + possibleEdgeN);
		//     System.out.println("edge N: " + edgeN);
		System.out.println("iterationN: " + iterationN);

		List<BestImprovementEdgeRunnableDivide> runnableList = new ArrayList<>();
		List<Thread> workers = new ArrayList<>();

		// int count = 0;
		// int l = 0;
		
		int iIncrease = 0;
		// int j = 1;
		int jOffset = 0;
		mainLoop: for (int i = 0; i < matrix.length; i += iIncrease) {
			// System.out.println("outer: " + i);
			// j = i+1+jOffset;
			int j = i+1+jOffset;
			for (;j < matrix.length; j += iterationN) {
				// System.out.println("i " + i + " j " + j /*+ " iterationN " + iterationN*/);
				// System.out.println("j " + (i < j));

				BestImprovementEdgeRunnableDivide b = new BestImprovementEdgeRunnableDivide(solution, i, j, iterationN);
				Thread t = new Thread(b);
				t.start();
				runnableList.add(b);
				workers.add(t);

			}
			iIncrease = j / matrix.length;
			jOffset = j % matrix.length;
			// j = i+1+ +iIncrease + jOffset;
			//System.out.println("iIncrease " + iIncrease + " jOffset " + jOffset /* + " j " + j*/);
		}

		// System.out.println("WORKER SIZE " + workers.size() + " " + iterationN);
		for (int m = 0; m < workers.size() /*threadListSize*/; m++) {
			// System.out.println("IM IN THREAD WAITING LOOP! " + workers.size());
			try {
				workers.get(m).join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			BestImprovementEdgeRunnableDivide r = runnableList.get(m);
			// System.out.println(r.fromI +" "+ r.fromJ +" "+r.getCurrentCrossingIncrease() );
			if (r.getCurrentCrossingIncrease() < 0) {
				if ((r.getCurrentCrossingIncrease()) < (bestAdditionCost - bestRemovalCost)) {
					bestV1 = r.getV1();
					bestV2 = r.getV2();
					bestFromPage = r.getFromPage();
					bestToPage = r.getBestToPage();
					bestRemovalCost = r.getBestRemovalCost();
					bestAdditionCost = r.getBestAdditionCost();
				}
			}
		}

		// System.out.println("TS "+threadListSize+ " RS "+remainingSize);

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
