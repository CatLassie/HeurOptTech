package construction;

import java.util.ArrayList;
import java.util.List;

import models.Solution;
import parser.KPMPInstance;
import util.CalculateIncreaseRunner;
import util.Utilities;

public class GreedyListConcurrent implements IConstruction {
	
	public Solution generateSolution(KPMPInstance kpmpInstance) {
		Solution solution = new Solution(kpmpInstance.getNumVertices(), kpmpInstance.getK());
		Utilities.sortAdjacencyList(kpmpInstance.getAdjacencyList());		
		int e = 1;
		
		for(int i = 0; i < kpmpInstance.getAdjacencyList().size(); i++) {
			List<Integer> innerList = kpmpInstance.getAdjacencyList().get(i);
			for(int j = innerList.size()-1; (j >= 0) && (innerList.get(j) > i); j--) {
				
				int v1 = i;
				int v2 = kpmpInstance.getAdjacencyList().get(i).get(j);
				// System.out.println("EDGE #" + e++ +" vertices: " + v1 + " " + v2 );
				
				//greedily decide to which page to add the edge
				int bestPage = 0;
				int minCrossingIncrease = -1;
				List<CalculateIncreaseRunner> cIR = new ArrayList<>();
				List<Thread> workers = new ArrayList<>();
				
				for(int k = 0; k < solution.getPageList().size(); k++) {
					//System.out.println("page #" + (k+1));
					CalculateIncreaseRunner c = new CalculateIncreaseRunner(solution,v1,v2,k);
					Thread t = new Thread(c);
					t.start();
					cIR.add(c);
					workers.add(t);
				}
				
				for(int k = 0; k < solution.getPageList().size(); k++) {
					try {
						workers.get(k).join();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					int crossingIncrease = cIR.get(k).getCurrentCrossingIncrease();
					//System.out.println(crossingIncrease);
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
