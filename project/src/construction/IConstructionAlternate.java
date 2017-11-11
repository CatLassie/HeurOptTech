package construction;

import models.Solution;
import models.SolutionAlternate;
import parser.KPMPInstance;

public interface IConstructionAlternate {

	public SolutionAlternate generateSolution(KPMPInstance kpmpInstance);
	
}
