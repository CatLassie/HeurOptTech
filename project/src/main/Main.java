package main;

import java.io.FileNotFoundException;

import construction.GreedyList;
import construction.GreedyMatrix;
import construction.IGreedy;
import models.Solution;
import parser.KPMPInstance;

public class Main {

	private static String path = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/Assignment1/instances.tar/instances/automatic-7.txt";
	private static String pathK4 = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/Assignment1/instances.tar/instances/k4.txt";
	
	public static void main(String[] args) {
		System.out.println("Main.main()");
		
		// GraphPrinter.printGraphData(path);
		
		try {
			KPMPInstance k = KPMPInstance.readInstance(path);
			IGreedy greedy = new GreedyList();
			
			long startTimeNano = System.nanoTime();
			long startTime = System.currentTimeMillis();
			
			Solution initialSolution = greedy.generateSolution(k);
			
			long endTimeNano = System.nanoTime();
			long endTime = System.currentTimeMillis();
			double diffSec = ((double) endTime - startTime)/1000;
			System.out.println("GREEDY TOOK: " + diffSec + " sec " + (endTimeNano - startTimeNano)); 

			for(int  i = 0; i < initialSolution.getPageList().size(); i++) {
				System.out.println("page #"+(i+1)+" : " + initialSolution.getPageList().get(i).getCrossingN());
				// System.out.println(initialSolution.getPageList().get(i).getAdjacencyList());
			}
			System.out.println("TOTAL CROSSINGS: "+initialSolution.getCrossingN());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
