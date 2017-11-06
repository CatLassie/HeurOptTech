package construction;

import models.Solution;
import parser.KPMPInstance;

public class GreedyList implements IGreedy {
	
	public Solution generateSolution(KPMPInstance kpmpInstance) {
		
		Solution solution = new Solution(kpmpInstance.getNumVertices(), kpmpInstance.getK());
		
		// keep track of added edges
		/*
		List<List<Integer>> addedAdjacencyList = new ArrayList<List<Integer>>();
		for(int i = 0; i < kpmpInstance.getNumVertices(); i++) {
			addedAdjacencyList.add(new ArrayList<Integer>());
		}
		*/
		
		int e = 1;
		for(int i = 0; i < kpmpInstance.getAdjacencyList().size(); i++) {
			for(int j = 0; j < kpmpInstance.getAdjacencyList().get(i).size(); j++) {
				
				int v1 = i;
				int v2 = kpmpInstance.getAdjacencyList().get(i).get(j);
				//addedAdjacencyList.get(v1).add(v2);
				
				System.out.println("EDGE #" + e++ +" vertices: " + v1 + " " + v2 );
				
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
					
					// System.out.println("storinka #" + (k+1));
					
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
	}

}
