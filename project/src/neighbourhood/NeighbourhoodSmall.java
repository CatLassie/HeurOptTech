package neighbourhood;

import models.SolutionAlternate;
import util.StepFunctionEnum;

public class NeighbourhoodSmall implements INeighbourhood {
	private StepFunctionEnum stepFunctionType;

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

	SolutionAlternate moveRandom(SolutionAlternate solution) {
		return solution;
	}

	SolutionAlternate moveFirstImprovement(SolutionAlternate solution) {
		return solution;
	}

	SolutionAlternate moveBestImprovement(SolutionAlternate solution) {
		return solution;
	}

}
