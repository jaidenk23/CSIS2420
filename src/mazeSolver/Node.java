package mazeSolver;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class is to create the individual data holding blocks for the various
 * data structures.
 * 
 * CSIS2420 FINAL PROJECT
 * 
 * @author Jaiden Kazemini && Clinton Choi
 *
 */
public class Node {

	private int Xpos;
	private int Ypos;
	private Color nodeColor = Color.LIGHT_GRAY;
	private final int WIDTH = 35;
	private final int HEIGHT = 35;
	private Node left, right, up, down;

	private double fcost;

	public Node(int x, int y) {
		Xpos = x;
		Ypos = y;
	}

	public Node() {
	}

	/**
	 * Returns the distance between one node to another.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double distance(Node a, Node b) {
		double x = Math.pow(a.Xpos - b.Xpos, 2);
		double y = Math.pow(a.Ypos - b.Ypos, 2);

		return Math.sqrt(x + y);
	}

	/**
	 * Sets the color and look of the node.
	 * 
	 * @param g
	 */
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawRect(Xpos, Ypos, WIDTH, HEIGHT);
		g.setColor(nodeColor);
		g.fillRect(Xpos + 1, Ypos + 1, WIDTH - 1, HEIGHT - 1);
	}

	/**
	 * This is the where the actions of the user are translated into how the nodes
	 * will react.
	 * 
	 * A left click creates a black node which is a wall. A scroll click creates a
	 * green node which is the start of the maze search. A right click creates a red
	 * node which is the end goal. If any of them are clicked again, they turn into
	 * a part of the path again.
	 * 
	 * @param buttonCode
	 */
	public void Clicked(int buttonCode) {
		System.out.println("called");
		if (buttonCode == 1) {
			// WALL
			nodeColor = Color.BLACK;

		}
		if (buttonCode == 2) {
			// START
			nodeColor = Color.GREEN;

		}
		if (buttonCode == 3) {
			// END
			nodeColor = Color.RED;

		}
		if (buttonCode == 4) {
			// CLEAR
			clearNode();

		}
	}

	/**
	 * Sets the color of the node.
	 * 
	 * @param c
	 */
	public void setColor(Color c) {
		nodeColor = c;
	}

	/**
	 * Returns the color of the node.
	 * 
	 * @return
	 */
	public Color getColor() {
		return nodeColor;
	}

	/**
	 * Returns a list of nodes that are adjacent and open.
	 * 
	 * @return
	 */
	public List<Node> getNeighbours() {
		List<Node> neighbours = new ArrayList<>();
		if (left != null && left.isPath())
			neighbours.add(left);
		if (down != null && down.isPath())
			neighbours.add(down);
		if (right != null && right.isPath())
			neighbours.add(right);
		if (up != null && up.isPath())
			neighbours.add(up);

		return neighbours;
	}

	/**
	 * Sets the directions of which way to go.
	 * 
	 * @param l = left
	 * @param r = right
	 * @param u = up
	 * @param d = down
	 */
	public void setDirections(Node l, Node r, Node u, Node d) {
		left = l;
		right = r;
		up = u;
		down = d;
	}

	/**
	 * Sets a node to the color Light Gray.
	 */
	public void clearNode() {
		nodeColor = Color.LIGHT_GRAY;
	}

	/**
	 * Returns the X position.
	 * 
	 * @return
	 */
	public int getX() {
		return (Xpos - 15) / WIDTH;
	}

	/**
	 * Returns the Y position.
	 * 
	 * @return
	 */
	public int getY() {
		return (Ypos - 15) / HEIGHT;
	}

	/**
	 * Sets the x position.
	 * 
	 * @param x
	 * @return
	 */
	public Node setX(int x) {
		Xpos = x;
		return this;
	}

	/**
	 * Sets the y position.
	 * 
	 * @param y
	 * @return
	 */
	public Node setY(int y) {
		Ypos = y;
		return this;
	}

	/**
	 * Sets the color of a wall to be black.
	 * 
	 * @return
	 */
	public boolean isWall() {
		return (nodeColor == Color.BLACK);
	}

	/**
	 * Sets the color of a wall to be green.
	 * 
	 * @return
	 */
	public boolean isStart() {
		return (nodeColor == Color.GREEN);
	}

	/**
	 * Sets the color of a wall to be red.
	 * 
	 * @return
	 */
	public boolean isEnd() {
		return (nodeColor == Color.RED);
	}

	/**
	 * Sets the color of a wall to be light gray.
	 * 
	 * @return
	 */
	public boolean isPath() {
		return (nodeColor == Color.LIGHT_GRAY || nodeColor == Color.RED);
	}

	/**
	 * Sets the path to be blue when already visited/searched.
	 * 
	 * @return
	 */
	public boolean isSearched() {
		return (nodeColor == Color.BLUE || nodeColor == Color.ORANGE);
	}

}