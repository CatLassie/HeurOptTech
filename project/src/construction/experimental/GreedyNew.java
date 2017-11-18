package construction.experimental;

import java.util.ArrayList;
import java.util.List;

import models.Solution;
import models.experimental.SolutionNew;
import parser.KPMPInstance;
import util.Utilities;

public class GreedyNew {

	public SolutionNew generateSolution(KPMPInstance kpmpInstance) {
		int vertexNumber = kpmpInstance.getNumVertices();
		int pageNumber = kpmpInstance.getK();
		// boolean[][] matrix = kpmpInstance.getAdjacencyMatrix();
		
		Utilities.sortAdjacencyList(kpmpInstance.getAdjacencyList());

		SolutionNew solution = new SolutionNew(vertexNumber, pageNumber, false);
		int edgeNumber = 0;
		
		for(int i = 0; i < kpmpInstance.getAdjacencyList().size(); i++) {
			List<Integer> innerList = kpmpInstance.getAdjacencyList().get(i);
			for(int j = innerList.size()-1; (j >= 0) && (innerList.get(j) > i); j--) {
					edgeNumber++;
					
					int v1 = i;
					int v2 = kpmpInstance.getAdjacencyList().get(i).get(j);
					// greedily decide to which page to add the edge
					List<Integer> crossingIncrease = solution.calculateCrossingIncreaseArray(v1, v2);

					int minCrossingIncrease = -1;
					int bestPage = -1;
					List<Integer> copyCrossingIncrease = new ArrayList<>(crossingIncrease);
					copyCrossingIncrease.sort((a, b) -> a - b);
					minCrossingIncrease = copyCrossingIncrease.get(0);
					bestPage = crossingIncrease.indexOf(minCrossingIncrease);

					solution.addEdge(v1, v2, bestPage);
					solution.addNewCrossings(minCrossingIncrease, bestPage);
					// System.out.println(minCrossingIncrease);
			}
		}
		solution.setEdgeNumber(edgeNumber);
		return solution;
	}

}
