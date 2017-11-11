package construction;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import models.Solution;
import models.SolutionAlternate;
import parser.KPMPInstance;
import util.Utilities;

public class RandomizedPageMatrix implements IConstructionAlternate {
	
	public SolutionAlternate generateSolution(KPMPInstance kpmpInstance) {
		int pageNumber = kpmpInstance.getK();
		SolutionAlternate solution = new SolutionAlternate(kpmpInstance.getNumVertices(), kpmpInstance.getK());
		
		for (int i = 0; i < kpmpInstance.getAdjacencyMatrix().length; i++) {
			for (int j = i + 1; j < kpmpInstance.getAdjacencyMatrix()[i].length; j++) {

				boolean currentEdge = kpmpInstance.getAdjacencyMatrix()[i][j];
				if (currentEdge) {
															
					int randomPage = ThreadLocalRandom.current().nextInt(1, pageNumber+1);
					// System.out.println("page #" + randomPage);
					int crossingIncrease = solution.calculateCrossingIncrease(i,j,randomPage);
					// System.out.println(crossingIncrease);

					solution.addEdge(i, j, randomPage);
					solution.addNewCrossings(crossingIncrease, randomPage-1);
					// System.out.println(minCrossingIncrease);
				}
				
			}
		}
		return solution;
	}

}
