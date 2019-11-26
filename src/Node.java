

import java.util.ArrayList;
import java.util.Random;

public class Node implements Comparable<Node>{

	static private int SIZE;
	
	private Queen[] queen;
	
	private ArrayList<Node> neighbours;

	private int conflict=0;

	public Node() {
		SIZE = 8; // DEFAULT 8 QUEEN
		queen = new Queen[SIZE];
		neighbours = new ArrayList<>();
		conflict = 0;
	}

	public Node(Node node) {
		queen = new Queen[SIZE];
		neighbours = new ArrayList<>();
		for (int i = 0; i < SIZE; i++) {
			queen[i] = new Queen(node.queen[i].getRow(), node.queen[i].getCol());
		}
	}

	public Node(Queen[] queens) {
		neighbours = new ArrayList<>();
		
		queen = new Queen[SIZE];
		for (int i = 0; i < SIZE; i++) {
			queen[i] = queens[i];
		}
		
	}

	static public int getSize() {
		return SIZE;
	}

	static public void setSize(int Size) {
		SIZE = Size;
	}

	public ArrayList<Node> generateNeighbours(Node start){

		int count=0;
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 1; j < SIZE; j++) {
				neighbours.add(count, new Node(start));
				neighbours.get(count).queen[i].moveDown(j);
				neighbours.get(count).computeCost();
				count++;
			}
		}
		
		return neighbours;
	}

	public Node getRandomNeighbour(Node start){
		Random rand = new Random();
		
		int col = rand.nextInt(SIZE);
		int d = rand.nextInt(SIZE - 1) + 1;
		
		Node node = new Node(start);
		
		node.queen[col].moveDown(d);
		node.computeCost();
		
		return node;
	}

	public int computeCost(){
		for (int i = 0; i < SIZE - 1; i++){
			for (int j = i + 1; j < SIZE; j++){
				if (queen[i].attackable(queen[j])){
					conflict++;
				}
			}
		}

		return conflict;
	}

	public int getconflict(){
		return conflict;
	}

	public int compareTo(Node n){
		if (conflict < n.getconflict()) {
			return -1;
		} else if (conflict > n.getconflict()) {
			return 1;
		} else {
			return 0;
		}
	}

	public void setState(Queen[] s) {
		for(int i = 0; i < SIZE; i++){
			queen[i]= new Queen(s[i].getRow(), s[i].getCol());
		}
	}

	public Queen[] getState(){
		return queen;
	}

}