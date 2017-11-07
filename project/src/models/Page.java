package models;

import java.util.ArrayList;
import java.util.List;

public class Page {

	private boolean[][] adjacencyMatrix;
	private List<List<Integer>> adjacencyList;
	// number of crossings on this page, update this value when new edge is
	// added
	// incremental evaluation? OMG !
	private int crossingN = 0;
	private int edgeN = 0;

	public Page(int vertexNumber) {
		this.adjacencyMatrix = new boolean[vertexNumber][vertexNumber];

		this.adjacencyList = new ArrayList<List<Integer>>();
		for (int i = 0; i < vertexNumber; i++) {
			this.adjacencyList.add(new ArrayList<Integer>());
		}
	}

	// call from solution
	// MAYBE NOT NEEDED IF INCREMENTAL EVALUATION IS USED
	public int calculateCrossingNumber(List<Integer> spineOrder) {
		return 0;
	}

	public int calculateCrossingIncrease_M(int v1, int v2, List<Integer> spineOrder) {
		int newCrossings = 0;
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = i + 1; j < adjacencyMatrix[i].length; j++) {
				if (adjacencyMatrix[i][j]) {
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

	public int calculateCrossingIncrease_L(int v1, int v2, List<Integer> spineOrder) {
		int newCrossings = 0;
		for (int i = 0; i < adjacencyList.size(); i++) {
			for (int j = 0; j < adjacencyList.get(i).size(); j++) {
				int w1 = i;
				int w2 = adjacencyList.get(i).get(j);
				// System.out.println("checking... " + w1 + " " + w2);
				int v1Index = spineOrder.indexOf(v1);
				int v2Index = spineOrder.indexOf(v2);
				int iIndex = spineOrder.indexOf(w1);
				int jIndex = spineOrder.indexOf(w2);
				if (isCrossing(v1Index, v2Index, iIndex, jIndex)) {
					newCrossings = newCrossings + 1;
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

	/*
	 * public boolean isEdgeInList(int v1, int v2) { return
	 * //adjacencyList.get(v1).contains(v2) ||
	 * adjacencyList.get(v2).contains(v1); }
	 */
	public void addEdge_M(int v1, int v2) {
		this.adjacencyMatrix[v1][v2] = true;
		this.adjacencyMatrix[v2][v1] = true;
	}

	public void addEdge_L(int v1, int v2) {
		this.adjacencyList.get(v1).add(v2);
		// this.adjacencyList.get(v2).add(this.adjacencyList.get(v2).size(),v1);
		this.edgeN++;
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

	public List<List<Integer>> getAdjacencyList() {
		return adjacencyList;
	}

	public void setAdjacencyList(List<List<Integer>> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}
	
	public int getEdgeN() {
		return this.edgeN;
	}

	public String toString() {
		String matrix = "";
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix[i].length; j++) {
				if (adjacencyMatrix[i][j]) {
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
