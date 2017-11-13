package neighbourhood;

import models.SolutionAlternate;

public interface INeighbourhood {
	public SolutionAlternate move(SolutionAlternate solution);
	public int getCurrentV1();
	public int getCurrentV2();
	public int getNewPage();
}
