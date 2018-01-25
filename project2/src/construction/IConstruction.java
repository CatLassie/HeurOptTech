package construction;

import models.Solution;
import parser.KPMPInstance;

public interface IConstruction {

	public Solution generateSolution(KPMPInstance kpmpInstance);
	
	public String toString();
	
}
