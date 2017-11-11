package models;

import java.util.ArrayList;
import java.util.List;

public class SolutionAlternate {
	
	private List<Integer> spineOrder;
	private int[][] adjacencyMatrix;
	private List<Integer> crossingsList;
	
	public SolutionAlternate(int vertexNumber, int pageNumber) {
		spineOrder = new ArrayList<>();		
		for(int i = 0; i < vertexNumber; i++) {
			spineOrder.add(i);
		}
		crossingsList = new ArrayList<>();
		for(int i = 0; i < pageNumber; i++) {
			crossingsList.add(0);
		}
		this.adjacencyMatrix = new int[vertexNumber][vertexNumber];		
	}
	
	public int calculateCrossingIncrease(int v1, int v2, int pageN) {
		int newCrossings = 0;
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = i + 1; j < adjacencyMatrix[i].length; j++) {
				if (adjacencyMatrix[i][j] == pageN) {
					int v1Index = spineOrder.indexOf(v1);
					int v2Index = spineOrder.indexOf(v2);
					int iIndex = spineOrder.indexOf(i);
					int jIndex = spineOrder.indexOf(j);
					if (isCrossing(v1Index, v2Index, iIndex, jIndex)) {
						newCrossings = newCrossings + 1;
					}
				}
			}
		}
		return newCrossings;
	}
	
	private boolean isCrossing(int newV1, int newV2, int v1, int v2) {
		if (newV1 < newV2) {
			if (v1 < v2) {
				return ((newV1 < v1) && (v1 < newV2) && (newV2 < v2)) || ((v1 < newV1) && (newV1 < v2) && (v2 < newV2));
			} else {
				return ((newV1 < v2) && (v2 < newV2) && (newV2 < v1)) || ((v2 < newV1) && (newV1 < v1) && (v1 < newV2));
			}
		} else {
			if (v1 < v2) {
				return ((newV2 < v1) && (v1 < newV1) && (newV1 < v2)) || ((v1 < newV2) && (newV2 < v2) && (v2 < newV1));
			} else {
				return ((newV2 < v2) && (v2 < newV1) && (newV1 < v1)) || ((v2 < newV2) && (newV2 < v1) && (v1 < newV1));
			}
		}
	}
	
	public void addEdge(int v1, int v2, int pageN) {
		this.adjacencyMatrix[v1][v2] = pageN;
		// this.adjacencyMatrix[v2][v1] = pageN;
	}
	
	public void addNewCrossings(int crossingIncrease, int pageN) {
		int crossingN = crossingsList.get(pageN) + crossingIncrease;
		crossingsList.set(pageN, crossingN);
	}
	
	public List<Integer> getSpineOrder() {
		return spineOrder;
	}

	public int[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
	
	public List<Integer> getCrossingsList() {
		return crossingsList;
	}
	
	public String toString() {
		String matrix = "\n";
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix[i].length; j++) {
				matrix += adjacencyMatrix[i][j] + " ";
			}
			matrix += "\n";
		}
		return matrix;
	}

}
