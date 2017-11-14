package construction;

import models.SolutionOld;
import parser.KPMPInstance;

public interface IConstruction {
	
	public SolutionOld generateSolution(KPMPInstance kpmpInstance);

}
