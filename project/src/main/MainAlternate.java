package main;

import java.io.FileNotFoundException;

import construction.GreedyList;
import construction.GreedyListConcurrent;
import construction.GreedyMatrix;
import construction.GreedyPageMatrix;
import construction.IConstruction;
import construction.IConstructionAlternate;
import construction.RandomizedPageMatrix;
import models.Solution;
import models.SolutionAlternate;
import parser.KPMPInstance;

public class MainAlternate {

	private static String path = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/Assignment1/instances.tar/instances/automatic-7.txt";
	private static String pathK4 = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/Assignment1/instances.tar/instances/k4.txt";
	
	public static void main(String[] args) {
		System.out.println("MainAlternate.main()");
		
		// GraphPrinter.printGraphData(path);
		
		try {
			KPMPInstance k = KPMPInstance.readInstance(path);			
			IConstructionAlternate construction = new RandomizedPageMatrix();
			construction = new GreedyPageMatrix();
			
			long startTimeNano = System.nanoTime();
			long startTime = System.currentTimeMillis();
			
			SolutionAlternate initialSolution = construction.generateSolution(k);
			
			long endTimeNano = System.nanoTime();
			long endTime = System.currentTimeMillis();
			double diffSec = ((double) endTime - startTime)/1000;
			System.out.println("GREEDY TOOK: " + diffSec + " sec " + (endTimeNano - startTimeNano)); 
			
			int totalCrossings = 0;
			for(int  i = 0; i < initialSolution.getCrossingsList().size(); i++) {
				System.out.println("page #"+(i+1)+" : " + initialSolution.getCrossingsList().get(i));
				totalCrossings += initialSolution.getCrossingsList().get(i);
			}
			System.out.println("TOTAL CROSSINGS: "+totalCrossings);
			// System.out.println("PAGE MATRIX: "+initialSolution);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
