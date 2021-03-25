package a04;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * @author Mark Rader, Jaiden Kazemini
 *
 *
 *
 */
public class Board {

	private final int N;
	private final int[] board;
	private int blankSpot;

	// constructor to make initial board from 2d array
	public Board(int[][] blocks) {
		if (blocks == null)
			throw new NullPointerException();
		N = blocks[0].length;
		board = new int[N * N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] == 0)
					blankSpot = (i * N + j);
				board[i * N + j] = blocks[i][j];
			}
		}
	}

	// constructor to make boards from 1d array
	private Board(int[] blocks, int N, int blankSpot) {
		this.N = N;
		this.blankSpot = blankSpot;
		board = new int[N * N];
		System.arraycopy(blocks, 0, board, 0, board.length);
	}

	public int size() {
		return N;
	}

	// returns the hamming distance, or number of elements out of position
	public int hamming() {
		int hamming = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] != 0 && board[i] != i + 1) {
				hamming++;
			}
		}
		return hamming;
	}

	// returns the manhattan distance, or number of moves to get each element into
	// correct position
	public int manhattan() {
		int distance = 0;
		for (int i = 0; i < board.length; i++) {

			if (board[i] == 0)
				continue; // 0 is not a block

			int index = board[i] - 1; // adjusting to the array starting at 0
			index = index < 0 ? board.length - 1 : index;

			int currentValueRow = i / (N);
			int currentValueCol = i % (N);
			int goalValueRow = index / (N);
			int goalValueCol = index % (N);

			distance += Math.abs(currentValueRow - goalValueRow) + Math.abs(currentValueCol - goalValueCol);
		}
		return distance;
	}

	// checks to see if this board is the goal board
	public boolean isGoal() {
		for (int i = 0; i < board.length; i++) {
			if (board[i] != 0 && board[i] != i + 1) {
				return false;
			}
		}
		return true;
	}

	// checks if the given game board can be solved. Checks the dimensions of the
	// board and the number of inversions
	public boolean isSolvable() {
		int inversions = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 0)
				continue; // skip the blank inversion
			for (int k = i; k < board.length; k++) {
				if (board[k] < board[i] && board[k] != 0) {
					inversions++;
				}
			}
		}
		boolean isBoardEven = (N % 2) == 0; // checks if board is even
		if (isBoardEven) {
			inversions += blankSpot / N;
		}
		boolean numInversions = (inversions % 2) == 0;

		return isBoardEven != numInversions;
	}

	@Override
	public boolean equals(Object y) {
		if (y == this)
			return true;
		if (y == null)
			return false;
		if (getClass() != y.getClass())
			return false;

		Board that = (Board) y;
		return Arrays.equals(this.board, that.board);
	}

	// Swapping elements in an array, to not duplicate code
	private void swap(int[] tempBoard, int index1, int index2) {
		int temp = tempBoard[index1];
		tempBoard[index1] = tempBoard[index2];
		tempBoard[index2] = temp;
	}

	// add game board to stack of neighbor boards
	private void pushToStack(Stack<Board> neighbors, int move) {
		swap(board, blankSpot, blankSpot + move); // swap the blank spot with a neighbor
		neighbors.push(new Board(board, N, blankSpot + move));
		swap(board, blankSpot, blankSpot + move); // reset the board for the next version
	}

	// returns possible neighboring game boards to a stack of boards
	public Iterable<Board> neighbors() {
		Stack<Board> neighbors = new Stack<>();

		if (blankSpot / N != 0) {
			pushToStack(neighbors, -N);
		}
		if (blankSpot / N != N - 1) {
			pushToStack(neighbors, N);
		}
		if (blankSpot % N != 0) {
			pushToStack(neighbors, -1);
		}
		if (blankSpot % N != N - 1) {
			pushToStack(neighbors, 1);
		}

		return neighbors;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(N + "\n");
		for (int i = 0; i < board.length; i++) {
			sb.append(String.format("%2d ", board[i]));
			if ((i + 1) % N == 0)
				sb.append("\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) {

		Board test = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } });
		StdOut.println(test.toString());

		Stack<Board> boardStack = new Stack<>();
		StdOut.println(test.neighbors().toString());

	}
}
