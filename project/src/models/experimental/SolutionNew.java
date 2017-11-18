package models.experimental;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SolutionNew {
	private List<Integer> spineOrder;
	private int pageNumber;
	private List<List<HalfEdge>> adjacencyList;
	private List<Integer> crossingsList;
	private int edgeNumber;
	
	public SolutionNew(int vertexNumber, int pageNumber, boolean isRandom) {
		spineOrder = new ArrayList<>();
		this.pageNumber = pageNumber;
		this.adjacencyList = new ArrayList<List<HalfEdge>>();
		for(int i = 0; i < vertexNumber; i++) {
			spineOrder.add(i);
			this.adjacencyList.add(new ArrayList<HalfEdge>());
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
	
	public SolutionNew(List<Integer> spineOrder, int pageNumber,
			List<List<HalfEdge>> adjacencyList, List<Integer> crossingsList, int edgeNumber) {
		this.spineOrder = spineOrder;
		this.pageNumber = pageNumber;
		this.adjacencyList = adjacencyList;
		this.crossingsList = crossingsList;
		this.edgeNumber = edgeNumber;
	}
				
	public int calculateCrossingIncrease(int v1, int v2, int pageN) {
		int newCrossings = 0;
		for (int i = 0; i < adjacencyList.size(); i++) {
			for (int j = 0; j < adjacencyList.get(i).size(); j++) {
				int w1 = i;
				int w2 = adjacencyList.get(i).get(j).v2;
				// System.out.println("checking... " + w1 + " " + w2);
				if (adjacencyList.get(i).get(j).p == pageN) {
					int v1Index = spineOrder.indexOf(v1);
					int v2Index = spineOrder.indexOf(v2);
					int iIndex = spineOrder.indexOf(w1);
					int jIndex = spineOrder.indexOf(w2);
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
		for (int i = 0; i < adjacencyList.size(); i++) {
			for (int j = 0; j < adjacencyList.get(i).size(); j++) {
				int w1 = i;
				int w2 = adjacencyList.get(i).get(j).v2;
				int pageN = adjacencyList.get(i).get(j).p;
				//if (pageN > -1) {
					// crossingsIncreaseList = new ArrayList<>(pageNumber);
					int v1Index = spineOrder.indexOf(v1);
					int v2Index = spineOrder.indexOf(v2);
					int iIndex = spineOrder.indexOf(w1);
					int jIndex = spineOrder.indexOf(w2);
					if (isCrossing(v1Index, v2Index, iIndex, jIndex)) {
						newCrossings = crossingsIncreaseList.get(pageN) + 1;
						crossingsIncreaseList.set(pageN, newCrossings);
					}
				//}
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
		this.adjacencyList.get(v1).add(new HalfEdge(v2, pageN));
		// System.out.println(v1 +" "+ this.adjacencyList.get(v1));
		// this.adjacencyList.get(v2).add(this.adjacencyList.get(v2).size(),v1);
		//this.edgeN++;
	}

	public void addNewCrossings(int crossingIncrease, int pageN) {
		int crossingN = crossingsList.get(pageN) + crossingIncrease;
		crossingsList.set(pageN, crossingN);
	}
		
	public List<Integer> getSpineOrder() {
		return spineOrder;
	}

	public List<List<HalfEdge>> getAdjacencyList() {
		return adjacencyList;
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
	
	public SolutionNew copy() {
		List<Integer> spineOrderCopy = new ArrayList<>(spineOrder);
		List<List<HalfEdge>> adjacencyListCopy = new ArrayList<List<HalfEdge>>();
		for(int i = 0; i < adjacencyList.size(); i++) {
			List<HalfEdge> innerListCopy = new ArrayList<HalfEdge>();
			for(int j = 0; j < adjacencyList.get(i).size(); j++) {
				innerListCopy.add(new HalfEdge(adjacencyList.get(i).get(j).v2, adjacencyList.get(i).get(j).p));
			}
			adjacencyListCopy.add(innerListCopy);
		}
		List<Integer> crossingsListCopy = new ArrayList<>(crossingsList);
		return new SolutionNew(spineOrderCopy, pageNumber, adjacencyListCopy, crossingsListCopy, edgeNumber);
	}
	
	/*
	public String toString() {
		String matrix = "\n";
		for (int i = 0; i < adjacencyL.length; i++) {
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
	*/

}
