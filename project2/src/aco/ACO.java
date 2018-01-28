package aco;

import java.util.ArrayList;
import java.util.List;

import construction.IConstruction;
import models.Ant;
import models.PheromoneMatrix;
import models.Solution;
import parser.KPMPInstance;
import util.Utilities;

public class ACO implements IConstruction {
	int antN, timeN;
	double initialPh, phWeight, costWeight, evapWeight;
	List<Ant> ants;
	List<Solution> population;
	PheromoneMatrix phMatrix;
	
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
		// Solution initialSolution = new Solution(vertexNumber, pageNumber, false);
		// int edgeNumber = 0;
		
		phMatrix = new PheromoneMatrix(vertexNumber, pageNumber, initialPh, evapWeight);
		for(int i = 0; i < antN; i++) {
			ants.add(new Ant(i, matrix, vertexNumber, pageNumber, phWeight, costWeight));
			// System.out.println(ants.get(i));
		}
		
		// it would be cool to make ants start from different edges
		timeLoop:
		for(int i = 0; i < timeN; i++){
			if(Utilities.isTimeOver()){
				System.out.println("ACO time is up!");
				break timeLoop;
			}
			population = generateOneRound(i);
			
			// sum of crossings of a population
			/*
			int s = 0;
			for(int j = 0; j < currentPopulation.size(); j++) {
				s += currentPopulation.get(j).getTotalCrossings();
			}
			System.out.println(s);
			*/
		}
		
		return population;
	}
	
	public List<Solution> generateOneRound(int timeStep) {
		
		List<Solution> currentPopulation = new ArrayList<>();
		List<Double> currentPheromoneValues = new ArrayList<>();
		antLoop:
		for(int j = 0; j < ants.size(); j++) {
			if(Utilities.isTimeOver()){
				System.out.println("Round time is up!");
				currentPopulation = population; // last complete population
				break antLoop;
			}
			
			Ant ant = ants.get(j);
			Solution solution_ij = ant.generateSolution(phMatrix);
			currentPopulation.add(solution_ij);
			
			double pheromoneValue = 1.0/solution_ij.getTotalCrossings();
			currentPheromoneValues.add(pheromoneValue);
			
			System.out.println("solution of ant "+j+" at time "+timeStep+" is: "+solution_ij.getTotalCrossings());
			// System.out.println("pheromone trail value: "+currentPheromoneValues);
		}
		// System.out.println(phMatrix);
		if(!Utilities.isTimeOver()){
			phMatrix.update(currentPopulation, currentPheromoneValues);
			phMatrix.evaporate();
		}
		
		return currentPopulation;
	}
	
	public Solution getBestSolution() {
		Solution bestSolution = population.get(0);
		for(int i = 0; i < population.size(); i++) {
			if(population.get(i).getTotalCrossings() < bestSolution.getTotalCrossings()){
				bestSolution = population.get(i);
			}			
		}
		return bestSolution;
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
