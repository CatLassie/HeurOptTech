package neighbourhood;

import java.util.List;

import models.Solution;

public class BestImprovementVertexRunnable implements Runnable {

	private Solution solution;
	private int fromPosition;
	private boolean isSolutionUpdated = false;

	public BestImprovementVertexRunnable(Solution solution, int fromPosition) {
		this.solution = solution;
		this.fromPosition = fromPosition;
	}

	public void run() {
		List<Integer> spineOrder = solution.getSpineOrder();
		int vertexN = spineOrder.size();
		Solution bestSolution = solution;
		isSolutionUpdated = false;
		
		for (int i = 0; i < vertexN; i++) {
			Solution solutionNew = solution.copy();
			int fromValue = solutionNew.getSpineOrder().get(fromPosition);
			solutionNew.getSpineOrder().remove(fromPosition);
			solutionNew.getSpineOrder().add(i, fromValue);
	
			List<Integer> newCrossingsList = solutionNew.calculateTotalCrossingArray();
			solutionNew.setCrossingsList(newCrossingsList);
			if(solutionNew.getTotalCrossings() < bestSolution.getTotalCrossings()){
				bestSolution = solutionNew;
				isSolutionUpdated = true;
			}
		}

		solution = bestSolution;
	}

	public boolean isSolutionUpdated() {
		return isSolutionUpdated;
	}
	
	public Solution getSolution(){
		return solution;
	}

}
