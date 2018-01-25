package aco;

import construction.IConstruction;
import models.Solution;
import parser.KPMPInstance;

public class ACO implements IConstruction {
	
	int antN, timeN;
	double initialPh, phWeight, costWeight, evapWeight;
	
	public ACO(int antN, int timeN, double initialPh, double phWeight, double costWeight, double evapWeight) {
		this.antN = antN;
		this.timeN = timeN;
		this.initialPh = initialPh;
		this.phWeight = phWeight;
		this.costWeight = costWeight;
		this.evapWeight = evapWeight;
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
