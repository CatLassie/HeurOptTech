package construction;

import java.util.ArrayList;
import java.util.List;

import models.Solution;
import parser.KPMPInstance;

public class Greedy implements IConstruction {

	public Solution generateSolution(KPMPInstance kpmpInstance) {
		int vertexNumber = kpmpInstance.getNumVertices();
		int pageNumber = kpmpInstance.getK();
		boolean[][] matrix = kpmpInstance.getAdjacencyMatrix();

		Solution solution = new Solution(vertexNumber, pageNumber, false);
		int edgeNumber = 0;
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix[i].length; j++) {

				boolean currentEdge = matrix[i][j];
				if (currentEdge) {
					edgeNumber++;
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
		solution.setEdgeNumber(edgeNumber);
		return solution;
	}
	
	public String toString(){
		return "Greedy construction heuristic";
	}

}
