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
		int vertexNumber = kpmpInstance.getNumVertices();
		int pageNumber = kpmpInstance.getK();
		boolean[][] matrix = kpmpInstance.getAdjacencyMatrix();

		SolutionAlternate solution = new SolutionAlternate(vertexNumber, pageNumber);

		for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix[i].length; j++) {

				boolean currentEdge = matrix[i][j];
				if (currentEdge) {
					// greedily decide to which page to add the edge
					List<Integer> crossingIncrease = solution.calculateCrossingIncreaseArray(i, j);

					int minCrossingIncrease = -1;
					int bestPage = -1;
					List<Integer> copyCrossingIncrease = new ArrayList<>(crossingIncrease);
					copyCrossingIncrease.sort((a, b) -> a - b);
					minCrossingIncrease = copyCrossingIncrease.get(0);
					bestPage = crossingIncrease.indexOf(minCrossingIncrease);

					solution.addEdge(i, j, bestPage);
					solution.addNewCrossings(minCrossingIncrease, bestPage);
					// System.out.println(minCrossingIncrease);
				}

			}
		}
		return solution;
	}

}
