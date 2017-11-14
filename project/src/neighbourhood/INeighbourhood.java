package neighbourhood;

import models.Solution;

public interface INeighbourhood {
	public Solution move(Solution solution);
	public int getCurrentV1();
	public int getCurrentV2();
	public int getNewPage();
}
