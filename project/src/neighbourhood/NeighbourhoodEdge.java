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
		int randomV1 = 0;
		int randomV2 = 0;
		int randomPage;
		do {
			randomV1 = ThreadLocalRandom.current().nextInt(0, vertexN);
			randomV2 = ThreadLocalRandom.current().nextInt(0, vertexN);
		} while(matrix[randomV1][randomV2] == -1);
		do {
			randomPage = ThreadLocalRandom.current().nextInt(0, pageN);
		} while(randomPage == matrix[randomV1][randomV2]);
		
		Solution solutionNew = solution.copy();
		solutionNew.getAdjacencyMatrix()[randomV1][randomV2] = randomPage;
		
		this.selectedV1 = randomV1;
		this.selectedV2 = randomV2;
		this.selectedPage = randomPage;
		// this.fromPage = matrix[v1][v2];
		
		return solutionNew;
	}
	
	
	
	
	
	

	Solution moveFirstImprovement(Solution solution) {
		return solution;
	}
	
	
	
	
	
	
	
	
	
	

	Solution moveBestImprovement(Solution solution) {
		return solution;
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

}
