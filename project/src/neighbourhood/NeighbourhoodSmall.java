package neighbourhood;

import java.util.concurrent.ThreadLocalRandom;

import models.SolutionAlternate;
import util.StepFunctionEnum;

public class NeighbourhoodSmall implements INeighbourhood {
	private StepFunctionEnum stepFunctionType;
	private int currentV1 = -1;
	private int currentV2 = -1;
	// public int fromPage = -1;
	private int newPage = -1;
	
	public NeighbourhoodSmall(StepFunctionEnum stepFunctionType) {
		this.stepFunctionType = stepFunctionType;
	}

	public SolutionAlternate move(SolutionAlternate solution) {
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
	SolutionAlternate moveRandom(SolutionAlternate solution) {
		int[][] matrix = solution.getAdjacencyMatrix();
		int vertexN = matrix.length;
		int pageN = solution.getPageNumber();
		int v1 = 0;
		int v2 = 0;
		int newPage;
		do {
			v1 = ThreadLocalRandom.current().nextInt(0, vertexN);
			v2 = ThreadLocalRandom.current().nextInt(0, vertexN);
		} while(matrix[v1][v2] == -1);
		do {
			newPage = ThreadLocalRandom.current().nextInt(0, pageN);
		} while(newPage == matrix[v1][v2]);
		
		SolutionAlternate solutionNew = solution.copy();
		solutionNew.getAdjacencyMatrix()[v1][v2] = newPage;
		
		this.currentV1 = v1;
		this.currentV2 = v2;
		// this.fromPage = matrix[v1][v2];
		this.newPage = solutionNew.getAdjacencyMatrix()[v1][v2];
		
		return solutionNew;
	}
	
	
	
	
	
	

	SolutionAlternate moveFirstImprovement(SolutionAlternate solution) {
		return solution;
	}
	
	
	
	
	
	
	
	
	
	

	SolutionAlternate moveBestImprovement(SolutionAlternate solution) {
		return solution;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public int getCurrentV1() {
		return currentV1;
	}

	public int getCurrentV2() {
		return currentV2;
	}

	public int getNewPage() {
		return newPage;
	}

}
