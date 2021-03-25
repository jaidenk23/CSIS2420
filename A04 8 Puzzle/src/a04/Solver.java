package a04;

import edu.princeton.cs.algs4.*;

/**
 * @author Mark Rader, Jaiden Kazemini
 */
public class Solver {

	private Stack<Board> solution;
	private int moves;

	private class SearchNode implements Comparable<SearchNode> {
		private int moves;
		private Board board;
		private SearchNode prev;

		public SearchNode(Board board, int moves, SearchNode prev) {
			this.board = board;
			this.moves = moves;
			this.prev = prev;
		}

		@Override
		public int compareTo(SearchNode o) {
			int diff = this.board.manhattan() + this.moves - o.board.manhattan() - o.moves;
			if (diff != 0)
				return diff;
			if (this.moves > o.moves)
				return -1;
			return 1;
		}

	}

	// find a solution to the initial board (using the A* alg)
	public Solver(Board initial) {
		if (!initial.isSolvable())
			throw new IllegalArgumentException();
		solution = new Stack<>();
		MinPQ<SearchNode> q = new MinPQ<>();
		q.insert(new SearchNode(initial, 0, null));
		while (true) {
			SearchNode move = q.delMin();
			if (move.board.isGoal()) {
				this.moves = move.moves;
				do {
					solution.push(move.board);
					move = move.prev;
				} while (move != null);
				return;
			}
			for (Board next : move.board.neighbors()) {
				if (move.prev == null || !next.equals(move.prev.board))
					q.insert(new SearchNode(next, move.moves + 1, move));
			}
		}
	}

	// min number of moves to solve the initial board
	public int moves() {
		return moves;
	}

	// sequence of boards in a shortest solution
	public Iterable<Board> solution() {
		return solution;
	}

	// solve a slider puzzle (given from assignment instructions)
	public static void main(String[] args) {
		// create initial board from file
		String fileName = "src/a04/puzzle3x3-03.txt";
		In in = new In(fileName);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// check if puzzle is solvable; if so, solve it and output solution
		if (initial.isSolvable()) {
			Solver solver = new Solver(initial);
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}

		// if not, report unsolvable
		else {
			StdOut.println("Unsolvable puzzle");
		}

	}
}
