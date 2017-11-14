package construction;

import models.SolutionOld;
import models.Solution;
import parser.KPMPInstance;

public interface IConstructionAlternate {

	public Solution generateSolution(KPMPInstance kpmpInstance);
	
}
