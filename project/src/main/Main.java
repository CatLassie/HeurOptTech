package main;

import java.io.FileNotFoundException;

import construction.Greedy;
import models.Solution;
import parser.KPMPInstance;

public class Main {

	private static String path = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/Assignment1/instances.tar/instances/automatic-1.txt";
	private static String pathK4 = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/Assignment1/instances.tar/instances/k4.txt";
	
	public static void main(String[] args) {
		System.out.println("Main.main()");
		
		// GraphPrinter.printGraphData(path);
		
		try {
			KPMPInstance k = KPMPInstance.readInstance(path);
			Solution initialSolution = Greedy.generateSolution(k);
			// System.out.println(initialSolution);
			for(int  i = 0; i < initialSolution.getPageList().size(); i++) {
				System.out.println(initialSolution.getPageList().get(i).getCrossingN());	
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
