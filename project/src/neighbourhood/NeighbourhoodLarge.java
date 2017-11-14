package neighbourhood;

import models.Solution;
import util.StepFunctionEnum;

public class NeighbourhoodLarge implements INeighbourhood {
	private StepFunctionEnum stepFunctionType;
	private int currentV1 = -1;
	private int currentV2 = -1;
	private int newPage = -1;
	
	public NeighbourhoodLarge(StepFunctionEnum stepFunctionType) {
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

	Solution moveRandom(Solution solution) {
		return solution;
	}

	Solution moveFirstImprovement(Solution solution) {
		return solution;
	}

	Solution moveBestImprovement(Solution solution) {
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
