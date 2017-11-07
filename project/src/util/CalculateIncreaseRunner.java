package util;

import models.Solution;

public class CalculateIncreaseRunner implements Runnable {

	Solution solution;
	int v1, v2, pageN;
	private int currentCrossingIncrease = -999;
	
	public CalculateIncreaseRunner(Solution solution, int v1, int v2, int pageN) {
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
