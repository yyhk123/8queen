

public class main {

	public static void main(String[] args) {
		int mutationRate = 10;
		long initialT;
		long finalT;
		initialT = System.currentTimeMillis();
		int totalcost=0;
		GeneticsAlgorithm g;
		for(int it=1; it<=500; it++) {
			g = new GeneticsAlgorithm(4, 20, 20); // 20x 20, 20 queens
			g.generatePopulation();
			
			System.out.println("Calculating " + it +"th board");
			
			
			int i = 0;
			
			for (; g.bestBoard().fitness() > 0; i++) {
				g.crossover();
				g.mutate(mutationRate);
			}
			totalcost+=i;
			//g.bestBoard().print();
		}		
		finalT = System.currentTimeMillis();
		System.out.println("------------------");
		System.out.println("Total 500 boards took: " +totalcost+" Generations\nSolution:");
		System.out.println("Average cost: " + totalcost/500);
		System.out.println("Time in s: " + ((double)(finalT - initialT)/1000) + " s");
		

        Node.setSize(25); // set 25 x 25 board size.

        double totalCost = 0;
        double avgCost = 0;
        double solved = 0;
        double fail = 0;
        
		long initialT2;
		long finalT2;
		initialT2 = System.currentTimeMillis();
        

        for (int i = 1; i <= 500; i++) { // run 500 times

            SimulatedAnnealing SA  = new SimulatedAnnealing();
            Node simulatedAnnealNode = SA.simulatedAnneal(25, 0.001);
            
            System.out.println("Calculating " + i +"th board");

            if(simulatedAnnealNode.getconflict() == 0){
            	solved++;
            }
            else {
            	fail++;
            }

            totalCost += SA.getNodeCount();

        }
        finalT2 = System.currentTimeMillis();

        avgCost = totalCost / 500;
        System.out.println("------------------");
        System.out.println("Total Cost = " + totalCost + "\nAverage Cost = " + avgCost);
        System.out.println("Solved : Fail ratio  = " + solved + " : " + fail);
        System.out.println("Total time: " + ((double)(finalT2 - initialT2)/1000) + " s");

	}

}
