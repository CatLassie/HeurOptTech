package construction.old;

import java.util.List;

import models.old.SolutionOld;
import parser.KPMPInstance;
import util.Utilities;

public class GreedyListOld implements IConstructionOld {
	
	public SolutionOld generateSolution(KPMPInstance kpmpInstance) {
		SolutionOld solution = new SolutionOld(kpmpInstance.getNumVertices(), kpmpInstance.getK());
		Utilities.sortAdjacencyList(kpmpInstance.getAdjacencyList());
				
		for(int i = 0; i < kpmpInstance.getAdjacencyList().size(); i++) {
			List<Integer> innerList = kpmpInstance.getAdjacencyList().get(i);
			for(int j = innerList.size()-1; (j >= 0) && (innerList.get(j) > i); j--) {
				
				int v1 = i;
				int v2 = kpmpInstance.getAdjacencyList().get(i).get(j);
				// System.out.println("EDGE #" + e++ +" vertices: " + v1 + " " + v2 );
				
				//greedily decide to which page to add the edge
				int bestPage = 0;
				int minCrossingIncrease = -1;
				
				for(int k = 0; k < solution.getPageList().size(); k++) {
					//System.out.println("page #" + (k+1));
					int crossingIncrease = solution.calculateCrossingIncrease_L(v1,v2,k);
					// System.out.println(crossingIncrease);
					if(minCrossingIncrease == -1 || crossingIncrease < minCrossingIncrease) {
						bestPage = k;
						minCrossingIncrease = crossingIncrease;
					}
				}
				
				solution.addEdgeToPage_L(v1, v2, bestPage);
				solution.addNewCrossings(minCrossingIncrease, bestPage);
				//System.out.println("min: " + minCrossingIncrease);
			}
		}
		return solution;
	}

}
