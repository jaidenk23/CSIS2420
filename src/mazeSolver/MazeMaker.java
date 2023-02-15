package mazeSolver;

import java.awt.Canvas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;

/**
 * 
 * CSIS2420 FINAL PROJECT
 * 
 * @author Jaiden Kazemini && Clinton Choi
 *
 */

/**
 * The MazeMaker class creates the GUI and uses methods from the other two
 * classes in order for the finished product to display and function properly.
 * 
 */
public class MazeMaker extends Canvas implements Runnable, MouseListener {
	/**
	 * Initialize variables.
	 * 
	 */
	Random rand = new Random(2);
	private static Node start = null;
	private static Node target = null;
	private static JFrame frame;

	private Node[][] nodeList;
	private static MazeMaker runTimeMain;
	private static MazeSolver algorithm;

	private final static int WIDTH = 1024;
	private final static int HEIGHT = 768;

	private final static int widthOfBoard = 28;
	private final static int heightOfBoard = 19;

	/**
	 * @wbp.parser.entryPoint
	 * 
	 *                        Main method is the method that will run when the class
	 *                        is ran, and is what displays the GUI by calling other
	 *                        methods.
	 */
	public static void main(String[] args) {
		frame = new JFrame("Maze Teacher");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		MazeMaker m = new MazeMaker();
		algorithm = new MazeSolver();
		// check
		m.setBounds(0, 25, WIDTH, HEIGHT);
		SetupMenu(frame);
		runTimeMain = m;
		// check
		frame.getContentPane().add(m);
		frame.setVisible(true);
		m.start();

	}

	/**
	 * @wbp.parser.entryPoint
	 * 
	 *                        SetupMenu method is the bulk of the GUI code, which
	 *                        displays each JMenuItem and contains the actions for
	 *                        them to actually function.
	 * 
	 */
	public static void SetupMenu(JFrame frame) {
		JMenuBar bar = new JMenuBar();
		bar.setBackground(Color.WHITE);
		bar.setBounds(0, 0, WIDTH, 25);
		frame.getContentPane().add(bar);
		JMenu boardMenu = new JMenu("Board");
		bar.add(boardMenu);
		JMenu algorithmsMenu = new JMenu("Algorithms");
		bar.add(algorithmsMenu);

		JMenuItem newGrid = new JMenuItem("New Board");
		JMenuItem clearSearch = new JMenuItem("Clear Search Results");
		JMenuItem randomBoard = new JMenuItem("Random Board");

		JMenuItem bfsItem = new JMenuItem("Breadth-First Search");
		JMenuItem dfsItem = new JMenuItem("Depth-First Search");
		JMenuItem searchTime = new JMenuItem("Search Time Per Node");

		newGrid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				runTimeMain.createNodes(true);
			}
		});
		clearSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				runTimeMain.clearSearchResults();
			}
		});
		randomBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				runTimeMain.createNodesRandomly();
			}
		});

		bfsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (runTimeMain.isMazeValid()) {
					algorithm.breadthFirstSearch(runTimeMain.start, runTimeMain.target, runTimeMain.widthOfBoard,
							runTimeMain.heightOfBoard);
				} else {
					System.out.println("DIDNT LAUNCH");
				}

			}

		});
		dfsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (runTimeMain.isMazeValid()) {
					algorithm.depthFirstSearch(runTimeMain.getStart());
				} else {
					System.out.println("DIDNT LAUNCH");
				}

			}

		});

		searchTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String input = JOptionPane.showInputDialog(null,
						"Enter the time you want to search each node in milliseconds(default = 100ms) ", "Search Time",
						JOptionPane.QUESTION_MESSAGE);
				algorithm.setSearchTime(Integer.parseInt(input));
			}
		});

		boardMenu.add(newGrid);
		boardMenu.add(clearSearch);
		boardMenu.add(randomBoard);
		algorithmsMenu.add(dfsItem);
		algorithmsMenu.add(bfsItem);
		algorithmsMenu.add(searchTime);

		JButton btnNewButton = new JButton("Instructions");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame,
						"To begin click *Board*, then click *Random Board*. Middle click any grey box to set the beginning of the maze, then right click any grey box to set the end of the maze. "
								+ "\nThen choose the time you want to search each node under *Algorithms*, and choose your algorithm. \nOnce it is complete you can click *Clear Search Results* and choose the other algorithm, "
								+ "or restart the process with a new random maze. Have fun!");// Note: Input can be
																								// null.

			}
		});

		bar.add(btnNewButton);

	}

	public void run() {
		init();
		while (true) {
			// check
			BufferStrategy bs = getBufferStrategy(); // check
			if (bs == null) {
				// check
				createBufferStrategy(2);
				continue;
			}
			// check
			Graphics2D grap = (Graphics2D) bs.getDrawGraphics(); // check
			render(grap);
			bs.show();
			try {
				Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void init() {
		// check
		requestFocus();
		addMouseListener(this);
		nodeList = new Node[widthOfBoard][heightOfBoard];
		createNodes(false);
		setMazeDirections();
	}

	/**
	 * Sets the direction of the maze itself using heightOfBoard and widthOfBoard
	 * alongside nodeList.
	 * 
	 */
	public void setMazeDirections() {
		for (int i = 0; i < nodeList.length; i++) {
			for (int j = 0; j < nodeList[i].length; j++) {
				Node up = null, down = null, left = null, right = null;
				int u = j - 1;
				int d = j + 1;
				int l = i - 1;
				int r = i + 1;

				if (u >= 0)
					up = nodeList[i][u];
				if (d < heightOfBoard)
					down = nodeList[i][d];
				if (l >= 0)
					left = nodeList[l][j];
				if (r < widthOfBoard)
					right = nodeList[r][j];

				nodeList[i][j].setDirections(left, right, up, down);
			}
		}
	}

	/**
	 * Creates nodes within the maze.
	 * 
	 * @param ref
	 * 
	 */
	public void createNodes(boolean ref) {
		for (int i = 0; i < nodeList.length; i++) {
			for (int j = 0; j < nodeList[i].length; j++) {
				if (!ref) {
					nodeList[i][j] = new Node(i, j).setX(15 + i * 35).setY(15 + j * 35);
				}
				nodeList[i][j].clearNode();
			}
		}
	}

	/**
	 * Generates random nodes, hence generating a random maze.
	 */
	public void createNodesRandomly() {
		int random;

		for (int i = 0; i < nodeList.length; i++) {
			for (int j = 0; j < nodeList[i].length; j++) {
				random = rand.nextInt(3);
				if (random == 0 || random == 1) {
					nodeList[i][j].setColor(Color.LIGHT_GRAY);
				} else {
					nodeList[i][j].setColor(Color.BLACK);

				}
			}
		}

	}

	/**
	 * Clears the search results meaning it will clear the maze in order to use
	 * another algorithm.
	 */
	public void clearSearchResults() {
		for (int i = 0; i < nodeList.length; i++) {
			for (int j = 0; j < nodeList[i].length; j++) {
				if (nodeList[i][j].isSearched()) {
					nodeList[i][j].clearNode();
				}
			}
		}
		if (isMazeValid()) {
			target.setColor(Color.RED);
			start.setColor(Color.GREEN);
		}
	}

	/**
	 * 
	 * @param g
	 */
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for (int i = 0; i < nodeList.length; i++) {
			for (int j = 0; j < nodeList[i].length; j++) {
				nodeList[i][j].render(g);
			}
		}
	}

	public void start() {
		// check
		new Thread(this).start();
	}

	/**
	 * Checks if the mouse is clicked.
	 * 
	 */
	public void mousePressed(MouseEvent e) {
		Node clickedNode = getNodeAt(e.getX(), e.getY());
		if (clickedNode == null)
			return;

		if (clickedNode.isWall()) {
			clickedNode.clearNode();
			return;
		}

		clickedNode.Clicked(e.getButton());

		if (clickedNode.isEnd()) {
			if (target != null) {
				target.clearNode();
			}
			target = clickedNode;
		} else if (clickedNode.isStart()) {

			if (start != null) {
				start.clearNode();
			}
			start = clickedNode;
		}

	}

	/**
	 * 
	 * Returns if the maze is valid using boolean values.
	 * 
	 */
	public boolean isMazeValid() {
		return target == null ? false : true && start == null ? false : true;
	}

	/**
	 * 
	 * Used to return the start of the maze.
	 * 
	 */
	private Node getStart() {
		for (int i = 0; i < nodeList.length; i++) {
			for (int j = 0; j < nodeList[i].length; j++) {
				if (nodeList[i][j].isStart()) {
					return nodeList[i][j];
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * 
	 *          Returns a node at a certain point on the maze.
	 * 
	 */
	public Node getNodeAt(int x, int y) {
		x -= 15;
		x /= 35;
		y -= 15;
		y /= 35;

		System.out.println(x + ":" + y);
		if (x >= 0 && y >= 0 && x < nodeList.length && y < nodeList[x].length) {
			return nodeList[x][y];
		}
		return null;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	/**
	 * 
	 * @param component
	 * @param popup
	 * 
	 *                  Adds a popup window which was used for the instructions
	 *                  portion of the GUI.
	 * 
	 */
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}