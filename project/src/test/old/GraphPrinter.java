package test.old;

import java.io.FileNotFoundException;

import parser.KPMPInstance;

public class GraphPrinter {
	
	public static void printGraphData(String path) {
		
		try {
			KPMPInstance k = KPMPInstance.readInstance(path);
			System.out.println("vertex number: " + k.getNumVertices() + " \npage number: " + k.getK());
			System.out.println("\n");
			for(int i = 0; i < k.getAdjacencyMatrix().length; i++){
				for(int j = 0; j < k.getAdjacencyMatrix()[i].length; j++){
					if(k.getAdjacencyMatrix()[i][j]){
						System.out.print(1 +" ");						
					} else {
						System.out.print(0 +" ");	
					}
				}
				System.out.println();
			}
			System.out.println("\n");
			for(int i = 0; i < k.getAdjacencyList().size(); i++){
				System.out.println(i+" => "+k.getAdjacencyList().get(i));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File was not found!");
		}
		
	}

}
