package test;

import java.io.FileNotFoundException;

import models.Solution;
import parser.KPMPInstance;

public class Tester {
	
	private static String path = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/Assignment1/instances.tar/instances/automatic-1.txt";
	
	public static void main(String[] args) {
		System.out.println("Tester.main()");
		
		// GraphPrinter.printGraphData(path);
		
		Solution x = new Solution(4, 2);
		// System.out.println(new Solution(4, 2));
		try {
			System.out.println(KPMPInstance.readInstance(path).getAdjacencyList());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
