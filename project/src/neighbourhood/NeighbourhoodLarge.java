package neighbourhood;

import models.SolutionAlternate;
import util.StepFunctionEnum;

public class NeighbourhoodLarge implements INeighbourhood {
	private StepFunctionEnum stepFunctionType;
	private int currentV1 = -1;
	private int currentV2 = -1;
	private int newPage = -1;
	
	public NeighbourhoodLarge(StepFunctionEnum stepFunctionType) {
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

	SolutionAlternate moveRandom(SolutionAlternate solution) {
		return solution;
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
