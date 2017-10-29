package models;

import java.util.ArrayList;
import java.util.List;

public class Solution {
	
	private List<Integer> spineOrder;
	private List<Page> pageList;
	
	public Solution(int vertexNumber, int pageNumber) {
		spineOrder = new ArrayList<>();
		pageList = new ArrayList<>();
		
		// set the spine order
		for(int i = 0; i < vertexNumber; i++) {
			spineOrder.add(i);
		}
		
		for(int i = 0; i < pageNumber; i++) {
			pageList.add(new Page(vertexNumber));
		}
	}
		
	public void addEdgeToPage(int v1, int v2, int pageN) {
		pageList.get(pageN).addEdge(v1,v2);
	}
	
	public int calculateCrossingIncrease(int v1, int v2, int pageN) {
		return pageList.get(pageN).calculateCrossingIncrease(v1, v2, spineOrder);
	}
	
	public void addNewCrossings(int crossingIncrease, int pageN) {
		pageList.get(pageN).addNewCrossings(crossingIncrease);
	}
		
	public List<Integer> getSpineOrder() {
		return spineOrder;
	}

	public void setSpineOrder(List<Integer> spineOrder) {
		this.spineOrder = spineOrder;
	}

	public List<Page> getPageList() {
		return pageList;
	}

	public void setPageList(List<Page> pageList) {
		this.pageList = pageList;
	}
	
	public String toString() {
		String solution = "";
		solution += "Spine order is: " + spineOrder + "\n";
		solution += "Page # is: " + pageList.size() + "\n";
		solution += "\n";
		for(int i = 0; i < pageList.size(); i++) {
			solution += "page #"+(i+1)+"\n"+ pageList.get(i).toString() +"\n";
		}
		return solution;
	}
	
}
