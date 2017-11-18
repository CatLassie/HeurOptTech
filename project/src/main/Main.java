package main;

import java.io.FileNotFoundException;

import construction.Greedy;
import construction.IConstruction;
import construction.Randomized;
import localSearch.ILocalSearch;
import localSearch.LocalSearch;
import models.Solution;
import parser.KPMPInstance;
import util.NeighbourhoodStructureEnum;
import util.StepFunctionEnum;

public class Main {

	private static String path = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/Assignment1/new_instances/instances.tar/instances/instance-06.txt";
	private static String pathK4 = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/Assignment1/instances.tar/instances/k4.txt";
	
	public static void main(String[] args) {
		System.out.println("Main.main()");
		
		// GraphPrinter.printGraphData(path);
		
		try {
			KPMPInstance k = KPMPInstance.readInstance(path);		
			
			
			
			// CONSTRUCTION HEURISTIC
			
			IConstruction construction = new Randomized();
			construction = new Greedy();
			
			long startTimeNano = System.nanoTime();
			long startTime = System.currentTimeMillis();
			
			Solution initialSolution = construction.generateSolution(k);
			
			long endTimeNano = System.nanoTime();
			long endTime = System.currentTimeMillis();
			double diffSec = ((double) endTime - startTime)/1000;
			System.out.println("CONSTRUCTION TOOK: " + diffSec + " sec " + (endTimeNano - startTimeNano)); 
			
			for(int  i = 0; i < initialSolution.getCrossingsList().size(); i++) {
				System.out.println("page #"+(i+1)+" : " + initialSolution.getCrossingsList().get(i));
			}
			System.out.println("INITIAL SOLUTION TOTAL CROSSINGS: "+initialSolution.getTotalCrossings());
			System.out.println("INITIAL SOLUTION RECALCULATED CROSSINGS: "+initialSolution.calculateTotalCrossingArray());
			// System.out.println("PAGE MATRIX: "+initialSolution);
			
			// System.out.println("SPINE ORDER: "+initialSolution.getSpineOrder());
			
			System.out.println("#edge: "+initialSolution.getEdgeNumber());
			
			// LOCAL SEARCH
			
			ILocalSearch localSearch = new LocalSearch(initialSolution, NeighbourhoodStructureEnum.EDGE, StepFunctionEnum.BEST_IMPROVEMENT);
			
			startTimeNano = System.nanoTime();
			startTime = System.currentTimeMillis();
			
			Solution bestSolution = localSearch.search();
			
			endTimeNano = System.nanoTime();
			endTime = System.currentTimeMillis();
			diffSec = ((double) endTime - startTime)/1000;
			System.out.println("LOCAL SEARCH TOOK: " + diffSec + " sec " + (endTimeNano - startTimeNano)); 
			
			for(int  i = 0; i < bestSolution.getCrossingsList().size(); i++) {
				System.out.println("page #"+(i+1)+" : " + bestSolution.getCrossingsList().get(i));
			}
			System.out.println("BEST SOLUTION TOTAL CROSSINGS: "+bestSolution.getTotalCrossings());
			System.out.println("BEST SOLUTION RECALCULATED CROSSINGS: "+bestSolution.calculateTotalCrossingArray());
			// System.out.println("PAGE MATRIX: "+bestSolution);
			System.out.println("#edge: "+bestSolution.getEdgeNumber());
			
			
			// System.out.println("PAGE MATRIX: "+localSearch.getBestSolution());
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
