package models;

import java.util.List;

public class Page {

	private boolean[][] adjacencyMatrix;
	// number of crossings on this page, update this value when new edge is added
	// incremental evaluation? OMG !
	private int crossingN = 0;
	
	public Page(int vertexNumber) {
		this.adjacencyMatrix = new boolean[vertexNumber][vertexNumber];
	}
	
	// call from solution
	// MAYBE NOT NEEDED IF INCREMENTAL EVALUATION IS USED
	public int calculateCrossingNumber(List<Integer> spineOrder) {
		return 0;
	}
	
	public int calculateCrossingIncrease(int v1, int v2, List<Integer> spineOrder) {
		int newCrossings = 0;
		for(int i = 0; i < adjacencyMatrix.length; i++) {
			for(int j = i+1; j < adjacencyMatrix[i].length; j++) {
				if(adjacencyMatrix[i][j]) {
					int v1Index = spineOrder.indexOf(v1);
					int v2Index = spineOrder.indexOf(v2);
					int iIndex = spineOrder.indexOf(i);
					int jIndex = spineOrder.indexOf(j);
					if(isCrossing(v1Index, v2Index, iIndex, jIndex)){
						newCrossings = newCrossings + 1;
					}
				}
			}
		}		
		return newCrossings;
	}
	
	private boolean isCrossing(int newV1, int newV2, int v1, int v2) {
		if(newV1 < newV2) {
			if(v1 < v2) {
				return ((newV1 < v1) && (v1 < newV2) && (newV2 < v2)) ||
						   ((v1 < newV1) && (newV1 <  v2) && (v2 < newV2));					
			} else {
				return ((newV1 < v2) && (v2 < newV2) && (newV2 < v1)) ||
						   ((v2 < newV1) && (newV1 <  v1) && (v1 < newV2));			
			}
		} else {
			if(v1 < v2) {
				return ((newV2 < v1) && (v1 < newV1) && (newV1 < v2)) ||
						   ((v1 < newV2) && (newV2 <  v2) && (v2 < newV1));					
			} else {
				return ((newV2 < v2) && (v2 < newV1) && (newV1 < v1)) ||
						   ((v2 < newV2) && (newV2 <  v1) && (v1 < newV1));			
			}		
		}
	}
		
	public void addEdge(int v1, int v2) {
		this.adjacencyMatrix[v1][v2] = true;
		this.adjacencyMatrix[v2][v1] = true;
	}
		
	public void addNewCrossings(int crossingIncrease) {
		crossingN += crossingIncrease;
	}
	
	public boolean[][] getAdjacencyMatrix() {
		return adjacencyMatrix;
	}

	public void setAdjacencyMatrix(boolean[][] adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
	}
	
	public int getCrossingN() {
		return crossingN;
	}
	
	public String toString() {
		String matrix = "";
		for(int i = 0; i < adjacencyMatrix.length; i++) {
			for(int j = 0; j < adjacencyMatrix[i].length; j++) {
				if(adjacencyMatrix[i][j]){
					matrix += 1 + " ";
				} else {
					matrix += 0 + " ";
				}
			}
			matrix += "\n";
		}
		return matrix;
	}
	
}
