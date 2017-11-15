package neighbourhood;

import models.Solution;

public interface INeighbourhood {
	public Solution move(Solution solution);
	public int getSelectedV1();
	public int getSelectedV2();
	public int getSelectedPage();
	public boolean isSolutionUpdated();
}
