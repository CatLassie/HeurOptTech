package models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Ant {
	
	int antID, vertexNumber, pageNumber;
	boolean[][] adjacencyMatrix;
	Solution currentSolution;
	
	public Ant(int antID, boolean[][] adjacencyMatrix, int vertexNumber, int pageNumber) {
		this.antID = antID;
		this.vertexNumber = vertexNumber;
		this.pageNumber = pageNumber;
		this.adjacencyMatrix = adjacencyMatrix;
	}
	
	public Solution generateSolution(PheromoneMatrix phMatrix){
		this.currentSolution = new Solution(vertexNumber, pageNumber, false);
		
		// System.out.println(phMatrix);
		int pageNumber = currentSolution.getPageNumber();
		int[][] matrix = currentSolution.getAdjacencyMatrix();
		int edgeNumber = 0;
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix[i].length; j++) {

				boolean currentEdge = adjacencyMatrix[i][j];
				if (currentEdge) {
					edgeNumber++;
					// Ant decision
					List<Integer> crossingIncrease = currentSolution.calculateCrossingIncreaseArray(i, j);
					double[] pheromones_ij = phMatrix.getValue()[i][j];
					int chosenPage = -1;
					chosenPage = decidePage(crossingIncrease, pheromones_ij);
					currentSolution.addEdge(i, j, chosenPage);
					currentSolution.addNewCrossings(crossingIncrease.get(chosenPage), chosenPage);
					// System.out.println(currentSolution);
					// System.out.println(crossingIncrease + " " + pheromones_ij[0]);
				}
				
			}
		}
		currentSolution.setEdgeNumber(edgeNumber);
		return currentSolution;
	}
	
	private int decidePage(List<Integer> crossingIncrease, double[] pheromones_ij) {
		int chosenPage = -1;
		double pSum = 0;
		for (int i = 0; i < crossingIncrease.size(); i++) {
			int inc = crossingIncrease.get(i);
			double ph = pheromones_ij[i];
			pSum += (1.0/(inc+0.1)) * ph;
		}
		
		/*
		double x = 0.0;
		for (int i = 0; i < crossingIncrease.size(); i++) {
			int inc = crossingIncrease.get(i);
			double ph = pheromones_ij[i];
		    x += ((1.0/(inc+0.1)) * ph)  /pSum;
		}
		System.out.println(x);
		*/
		
		
		double p = Math.random();
		// System.out.println(p);
		// System.out.println("\n"+pSum);
		double cumulativeProbability = 0;
		crossLoop:
		for (int i = 0; i < crossingIncrease.size(); i++) {
			int inc = crossingIncrease.get(i);
			double ph = pheromones_ij[i];
		    cumulativeProbability += ((1.0/(inc+0.1)) * ph)/pSum;
		    /*
		    System.out.println("page "+i);
		    System.out.println("prob "+(1/(inc+0.1))/pSum);
		    System.out.println("cumul "+cumulativeProbability);
		    System.out.println("inc "+inc);
		    */
		    chosenPage = i;
		    if (p <= cumulativeProbability) {
		        // chosenPage = i;
		        break crossLoop;
		    }
		}
		// System.out.println("chosen "+chosenPage);
		return chosenPage;
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
