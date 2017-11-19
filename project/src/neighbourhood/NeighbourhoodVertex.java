package neighbourhood;

import java.util.ArrayList;
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
		List<Integer> spineOrder = solution.getSpineOrder();
		int vertexN = spineOrder.size();
		Solution betterSolution = solution;
		isSolutionUpdated = false;

		firstImprovement: for (int i = 0; i < vertexN; i++) {
			for (int j = 0; j < vertexN; j++) {

				Solution solutionNew = betterSolution.copy();
				int fromValue = solutionNew.getSpineOrder().get(i);
				solutionNew.getSpineOrder().remove(i);
				solutionNew.getSpineOrder().add(j, fromValue);
				
				List<Integer> newCrossingsList = solutionNew.calculateTotalCrossingArray();
				solutionNew.setCrossingsList(newCrossingsList);
				if(solutionNew.getTotalCrossings() < betterSolution.getTotalCrossings()){
					betterSolution = solutionNew;
					isSolutionUpdated = true;
					break firstImprovement;
				}
				
			}
		}

		return betterSolution;
	}

	Solution moveBestImprovement(Solution solution) {
		List<Integer> spineOrder = solution.getSpineOrder();
		int vertexN = spineOrder.size();
		Solution bestSolution = solution;
		isSolutionUpdated = false;

		List<BestImprovementVertexRunnable> runnableList = new ArrayList<>();
		List<Thread> workers = new ArrayList<>();
		for (int i = 0; i < vertexN; i++) {		
			BestImprovementVertexRunnable b = new BestImprovementVertexRunnable(solution, i);
			Thread t = new Thread(b);
			t.start();
			runnableList.add(b);
			workers.add(t);
		}
		
		for (int i = 0; i < workers.size(); i++) {
			try {
				workers.get(i).join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			BestImprovementVertexRunnable r = runnableList.get(i);
			if (r.getSolution().getTotalCrossings() < bestSolution.getTotalCrossings()) {
				bestSolution = r.getSolution();
				isSolutionUpdated = true;
			}
		}
		
		return bestSolution;
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
