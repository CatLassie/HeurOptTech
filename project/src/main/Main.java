package main;

import java.io.FileNotFoundException;

import construction.GreedyList;
import construction.GreedyListConcurrent;
import construction.GreedyMatrix;
import construction.GreedyPageMatrix;
import construction.IConstruction;
import construction.IConstructionAlternate;
import construction.RandomizedPageMatrix;
import localSearch.ILocalSearch;
import localSearch.LocalSearch;
import models.SolutionOld;
import models.Solution;
import parser.KPMPInstance;
import util.NeighbourhoodStructureEnum;
import util.StepFunctionEnum;

public class Main {

	private static String path = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/Assignment1/instances.tar/instances/automatic-7.txt";
	private static String pathK4 = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/Assignment1/instances.tar/instances/k4.txt";
	
	public static void main(String[] args) {
		System.out.println("MainAlternate.main()");
		
		// GraphPrinter.printGraphData(path);
		
		try {
			KPMPInstance k = KPMPInstance.readInstance(path);		
			
			
			
			// CONSTRUCTION HEURISTIC
			
			IConstructionAlternate construction = new RandomizedPageMatrix();
			construction = new GreedyPageMatrix();
			
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
			// System.out.println("PAGE MATRIX: "+initialSolution);
			
			
			
			// LOCAL SEARCH
			
			ILocalSearch localSearch = new LocalSearch(initialSolution, NeighbourhoodStructureEnum.XOR, StepFunctionEnum.RANDOM);
			
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
			// System.out.println("PAGE MATRIX: "+bestSolution);
			
			
			// System.out.println("PAGE MATRIX: "+localSearch.getBestSolution());
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
