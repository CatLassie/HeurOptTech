package localSearch;

import models.Solution;

public interface ILocalSearch {
	
	public Solution search();
	public Solution getBestSolution();
}
