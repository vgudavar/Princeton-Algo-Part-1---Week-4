

import java.util.Arrays;
import java.util.Iterator;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
	
	private final int[][] board;
	
	public Board(int[][] blocks){
		this.board = deepCopy(blocks);
	}
	
	public int dimension() {
		return this.board[0].length;
	}
	
	public int hamming(){
		int count = 0;
		for(int i = 0; i < this.board[0].length; i++){
			for(int j = 0; j < this.board[0].length; j++){
				if(this.board[i][j] != dimension()*i + j + 1 && this.board[i][j] != 0){
					count++;
				}
			}
		}
		return count;
	}
	
	public int manhattan() {
		int manhattan_distance = 0;
		for(int i = 0; i < this.board[0].length; i++){
			for(int j = 0; j < this.board[0].length; j++){
				manhattan_distance = manhattan_distance + manhattan_helper(i, j, this.board[i][j]);
			}
		}
		
		return manhattan_distance;
	}
		
	private int manhattan_helper(int i, int j, int num){
		if (num == 0) return 0;
		if (num % dimension() == 0){
			int p = num / dimension();
			p = p - 1;
			int q = dimension() - 1;
			return Math.abs(i-p)+Math.abs(j-q);
		}
		int p = num / dimension();
		int q = num % dimension();
		q = q - 1;
		return Math.abs(i-p)+Math.abs(j-q);
	}
	
	public boolean isGoal(){
		for(int i = 0; i < this.board[0].length; i++){
			for(int j = 0; j < this.board[0].length; j++){
				if(this.board[i][j] != i*dimension() + j + 1 && this.board[i][j] != 0){
					return false;
				}
			}
		}
		return true;
	}
	
	public Board twin(){
		if(dimension() < 3) {
			for(int i = 0; i < this.board[0].length; i++){
				for(int j = 0; j < this.board[0].length; j++){
					if (this.board[i][j] == 0) {
						if (i == 0){
							return swap(1,0,1,1);
						}
						else {
							return swap(0,0,0,1);
						}
					}
				}
			}
		}
		else {
			for(int i = 0; i < this.board[0].length; i++){
				for(int j = 0; j < this.board[0].length; j++){
					if (this.board[i][j] == 0) {
						if (i == 0){
							return swap(1,1,2,1);
						}
						else if(i == dimension() - 1){
							return swap(0,1,1,1);
						}
						else return swap(0,1,dimension()-1,1);
					}
			
				}
			}
			return null;
		}
		return null;
	}
	
	private int[][] deepCopy(int[][] original) {
	    if (original == null) {
	        return null;
	    }
	    
	    int[][] result = new int[original.length][];
	    for (int i = 0; i < original.length; i++) {
	        result[i] = Arrays.copyOf(original[i], original[i].length);
	    }
	    return result;
	}
	
		
	
	private Board swap(int p, int q, int r, int s){
		Board x = new Board(deepCopy(this.board));
		int t = x.board[p][q];
		x.board[p][q] = x.board[r][s];
		x.board[r][s] = t;
		return x;
	}
	
	public boolean equals(Object y){
		if (y == null) return false;
		if (y.getClass() != this.getClass()) return false;
		Board x = (Board) y;
		if (x.board == null) return false;
		if (this.dimension() != x.dimension()) return false;
		for(int i = 0; i < this.board[0].length; i++){
			for(int j = 0; j < this.board[0].length; j++){
				if (this.board[i][j] != x.board[i][j]) return false;
			}
		}
		return true;
	}
	
	public Iterable<Board> neighbors() {
		Queue<Board> q = new Queue<Board>();
		for(int i = 0; i < this.board[0].length; i++){
			for(int j = 0; j < this.board[0].length; j++){
				if (this.board[i][j] == 0){
					if (i-1 >= 0) {
						q.enqueue(swap(i, j, i-1, j));
					}
					if (j-1 >= 0) {
						q.enqueue(swap(i, j, i, j-1));
					}
					if (i+1 < dimension()) {
						q.enqueue(swap(i, j, i+1, j));
					}
					if (j+1 < dimension()) {
						q.enqueue(swap(i, j, i, j+1));
					}
				}
			}
		}
	    return q;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(dimension() + "\n");
		for(int i = 0; i < this.board[0].length; i++){
			for(int j = 0; j < this.board[0].length; j++){
				s.append(this.board[i][j] + " ");
			}
		    s.append("\n");
		}
		
		return s.toString();
	}

	
	public static void main(String[] args) {
		int[][] a = new int[2][2];
		a[0][0] = 1;
		a[0][1] = 2;
		a[1][0] = 0;
		a[1][1] = 3;
		Board b = new Board(a);
		int[][] x = b.deepCopy(a);
		System.out.println(x[0][0] + " " + x[0][1] + " " + x[1][0] + " " + x[1][1]);
		x[0][0] = 2;
		x[0][1] = 1;
		System.out.println(x[0][0] + " " + x[0][1] + " " + x[1][0] + " " + x[1][1]);
		System.out.println(a[0][0] + " " + a[0][1] + " " + a[1][0] + " " + a[1][1]);
		
	}
}
