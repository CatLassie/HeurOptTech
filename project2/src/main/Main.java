package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import aco.ACO;
import construction.Greedy;
import construction.IConstruction;
import construction.Randomized;
import localSearch.ILocalSearch;
import localSearch.LocalSearch;
import models.Solution;
import parser.KPMPInstance;
import parser.KPMPSolutionWriter;
import util.NeighbourhoodStructureEnum;
import util.StepFunctionEnum;
import util.Utilities;

public class Main {
	
	// command line arguments are: 
	// instance number (01,02,03,etc),
	// construction heuristic type (greedy/random)
	// metaheuristic type (local/vnd/gvns)
	// in case of "local" meta 2 additional parameters:
	// neighbourhood type (edge/vertex)
	// step funciton type (random/first/best)
	
	//antN == number of ants
	//timeN == number of time steps
	//initialPh == initial pheromone value
	//phWeight == initial pheromone control level
	//costWeight == initial cost control level
	//evapWeight == pheromone evaporation rate control level
	
	// "hybrid" to enable hybridization, also add then neighourhoodType and stepFunctionType

	public static void main(String[] args) {
		System.out.println("\nMain.main()");
		
		String instanceN = args[0];
		String constrType = args[1];
		String metaType = args.length > 2 ? args[2] : "none";
		String neighbourhoodType = metaType.equals("local") && !constrType.equals("ACO") ? args[3] : "none";
		String stepFunctionType = metaType.equals("local") && !constrType.equals("ACO") ? args[4] : "none";
		
		String antN = constrType.equals("ACO") ? args[2] : "none";
		String timeN = constrType.equals("ACO") ? args[3] : "none";
		String initialPh = constrType.equals("ACO") ? args[4] : "none";
		String phWeight = constrType.equals("ACO") ? args[5] : "none";
		String costWeight = constrType.equals("ACO") ? args[6] : "none";
		String evapWeight = constrType.equals("ACO") ? args[7] : "none";
		
		metaType = constrType.equals("ACO") ? (args.length > 8 ? (args[8].equals("hybrid")? "local" : "none") : "none") : metaType;
		neighbourhoodType = constrType.equals("ACO") ? (metaType.equals("local") ? args[9] : "none") : neighbourhoodType;
		stepFunctionType = constrType.equals("ACO") ? (metaType.equals("local") ? args[10] : "none") : stepFunctionType;
		
		
		
		System.out.println("command line args: "+instanceN+" "+constrType+" "+metaType+" "+neighbourhoodType +" "+ stepFunctionType+"");
		System.out.println("ACO specific arguments: "+antN+" "+timeN+" "+initialPh+" "+phWeight+" "+costWeight+" "+evapWeight+"\n");
		
		
		String readPath = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/GitRepos/1AssignmentRepo/instances/instance-"+instanceN+".txt";
		
		
		try {
			KPMPInstance k = KPMPInstance.readInstance(readPath);
			
			// CONSTRUCTION HEURISTIC
			IConstruction construction = constrType.equals("random") ? new Randomized() 
					: (constrType.equals("greedy") ? new Greedy() 
							: new ACO(Integer.parseInt(antN), Integer.parseInt(timeN), Double.parseDouble(initialPh), Double.parseDouble(phWeight), 
									Double.parseDouble(costWeight), Double.parseDouble(evapWeight)));
			
			long startTimeNano = System.nanoTime();
			long startTime = System.currentTimeMillis();
			//// Solution initialSolution = construction.generateSolution(k);
			long endTimeNano = System.nanoTime();
			long endTime = System.currentTimeMillis();
			double diffSec = ((double) endTime - startTime)/1000;
			//// System.out.println("CONSTRUCTION TOOK: " + diffSec + " sec " + (endTimeNano - startTimeNano)); 
			/*
			for(int  i = 0; i < initialSolution.getCrossingsList().size(); i++) {
				System.out.println("page #"+(i+1)+" : " + initialSolution.getCrossingsList().get(i));
			}
			*/
			//// System.out.println("INITIAL SOLUTION TOTAL CROSSINGS: "+initialSolution.getTotalCrossings()+"\n");
			// System.out.println("PAGE MATRIX: "+initialSolution);
			// System.out.println("SPINE ORDER: "+initialSolution.getSpineOrder());
			//System.out.println("#edge: "+initialSolution.getEdgeNumber());
			if(metaType.equals("none")) {
				String pathContd = constrType+"/instance-"+instanceN+".txt";
				//// writeSolution(initialSolution, pathContd);
			}
			
			System.out.println("First heuristic is: " + construction);
			List<Solution> initialSolution = construction.generateSolutionPopulation(k);
			
			
			// LOCAL SEARCH
			if(metaType.equals("local")){
				System.out.println("HYBRIDIZATION ENABLED WITH: "+metaType+" "+neighbourhoodType +" "+stepFunctionType);
				
				ILocalSearch localSearch;
				NeighbourhoodStructureEnum n = null;
				if(neighbourhoodType.equals("edge")) { n = NeighbourhoodStructureEnum.EDGE; }
				if(neighbourhoodType.equals("vertex")) { n = NeighbourhoodStructureEnum.VERTEX; }
				StepFunctionEnum s = null;
				if(stepFunctionType.equals("random")){ s = StepFunctionEnum.RANDOM; }
				if(stepFunctionType.equals("first")){ s = StepFunctionEnum.FIRST_IMPROVEMENT; }
				if(stepFunctionType.equals("best")){ s = StepFunctionEnum.BEST_IMPROVEMENT; }
				if(n == null || s == null){ 
					System.out.println("Incorrect local search parameters!");
					System.exit(1); 
				}
				
				//// localSearch = new LocalSearch(initialSolution, n, s);
			
				Utilities.startTimer();
				
				startTimeNano = System.nanoTime();
				startTime = System.currentTimeMillis();
				//// Solution bestSolution = localSearch.search();
				endTimeNano = System.nanoTime();
				endTime = System.currentTimeMillis();
				diffSec = ((double) endTime - startTime)/1000;
				//// System.out.println("LOCAL SEARCH ("+n+" "+s+") TOOK: " + diffSec + " sec " + (endTimeNano - startTimeNano));
				/*	
				for(int  i = 0; i < bestSolution.getCrossingsList().size(); i++) {
					System.out.println("page #"+(i+1)+" : " + bestSolution.getCrossingsList().get(i));
				}
				*/
				//// System.out.println("BEST SOLUTION TOTAL CROSSINGS: "+bestSolution.getTotalCrossings());
				// System.out.println("BEST SOLUTION RECALCULATED CROSSINGS: "+bestSolution.calculateTotalCrossingArray());
				// System.out.println("PAGE MATRIX: "+bestSolution);
				//System.out.println("BEST SOLUTION spine order: "+bestSolution.getSpineOrder());
				
				//// String pathContd = metaType+"/instance-"+instanceN+".txt";
				//// writeSolution(bestSolution, pathContd);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void writeSolution(Solution solution, String pathContd) {
		String writePathBase = "C:/Users/prjabc/Desktop/uni/P/2017W/Heuristic Optimization Techniques/GitRepos/1AssignmentRepo/results/";
		String writePath = writePathBase + pathContd;
		KPMPSolutionWriter solutionWriter = new KPMPSolutionWriter(solution.getPageNumber());
		
		solutionWriter.setSpineOrder(solution.getSpineOrder());
		int[][] matrix = solution.getAdjacencyMatrix();
		for(int i = 0; i < matrix.length; i++){
			for(int j = i+1; j < matrix.length; j++){
				if(matrix[i][j] > -1){
					solutionWriter.addEdgeOnPage(i, j, matrix[i][j]);	
				}				
			}
		}
		
		try {
			solutionWriter.write(writePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
