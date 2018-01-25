package models;

public class Ant {
	
	int antID;
	Solution currentSolution;
	
	public Ant(int antID ,Solution initialSolution) {
		this.currentSolution = initialSolution.copy();
		this.antID = antID;
	}
	
	public int getAntID() {
		return antID;
	}

	public Solution getCurrentSolution() {
		return currentSolution;
	}
	
	public String toString() {
		String info = "\nAnt "+antID+":\n";
		info = info + "ant's current solution: "+currentSolution.getTotalCrossings();
		return info;
	}

}
