package construction;

import java.util.concurrent.ThreadLocalRandom;

import models.Solution;
import parser.KPMPInstance;

public class RandomizedPageMatrix implements IConstruction {
	
	public Solution generateSolution(KPMPInstance kpmpInstance) {
		int vertexNumber = kpmpInstance.getNumVertices();
		int pageNumber = kpmpInstance.getK();
		boolean[][] matrix = kpmpInstance.getAdjacencyMatrix();
		
		Solution solution = new Solution(vertexNumber, pageNumber, true);
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix[i].length; j++) {

				boolean currentEdge = matrix[i][j];
				if (currentEdge) {
															
					int randomPage = ThreadLocalRandom.current().nextInt(0, pageNumber);
					// System.out.println("page #" + randomPage);
					int crossingIncrease = solution.calculateCrossingIncrease(i,j,randomPage);
					// System.out.println(crossingIncrease);

					solution.addEdge(i, j, randomPage);
					solution.addNewCrossings(crossingIncrease, randomPage);
					// System.out.println(minCrossingIncrease);
				}
				
			}
		}
		return solution;
	}

}
