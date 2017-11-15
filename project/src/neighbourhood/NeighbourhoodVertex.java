package neighbourhood;

import models.Solution;
import util.StepFunctionEnum;

public class NeighbourhoodVertex implements INeighbourhood {
	private StepFunctionEnum stepFunctionType;
	// private int currentV1 = -1;
	// private int currentV2 = -1;
	// private int selectedPage = -1;
	
	public NeighbourhoodVertex(StepFunctionEnum stepFunctionType) {
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
	
	public int getSelectedV1() {
		return 0;
	}

	public int getSelectedV2() {
		return 0;
	}

	public int getSelectedPage() {
		return 0;
	}
	
	public boolean isSolutionUpdated() {
		return false;
	}

}
