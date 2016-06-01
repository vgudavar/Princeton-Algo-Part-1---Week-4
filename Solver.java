

import java.util.Iterator;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	
	private SearchNode solution;
	private final Board initial;
	
	private class SearchNode implements Comparable<SearchNode> {
		Board board;
		int moves_made;
		SearchNode prev;
		
		public SearchNode(Board a, int moves,SearchNode SN){
			this.board = a;
			this.moves_made = moves;
			this.prev = SN;
		}

		@Override
		public int compareTo(SearchNode SN) {
			if ((this.board.manhattan() + this.moves_made) < (SN.board.manhattan() + SN.moves_made)) return -1;
			else if ((this.board.manhattan() + this.moves_made) > (SN.board.manhattan() + SN.moves_made)) return +1;
			else return 0;
		}
		
	} 
	

	
	public Solver(Board initial) {
		this.initial = initial;
		SearchNode init = new SearchNode(initial, 0, null);
		MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
		pq.insert(init);
		pq.insert(new SearchNode(init.board.twin(), 0, null));
		while(!pq.isEmpty()){
			SearchNode curr = pq.delMin();
			if (!curr.board.isGoal()) {
				Iterable<Board> q = curr.board.neighbors();
				Iterator<Board> iter = q.iterator();
				while(iter.hasNext()){
					Board b = iter.next();
					if(curr.prev == null) {
						pq.insert(new SearchNode(b, curr.moves_made + 1, curr));
					}
					else if (! curr.prev.board.equals(b)) {
						pq.insert(new SearchNode(b, curr.moves_made + 1, curr));
					}
				}
			}
			
			else {
				solution = curr;
				break;
			}
		}
	}
	
	public boolean isSolvable() {
		SearchNode s = solution;
		while(s.prev != null) {
			s = s.prev;
		}
		
		if (s.board.equals(initial)) return true;
		else  return false;
	}
	
	public int moves() {
		if (!isSolvable()) return -1;
		else return solution.moves_made;
	}
	
	public Iterable<Board> solution() {
		if (!isSolvable()) return null;
		Stack<Board> q = new Stack<Board>();
		SearchNode s = solution;
		while(s != null) {
			q.push(s.board);
			s = s.prev;
		}
		return q;
	}
	
	
	public static void main(String[] args) {
		 int N = 3;
		 int[][] blocks = new int[N][N];
		  blocks[0][0] = 1;
		  blocks[0][1] = 2;
		  blocks[0][2] = 3;
		  blocks[1][0] = 4;
		  blocks[1][1] = 5;
		  blocks[1][2] = 6;
		  blocks[2][0] = 7;
		  blocks[2][1] = 8;
		  blocks[2][2] = 0;
		  Board initial = new Board(blocks);
		  Solver solver = new Solver(initial);
		  if (!solver.isSolvable())
		        StdOut.println("No solution possible");
		    else {
		        StdOut.println("Minimum number of moves = " + solver.moves());
		        for (Board board : solver.solution())
		            StdOut.println(board);
		    }
		}
	}
