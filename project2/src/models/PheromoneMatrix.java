package models;

import java.util.List;

public class PheromoneMatrix {
	private int vertexN;
	private int pageN;
	private double initialPh;
	private double evapWeight;
	private double[][][] matrix;
	
	public PheromoneMatrix(int vertexN, int pageN, double initialPh, double evapWeight) {
		this.vertexN = vertexN;
		this.pageN = pageN;
		this.initialPh = initialPh;
		this.evapWeight = evapWeight;
		this.matrix = new double[vertexN][vertexN][pageN];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix[i].length; j++) {
				for(int k = 0; k < matrix[i][j].length; k++){
					matrix[i][j][k] = initialPh;
				}
			}
		}
	}
	
	public void update(List<Solution> population, List<Double> phValues) {
		for(int pop = 0; pop < population.size(); pop++) {
			for (int i = 0; i < matrix.length; i++) {
				for (int j = i + 1; j < matrix[i].length; j++) {
					int usedPage = population.get(pop).getAdjacencyMatrix()[i][j];
					if(usedPage > -1) {
						matrix[i][j][usedPage] += phValues.get(pop);
					}
				}
			}
		}
	}
	
	public void evaporate() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix[i].length; j++) {
				for(int k = 0; k < matrix[i][j].length; k++){
					matrix[i][j][k] = (1-evapWeight)*matrix[i][j][k];
				}
			}
		}
	}
	
	public double[][][] getValue(){
		return this.matrix;
	}

	public String toString(){
			String info = "\n";
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					info += "[";
					for(int k = 0; k < matrix[i][j].length; k++){
						info += matrix[i][j][k] + " ";
					}
					info += "] ";
				}
				info += "\n";
			}
			return info;
	}
	
}
