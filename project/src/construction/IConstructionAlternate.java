package construction;

import models.SolutionOld;
import models.SolutionAlternate;
import parser.KPMPInstance;

public interface IConstructionAlternate {

	public SolutionAlternate generateSolution(KPMPInstance kpmpInstance);
	
}
