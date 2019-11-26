
import java.util.Random;

public class SimulatedAnnealing {
	int nodeCount;
	private Queen[] startState;
	private Node start;
	
	
	public SimulatedAnnealing() {
		Queen[] queen = new Queen[Node.getSize()];
		Random gen = new Random();
		
		nodeCount = 0;
		start = new Node();
		startState = new Queen[Node.getSize()];
		
		for (int i = 0; i < Node.getSize(); i++) {
			queen[i] = new Queen(gen.nextInt(Node.getSize()), i);
		}
		
		for (int i = 0; i < Node.getSize(); i++) {
			startState[i] = new Queen(queen[i].getRow(), queen[i].getCol());
		}
		start.setState(startState);
		start.computeCost();
		
	}


	public Node simulatedAnneal(double initialTemp, double step) {
		Node currentNode = start;
		double temp = initialTemp;
		
		double val = step;
		
		Node nextNode = new Node();
		
		while (temp > 0 && currentNode.getconflict() != 0){
			double probability;
			double determine;
			int i;
			
			nextNode = currentNode.getRandomNeighbour(currentNode);
			nodeCount++;
			
			if (nextNode.getconflict() == 0)
				return nextNode;
			
			i = currentNode.getconflict() - nextNode.getconflict();
			
			if (i <= 0) {
				probability = Math.exp(i/temp);
				determine = Math.random();
				
				if (determine <= probability) {
					currentNode = nextNode;
				}
			} 
			else {
				currentNode = nextNode;
			}
			
			temp -= val;
		}
		
		return currentNode;
	}
	

	public int getNodeCount(){
		return nodeCount;
	}

	public Node getStartNode(){
		return start;
	}
	
	public static void main(String[] args){

        Node.setSize(25); // set 25 x 25 board size.

        double totalCost = 0;
        double avgCost = 0;
        double solved = 0;
        double fail = 0;
        
		long initialT;
		long finalT;
		initialT = System.currentTimeMillis();
        

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
        finalT = System.currentTimeMillis();

        avgCost = totalCost / 500;
        System.out.println("------------------");
        System.out.println("Total Cost = " + totalCost + "\nAverage Cost = " + avgCost);
        System.out.println("Solved : Fail ratio  = " + solved + " : " + fail);
        System.out.println("Total time: " + ((double)(finalT - initialT)/1000) + " s");

	}
	
	
}