package localSearch;

import models.SolutionAlternate;

public interface ILocalSearch {
	
	public SolutionAlternate search();
	public SolutionAlternate getBestSolution();
}
