


public class GABoard {
	
	private int size; // size of board
	private byte queens[];


	public GABoard() {
		size = 20;
		queens = new byte[20];
	}
	
	public int size() {
		return size;
	}
	
	public void setSize(int s) {
		size = s;
	}
	public void setQueen(int q) {
		queens = new byte[q];
	}

	public void genQueens() {
		for (int i = 0; i < size; i++) {
			byte r = (byte) (Math.random() * size);
			for(int j = 0; j < i; j++){
				if(queens[j] == r)
				{
					j = -1;
					r = (byte) (Math.random() * size);
				}
			}
			queens[i] = r; 
		}
	}


	public int fitness() {
		int fitness = 0;
		for(int i = 0; i<size;i++) {
			for(int j = i+1; j<size;j++) {
				if(Math.abs(queens[i]-queens[j])==Math.abs(i-j)) 
					fitness++;
			}
		}
		return fitness/2;
	}

	
	public void setQueen(int i, byte val) {
		queens[i] = val;
	}
	
	public byte getQueen(int i) {
		return queens[i];
	}
	
	public void repairBoard() {
		for (int i = 0; i < size(); i++) {
			for (int j = 0; j < size(); j++) {
				if (i==j)  {
					j++;
					if(j>=size()) {
						break;
					}
				}
				if (getQueen(i) == getQueen(j)) {
					int k;
					if (Math.random() > 0.5)
						k = i;
					else
						k = j;
					byte r = (byte) (Math.random() * size());
					for (int t = 0; t < size(); t++) {
						if (getQueen(t) == r) {
							t = -1;
							r = (byte) (Math.random() * size());
						}
					}
					setQueen(k, r);
				}
			}
		}
	}
	
	
	
	public void print() {
		for (int i = 0; i < size; i++) {
			int a = 0;
			for (; a < queens[i]; a++) {
				System.out.print(". ");
			}
			System.out.print("Q ");
			a++;
			for (; a < size; a++) {
				System.out.print(". ");
			}
			System.out.println();
		}
	}
	
	
}