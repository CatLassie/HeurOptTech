package construction.old;

import models.old.SolutionOld;
import parser.KPMPInstance;

public class GreedyMatrixOld implements IConstructionOld {

	public SolutionOld generateSolution(KPMPInstance kpmpInstance) {

		SolutionOld solution = new SolutionOld(kpmpInstance.getNumVertices(), kpmpInstance.getK());

		// assign edges to pages

		for (int i = 0; i < kpmpInstance.getAdjacencyMatrix().length; i++) {
			for (int j = i + 1; j < kpmpInstance.getAdjacencyMatrix()[i].length; j++) {

				boolean currentEdge = kpmpInstance.getAdjacencyMatrix()[i][j];
				if (currentEdge) {

					// greedily decide to which page to add the edge
					int bestPage = 0;
					int minCrossingIncrease = -1;
					for (int k = 0; k < solution.getPageList().size(); k++) {
						int crossingIncrease = solution.calculateCrossingIncrease_M(i, j, k);
						if (minCrossingIncrease == -1 || crossingIncrease < minCrossingIncrease) {
							bestPage = k;
							minCrossingIncrease = crossingIncrease;
						}
					}

					solution.addEdgeToPage_M(i, j, bestPage);
					solution.addNewCrossings(minCrossingIncrease, bestPage);
					// System.out.println(minCrossingIncrease);
				}
				
			}
		}
		return solution;
	}

}
