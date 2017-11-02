package construction;

import java.util.ArrayList;
import java.util.List;

import models.Solution;
import parser.KPMPInstance;

public class Greedy {
	
	public static Solution generateSolution(KPMPInstance kpmpInstance) {
		
		Solution solution = new Solution(kpmpInstance.getNumVertices(), kpmpInstance.getK());
		
		
		
		
		
		// LIST GREEDY
		
		
		// keep track of added edges
		/*
		List<List<Integer>> addedAdjacencyList = new ArrayList<List<Integer>>();
		for(int i = 0; i < kpmpInstance.getNumVertices(); i++) {
			addedAdjacencyList.add(new ArrayList<Integer>());
		}
		*/
		
		
		for(int i = 0; i < kpmpInstance.getAdjacencyList().size(); i++) {
			for(int j = 0; j < kpmpInstance.getAdjacencyList().get(i).size(); j++) {
				
				int v1 = i;
				int v2 = kpmpInstance.getAdjacencyList().get(i).get(j);
				//addedAdjacencyList.get(v1).add(v2);
				
				// check is edge is alreay in the list
				boolean edgeIsInList = false;
				
				for(int k = 0; k < solution.getPageList().size(); k++) {
					if(solution.getPageList().get(k).isEdgeInList(v1, v2)){
						edgeIsInList = true;
					}
				}
				
				// edgeIsInList = addedAdjacencyList.get(v2).contains(v1);
				
				if(!edgeIsInList){	
				//greedily decide to which page to add the edge
				int bestPage = 0;
				int minCrossingIncrease = -1;

				for(int k = 0; k < solution.getPageList().size(); k++) {					
					int crossingIncrease = solution.calculateCrossingIncrease_L(v1,v2,k);
					if(minCrossingIncrease == -1 || crossingIncrease < minCrossingIncrease) {
						bestPage = k;
						minCrossingIncrease = crossingIncrease;
					}
				}
				
				solution.addEdgeToPage_L(v1, v2, bestPage);
				solution.addNewCrossings(minCrossingIncrease, bestPage);
				//System.out.println(minCrossingIncrease);
				
				}

			}
		}
		
		return solution;
		
				
		
		
		
		
		
		// MATRIX GREEDY
		
		
		
		// assign edges to pages
		/*
		for(int i = 0; i < kpmpInstance.getAdjacencyMatrix().length; i++) {
			for(int j = i+1; j < kpmpInstance.getAdjacencyMatrix()[i].length; j++) {
				
				boolean currentEdge = kpmpInstance.getAdjacencyMatrix()[i][j];
				if(currentEdge) {
					
					//greedily decide to which page to add the edge
					int bestPage = 0;
					int minCrossingIncrease = -1;
					for(int k = 0; k < solution.getPageList().size(); k++) {
						int crossingIncrease = solution.calculateCrossingIncrease_M(i,j,k);
						if(minCrossingIncrease == -1 || crossingIncrease < minCrossingIncrease) {
							bestPage = k;
							minCrossingIncrease = crossingIncrease;
						}
					}
					
					solution.addEdgeToPage_M(i, j, bestPage);
					solution.addNewCrossings(minCrossingIncrease, bestPage);
					//System.out.println(minCrossingIncrease);
				}
				
			}
		}
		return solution;
		*/
	}

}
