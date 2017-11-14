package util.old;

import models.old.SolutionOld;

public class CalculateIncreaseRunnerOld implements Runnable {

	SolutionOld solution;
	int v1, v2, pageN;
	private int currentCrossingIncrease = -999;
	
	public CalculateIncreaseRunnerOld(SolutionOld solution, int v1, int v2, int pageN) {
		this.solution = solution;
		this.v1 = v1;
		this.v2 = v2;
		this.pageN = pageN;
	}
	
	public void run() {
		//System.out.println("thread " + pageN);
		currentCrossingIncrease = solution.calculateCrossingIncrease_L(v1, v2, pageN);
	}
	
	public int getCurrentCrossingIncrease() {
		return currentCrossingIncrease;
	}
	
}
