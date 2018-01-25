package models;

public class Ant {
	
	int antID;
	Solution currentSolution;
	
	public Ant(int antID) {
		this.antID = antID;
	}
	
	public Solution generateSolution(Solution solution){
		this.currentSolution = solution.copy();
		
		return currentSolution;
	}
	
	public int getAntID() {
		return antID;
	}

	public Solution getCurrentSolution() {
		return currentSolution;
	}
	
	public String toString() {
		String info = "\nAnt "+antID+":\n";
		String solution = currentSolution != null ? Integer.toString(currentSolution.getTotalCrossings()) : "none";
		info = info + "ant's current solution: "+ solution;
		return info;
	}

}
