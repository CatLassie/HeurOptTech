package construction;

import java.util.List;

import models.Solution;
import parser.KPMPInstance;

public interface IConstruction {

	public Solution generateSolution(KPMPInstance kpmpInstance);
	
	public List<Solution> generateSolutionPopulation(KPMPInstance kpmpInstance);
	
	public Solution getBestSolution();
	
	public String toString();
	
}
