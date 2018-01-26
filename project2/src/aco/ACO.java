package aco;

import java.util.ArrayList;
import java.util.List;

import construction.IConstruction;
import models.Ant;
import models.Solution;
import parser.KPMPInstance;

public class ACO implements IConstruction {
	
	int antN, timeN;
	double initialPh, phWeight, costWeight, evapWeight;
	List<Ant> ants;
	
	public ACO(int antN, int timeN, double initialPh, double phWeight, double costWeight, double evapWeight) {
		this.antN = antN;
		this.timeN = timeN;
		this.initialPh = initialPh;
		this.phWeight = phWeight;
		this.costWeight = costWeight;
		this.evapWeight = evapWeight;
		this.ants = new ArrayList<>();
	}
	
	public List<Solution> generateSolutionPopulation(KPMPInstance kpmpInstance) {
		int vertexNumber = kpmpInstance.getNumVertices();
		int pageNumber = kpmpInstance.getK();
		boolean[][] matrix = kpmpInstance.getAdjacencyMatrix();
		Solution initialSolution = new Solution(vertexNumber, pageNumber, true);
		// int edgeNumber = 0;
		
		for(int i = 0; i < antN; i++) {
			ants.add(new Ant(i, matrix));
			// System.out.println(ants.get(i));
		}
		
		List<Solution> currentPopulation = null;
		
		// it would be cool to make ants start from different edges
		for(int i = 0; i < timeN; i++){
			currentPopulation = generateOneRound(initialSolution, i);
		}
		
		return currentPopulation;
	}
	
	public List<Solution> generateOneRound(Solution initialSolution, int timeStep) {
		
		List<Solution> currentPopulation = new ArrayList<>();
		for(int j = 0; j < ants.size(); j++) {
			Ant ant = ants.get(j);
			Solution solution_ij = ant.generateSolution(initialSolution);
			currentPopulation.add(solution_ij);
			
			System.out.println("solution of ant "+j+" at time "+timeStep+" is: "+solution_ij.getTotalCrossings());
		}
		
		return currentPopulation;
	}
	
	public Solution getBestSolution() {
		return null;
	}
	
	public Solution generateSolution(KPMPInstance kpmpInstance) {
		return null;
	}
	
	public String toString() {
		String info = "\n";
		info = info+"ACO\nant number: "+antN+"\n"
				+"time steps: "+timeN+"\n"
				+"initial pheromone value: "+initialPh+"\n"
				+"pheromone control level: "+phWeight+"\n"
				+"cost control level: "+costWeight+"\n"
				+"evaporation rate control level: "+evapWeight;
		return info;
	}
	
}
