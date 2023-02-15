package mazeSolver;

import java.awt.Color;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * This class has the two Algorithms that the maze will use: Depth First Search
 * and Breadth First Search. It also contains the searchTime, meaning how fast
 * the maze will be solved or run through.
 * 
 * CSIS2420 FINAL PROJECT
 * 
 * @author Jaiden Kazemini && Clinton Choi
 *
 */
public class MazeSolver {

	private int searchtime = 100;

	/**
	 * Returns the searchTime;
	 * 
	 * @return
	 */
	public int getSearchTime() {
		return searchtime;
	}

	/**
	 * Sets the searchTime;
	 * 
	 * @param searchtime
	 */
	public void setSearchTime(int searchtime) {
		this.searchtime = searchtime;
	}

	/**
	 * This is the depth first search algorithm.
	 * 
	 * It uses a node as a parameter and uses a stack as a way to keep track of
	 * where it has or hasn't been. It also colors the path as it goes along.
	 * 
	 * @param start
	 */
	public void depthFirstSearch(Node start) {
		Stack<Node> nodes = new Stack<>();
		nodes.push(start);

		while (!nodes.empty()) {

			Node curNode = nodes.pop();
			if (!curNode.isEnd()) {

				curNode.setColor(Color.RED);
				try {
					Thread.sleep(searchtime);
				} catch (Exception e) {
					e.printStackTrace();
				}
				curNode.setColor(Color.BLUE);
				for (Node adjacent : curNode.getNeighbours()) {
					nodes.push(adjacent);
				}

			}

			else {
				curNode.setColor(Color.MAGENTA);
				break;
			}
		}

	}

	/**
	 * This is the breadth first search algorithm. It takes a starting node, an end
	 * node, the width, and height of the graph as well.
	 * 
	 * This algorithm uses a queue to keep track of where it has been.
	 * 
	 * @param start
	 * @param end
	 * @param graphWidth
	 * @param graphHeight
	 */
	public void breadthFirstSearch(Node start, Node end, int graphWidth, int graphHeight) {
		Queue<Node> queue = new LinkedList<>();
		Node[][] prev = new Node[graphWidth][graphHeight];

		queue.add(start);
		while (!queue.isEmpty()) {

			Node curNode = queue.poll();
			if (curNode.isEnd()) {
				curNode.setColor(Color.MAGENTA);
				break;
			}

			if (!curNode.isSearched()) {
				curNode.setColor(Color.ORANGE);
				try {
					Thread.sleep(searchtime);
				} catch (Exception e) {
					e.printStackTrace();
				}
				curNode.setColor(Color.BLUE);
				for (Node adjacent : curNode.getNeighbours()) {
					queue.add(adjacent);
					prev[adjacent.getX()][adjacent.getY()] = curNode;

				}
			}
		}

		shortpath(prev, end);
	}

	/**
	 * Returns the shortest path for breadth first search.
	 * 
	 * @param prev
	 * @param end
	 */
	private void shortpath(Node[][] prev, Node end) {
		Node pathConstructor = end;
		while (pathConstructor != null) {
			pathConstructor = prev[pathConstructor.getX()][pathConstructor.getY()];

			if (pathConstructor != null) {
				pathConstructor.setColor(Color.ORANGE);
			}
			try {
				Thread.sleep(searchtime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
