package construction;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import models.Solution;
import parser.KPMPInstance;
import util.Utilities;

public class RandomizedList implements IConstruction {

	public Solution generateSolution(KPMPInstance kpmpInstance) {		
		Solution solution = new Solution(kpmpInstance.getNumVertices(), kpmpInstance.getK());
		Utilities.sortAdjacencyList(kpmpInstance.getAdjacencyList());

		for (int i = 0; i < kpmpInstance.getAdjacencyList().size(); i++) {
			List<Integer> innerList = kpmpInstance.getAdjacencyList().get(i);
			for (int j = innerList.size() - 1; (j >= 0) && (innerList.get(j) > i); j--) {
				
				int v1 = i;
				int v2 = kpmpInstance.getAdjacencyList().get(i).get(j);
				// System.out.println("EDGE #" + e++ +" vertices: " + v1 + " " + v2 );
				
				int randomPage = ThreadLocalRandom.current().nextInt(0, solution.getPageList().size());
				//System.out.println("page #" + randomPage);
				int crossingIncrease = solution.calculateCrossingIncrease_L(v1,v2,randomPage);
				// System.out.println(crossingIncrease);
				
				solution.addEdgeToPage_L(v1, v2, randomPage);
				solution.addNewCrossings(crossingIncrease, randomPage);
				//System.out.println("min: " + minCrossingIncrease);	
			}
		}
		
		return solution;
	}

}