package neighbourhood;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import models.Solution;
import util.StepFunctionEnum;

public class NeighbourhoodVertex implements INeighbourhood {
	private StepFunctionEnum stepFunctionType;
	private int selectedFromPosition = 0;
	private int selectedToPosition = 0;
	private boolean isSolutionUpdated = false;
	
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
		List<Integer> spineOrder = solution.getSpineOrder();
		int vertexN = spineOrder.size();
		int fromPosition = 0;
		int toPosition = 0;
		
		fromPosition = ThreadLocalRandom.current().nextInt(0, vertexN);
		do {
			toPosition = ThreadLocalRandom.current().nextInt(0, vertexN);
		} while (fromPosition == toPosition);

		Solution solutionNew = solution.copy();
		int fromValue = solutionNew.getSpineOrder().get(fromPosition);
		solutionNew.getSpineOrder().remove(fromPosition);
		solutionNew.getSpineOrder().add(toPosition, fromValue);
		this.selectedFromPosition = fromPosition;
		this.selectedToPosition = toPosition;

		// System.out.println("from "+fromPosition+" to "+toPosition);
		return solutionNew;
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
		return isSolutionUpdated;
	}

	public int getSelectedFromPosition() {
		return selectedFromPosition;
	}

	public int getSelectedToPosition() {
		return selectedToPosition;
	}


}
