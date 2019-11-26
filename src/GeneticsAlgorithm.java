

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;


public class GeneticsAlgorithm {

	private LinkedList<GABoard> col;
	private int population;


	public GeneticsAlgorithm(int generations, int size, int queens) {
		GABoard g = new GABoard();
		col = new LinkedList<>();
		population = generations;
		g.setSize(size);
		g.setQueen(queens);
	}
	
	public void generatePopulation() {
		for (int i = 0; i < population; i++) {
			int temp;
			GABoard board;
			do {
				board = new GABoard();
				board.genQueens();
				temp = col.indexOf(board);
			} while (temp != -1);
			col.add(board);
		}
	}
	

	public void rank() {
		Collections.sort(col, new Comparator<GABoard>() {
			public int compare(GABoard o1, GABoard o2) {
				int f2 = o2.fitness();
				int f1 = o1.fitness();
				if (f2 > f1) {
					return -1;
				} else if (f2 < f1) {
					return 1;
				}
				return 0;
			}
		});
	}


	public void crossover() {
		rank();
		
		Iterator<GABoard> i = col.iterator();
		LinkedList<GABoard> b = new LinkedList<>(col);
		GABoard b1 = null;
		GABoard b2 = null;
		for (int a = 0; a < population; a++) {
			b2 = i.next();
			GABoard n = crossover(b1, b2);
			b.set(a, n);
			b1 = b2;
		}
		col = b;
		rank();
	}


	public static GABoard crossover(GABoard b1, GABoard b2) {
		if (b1 == null)
			return b2;
		if (b2 == null)
			return b1;
		if (b1.size() != b2.size())
			return null;
		GABoard b = new GABoard();
		if(Math.random()>0.5) {
			GABoard s = b1;
			b1 = b2;
			b2 = s;
		}
		for (int i = b.size() / 2; i < b.size(); i++) {
			if (i < b.size() / 2) {
				b.setQueen(i, b1.getQueen(i));
			} else {
				b.setQueen(i, b2.getQueen(i));
			}
		}
		b.repairBoard();
		return b;
	}

	public void mutate(int mutation) {
		int a = (col.size()-1) * mutation / 100;
		if (mutation > 0 && a < 1)
			a = 1;
		for (int i = 0; i < a; i++) {
			int pos = (int) (Math.random() * (col.size()-1));
			col.set(pos, mutation(col.get(pos), mutation));
			col.get(pos).repairBoard();
		}
	}


	public static GABoard mutation(GABoard board, int mutate) {
		GABoard B = new GABoard();
		int temp = Math.round(B.size() ^ 2 * mutate / 100);
		if (mutate > 0 && temp < 1)
			temp = 1;
		for (int i = 0; i < temp; i++) {
			int p1 = (int)(Math.random() * B.size());
			int p2 = (int)(Math.random() * B.size());
			byte by = board.getQueen(p1);
			B.setQueen(p1, board.getQueen(p2));
			B.setQueen(p2, by);
		}
		return B;
	}

	public GABoard bestBoard() {
		return col.getFirst();
	}
	
	
	
//	static public void main(String args[]) {
//		int mutationRate = 10;
//		long initialT;
//		long finalT;
//		initialT = System.currentTimeMillis();
//		int totalcost=0;
//		GeneticsAlgorithm g;
//		for(int it=1; it<=500; it++) {
//			g = new GeneticsAlgorithm(4, 20, 20); // 20x 20, 20 queens
//			g.generatePopulation();
//			
//			System.out.println("Calculating " + it +"th board");
//			
//			
//			int i = 0;
//			
//			for (; g.bestBoard().fitness() > 0; i++) {
//				g.crossover();
//				g.mutate(mutationRate);
//			}
//			totalcost+=i;
//			//g.bestBoard().print();
//		}		
//		finalT = System.currentTimeMillis();
//		System.out.println("------------------");
//		System.out.println("Total 500 boards took: " +totalcost+" Generations\nSolution:");
//		System.out.println("Average cost: " + totalcost/500);
//		System.out.println("Time in s: " + ((double)(finalT - initialT)/1000) + " s");
//		
//	}
	
	

}