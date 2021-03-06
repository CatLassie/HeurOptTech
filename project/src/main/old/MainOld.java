package main.old;

import java.io.FileNotFoundException;

import construction.old.GreedyListConcurrentOld;
import construction.old.GreedyListOld;
import construction.old.GreedyMatrixOld;
import construction.old.IConstructionOld;
import construction.old.RandomizedListOld;
import models.old.SolutionOld;
import parser.KPMPInstance;

public class MainOld {

	private static String path = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/Assignment1/instances.tar/instances/automatic-7.txt";
	private static String pathK4 = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/Assignment1/instances.tar/instances/k4.txt";
	
	public static void main(String[] args) {
		System.out.println("MainOld.main()");
		
		// GraphPrinter.printGraphData(path);
		
		try {
			KPMPInstance k = KPMPInstance.readInstance(path);
			IConstructionOld greedy = new GreedyListConcurrentOld();
			// greedy = new RandomizedListOld();
			
			long startTimeNano = System.nanoTime();
			long startTime = System.currentTimeMillis();
			
			SolutionOld initialSolution = greedy.generateSolution(k);
			
			long endTimeNano = System.nanoTime();
			long endTime = System.currentTimeMillis();
			double diffSec = ((double) endTime - startTime)/1000;
			System.out.println("GREEDY TOOK: " + diffSec + " sec " + (endTimeNano - startTimeNano)); 

			for(int  i = 0; i < initialSolution.getPageList().size(); i++) {
				System.out.println("page #"+(i+1)+" : " + initialSolution.getPageList().get(i).getCrossingN());
				//System.out.println(initialSolution.getPageList().get(i).getAdjacencyList());
				//System.out.println(initialSolution.getPageList().get(i).getEdgeN());
			}
			System.out.println("TOTAL CROSSINGS: "+initialSolution.getCrossingN());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
