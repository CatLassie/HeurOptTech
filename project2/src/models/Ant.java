package models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Ant {
	
	int antID;
	boolean[][] adjacencyMatrix;
	Solution currentSolution;
	
	public Ant(int antID, boolean[][] adjacencyMatrix) {
		this.antID = antID;
		this.adjacencyMatrix = adjacencyMatrix;
	}
	
	public Solution generateSolution(Solution solution){
		this.currentSolution = solution.copy();
		
		int pageNumber = currentSolution.getPageNumber();
		int[][] matrix = currentSolution.getAdjacencyMatrix();
		int edgeNumber = 0;
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix[i].length; j++) {

				boolean currentEdge = adjacencyMatrix[i][j];
				if (currentEdge) {
					edgeNumber++;
					
					// Greedy Construction
					
					// greedily decide to which page to add the edge
					List<Integer> crossingIncrease = currentSolution.calculateCrossingIncreaseArray(i, j);
					int minCrossingIncrease = -1;
					int bestPage = -1;
					List<Integer> copyCrossingIncrease = new ArrayList<>(crossingIncrease);
					copyCrossingIncrease.sort((a, b) -> a - b);
					minCrossingIncrease = copyCrossingIncrease.get(0);
					bestPage = crossingIncrease.indexOf(minCrossingIncrease);
					currentSolution.addEdge(i, j, bestPage);
					currentSolution.addNewCrossings(minCrossingIncrease, bestPage);
					// System.out.println(minCrossingIncrease);
					
					
					// Random Construction
					/*
					int randomPage = ThreadLocalRandom.current().nextInt(0, pageNumber);
					// System.out.println("page #" + randomPage);
					int crossingIncrease = currentSolution.calculateCrossingIncrease(i,j,randomPage);
					// System.out.println(i+" "+j+" p: "+randomPage+" increase: "+crossingIncrease);
					currentSolution.addEdge(i, j, randomPage);
					currentSolution.addNewCrossings(crossingIncrease, randomPage);
					// System.out.println(minCrossingIncrease);
					*/
				}
				
			}
		}
		currentSolution.setEdgeNumber(edgeNumber);
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
