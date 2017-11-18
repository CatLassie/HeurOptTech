package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solution {
	private List<Integer> spineOrder;
	private int pageNumber;
	private int[][] adjacencyMatrix;
	private List<Integer> crossingsList;
	private int edgeNumber;
	
	public Solution(int vertexNumber, int pageNumber, boolean isRandom) {
		spineOrder = new ArrayList<>();
		this.pageNumber = pageNumber;
		this.adjacencyMatrix = new int[vertexNumber][vertexNumber];
		for(int i = 0; i < vertexNumber; i++) {
			spineOrder.add(i);
			for(int j = 0; j < vertexNumber; j++) {
				adjacencyMatrix[i][j] = -1;
			}
		}
		crossingsList = new ArrayList<>();
		for(int i = 0; i < pageNumber; i++) {
			crossingsList.add(0);
		}
		if(isRandom) {
			Collections.shuffle(spineOrder);	
		}
		// System.out.println("spine order is: " + spineOrder);
	}
	
	public Solution(List<Integer> spineOrder, int pageNumber,
			int[][] adjacencyMatrix, List<Integer> crossingsList, int edgeNumber) {
		this.spineOrder = spineOrder;
		this.pageNumber = pageNumber;
		this.adjacencyMatrix = adjacencyMatrix;
		this.crossingsList = crossingsList;
		this.edgeNumber = edgeNumber;
	}
	
	// calculation of objective function value from scratch
	public List<Integer> calculateTotalCrossingArray(){
		List<Integer> crossingsList = new ArrayList<>();
		for(int i = 0; i < pageNumber; i++) { crossingsList.add(0); }
				
		// edge to check
		for (int v1 = 0; v1 < adjacencyMatrix.length; v1++) {
			for (int v2 = v1 + 1; v2 < adjacencyMatrix[v1].length; v2++) {
				int pageNofV = adjacencyMatrix[v1][v2];
				if (pageNofV > -1) {
					
					// against all other remaining edges
					int w2 = v2+1;
					for (int w1 = v1; w1 < adjacencyMatrix.length; w1++) {
						for (; w2 < adjacencyMatrix[w1].length; w2++) {
							int pageNofW = adjacencyMatrix[w1][w2];
							if(pageNofV == pageNofW) {
								int v1Index = spineOrder.indexOf(v1);
								int v2Index = spineOrder.indexOf(v2);
								int w1Index = spineOrder.indexOf(w1);
								int w2Index = spineOrder.indexOf(w2);
								if (isCrossing(v1Index, v2Index, w1Index, w2Index)) {
									int newCrossingNumber = crossingsList.get(pageNofV) + 1;
									crossingsList.set(pageNofV, newCrossingNumber);
								}
								
							}
						}
						w2 = w1+2;
					}
					
				}
			}
		}
		
		return crossingsList;
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
	
	public List<Integer> calculateCrossingIncreaseArray(int v1, int v2) {
		List<Integer> crossingsIncreaseList = new ArrayList<>();
		for(int i = 0; i < pageNumber; i++) { crossingsIncreaseList.add(0); }
		int newCrossings = 0;
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = i + 1; j < adjacencyMatrix[i].length; j++) {
				int pageN = adjacencyMatrix[i][j];
				if (pageN > -1) {
					// crossingsIncreaseList = new ArrayList<>(pageNumber);
					int v1Index = spineOrder.indexOf(v1);
					int v2Index = spineOrder.indexOf(v2);
					int iIndex = spineOrder.indexOf(i);
					int jIndex = spineOrder.indexOf(j);
					if (isCrossing(v1Index, v2Index, iIndex, jIndex)) {
						newCrossings = crossingsIncreaseList.get(pageN) + 1;
						crossingsIncreaseList.set(pageN, newCrossings);
					}
				}
			}
		}
		return crossingsIncreaseList;
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
	
	public int getPageNumber() {
		return pageNumber;
	}
	
	public int getTotalCrossings() {
		int totalCrossings = 0;
		for(int  i = 0; i < crossingsList.size(); i++) {
			totalCrossings += crossingsList.get(i);
		}
		return totalCrossings;
	}
	
	public int getEdgeNumber() {
		return edgeNumber;
	}

	public void setEdgeNumber(int edgeNumber) {
		this.edgeNumber = edgeNumber;
	}
	
	public Solution copy() {
		List<Integer> spineOrderCopy = new ArrayList<>(spineOrder);
		int[][] adjacencyMatrixCopy = new int[adjacencyMatrix.length][adjacencyMatrix.length];
		for(int i = 0; i < adjacencyMatrix.length; i++) {
			adjacencyMatrixCopy[i] = adjacencyMatrix[i].clone();
		}
		List<Integer> crossingsListCopy = new ArrayList<>(crossingsList);
		return new Solution(spineOrderCopy, pageNumber, adjacencyMatrixCopy, crossingsListCopy, edgeNumber);
	}
	
	public String toString() {
		String matrix = "\n";
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix[i].length; j++) {
				if(adjacencyMatrix[i][j] == -1){
					matrix += "*" + " ";
				} else {
					matrix += adjacencyMatrix[i][j] + " ";	
				}
			}
			matrix += "\n";
		}
		return matrix;
	}

}
