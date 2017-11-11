package construction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import models.Solution;
import models.SolutionAlternate;
import parser.KPMPInstance;
import util.Utilities;

public class GreedyPageMatrix implements IConstructionAlternate {

	public SolutionAlternate generateSolution(KPMPInstance kpmpInstance) {
		int pageNumber = kpmpInstance.getK();
		SolutionAlternate solution = new SolutionAlternate(kpmpInstance.getNumVertices(), kpmpInstance.getK());

		for (int i = 0; i < kpmpInstance.getAdjacencyMatrix().length; i++) {
			for (int j = i + 1; j < kpmpInstance.getAdjacencyMatrix()[i].length; j++) {

				boolean currentEdge = kpmpInstance.getAdjacencyMatrix()[i][j];
				if (currentEdge) {
					// greedily decide to which page to add the edge
					List<Integer> crossingIncrease = solution.calculateCrossingIncreaseArray(i, j);

					int minCrossingIncrease = -1;
					int bestPage = -1;
					List<Integer> copyCrossingIncrease = new ArrayList<>(crossingIncrease);
					copyCrossingIncrease.sort((a, b) -> a - b);
					minCrossingIncrease = copyCrossingIncrease.get(0);
					bestPage = crossingIncrease.indexOf(minCrossingIncrease);

					solution.addEdge(i, j, bestPage + 1);
					solution.addNewCrossings(minCrossingIncrease, bestPage);
					// System.out.println(minCrossingIncrease);
				}

			}
		}
		return solution;
	}

}
