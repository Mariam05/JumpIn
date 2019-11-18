package JumpIn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * This is the view class for the GUI. It has a model (Game object) and updates
 * whenever the model updates
 * 
 * @author Taher Shabaan, Hassan Hassan, Mariam Almalki, Nazifa Tanzim
 *
 */
public class GameView extends JFrame {

	private static final long serialVersionUID = 1L;
	private Container container;
	JButton board[][];// This will be a board of squares

	private int size; // The size of the board

	private Image piece, whiteRabbit, yellowRabbit, greyRabbit, mushroom, foxface, foxtail, hole;

	/**
	 * this will be used twice firstly to add holes and secondly to change the
	 * background of buttons
	 */
	private int dimensions[] = { 0, 0, 0, 4, 4, 0, 4, 4, 2, 2 };

	private Game game;
	private JMenuBar menuBar;
	// private JMenu menu;
	private JMenuItem menuItemHelp, menuItemQuit, menuItemReset, menuItemUndo, menuItemRedo, menuItemHint;

	/**
	 * Create a new view
	 * 
	 * @param model pass the model to the view
	 */
	public GameView(Game model) {
		super();

		this.game = model;

		size = model.getBoard().SIZE;

		container = new Container();
		container.setLayout(new GridLayout(size, size));

		add(container); // add the container to the jframe

		setTitle("JumpIn Game");
		setSize(700, 700);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		addMenuItems();
		createBoard();
		instantiateIcons();
		putIconsOnBoard();
	}

	/**
	 * Get the size of the view's board
	 * 
	 * @return int of size (in one way bc it's a square)
	 */
	public int getBoardSize() {
		return this.size;

	}

	/**
	 * This method checks the location of all the animals and places an image of
	 * them on the board .
	 */
	private void putIconsOnBoard() {
		// Add the holes
		piece = hole.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
		for (int i = 1; i < dimensions.length; i = i + 2) {
			board[dimensions[i - 1]][dimensions[i]].setIcon(new ImageIcon(piece));
			board[dimensions[i - 1]][dimensions[i]].setBackground(new Color(153, 0, 0));
		}

		// Add the mushrooms
		piece = mushroom.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
		board[1][3].setIcon(new ImageIcon(piece));
		board[4][2].setIcon(new ImageIcon(piece));

		// Add the animals
		HashMap<String, Piece> animals = game.getAnimalsOnBoard(); // get the animals from the model
		for (String s : animals.keySet()) { // for each animal
			Piece animal = animals.get(s);
			int animalRow = animal.getYPos();
			int animalCol = animal.getXPos();
			if (animal instanceof Rabbit) { // if it's a rabbit, associate it with the appropriate rabbit image
				Rabbit r = (Rabbit) animal;
				if (r.getColour() == Color.WHITE)
					piece = whiteRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
				if (r.getColour() == Color.GRAY)
					piece = greyRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
				if (r.getColour() == Color.YELLOW)
					piece = yellowRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
				board[animalRow][animalCol].setIcon(new ImageIcon(piece)); // add the image to the animal's location
				
			} else if (animal instanceof Fox) { // if it's a fox, associate it with the appropriate fox image depending
												// on whether it's a head/tail
				Fox f = (Fox) animal;
				
				if (f.isHead()) {
					Fox ft = f.getAssociatedPart();
					
					piece = foxface.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
					board[animalRow][animalCol].setIcon(new ImageIcon(piece)); // add the image to the animal's location

					piece = foxtail.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
					board[ft.getYPos()][ft.getXPos()].setIcon(new ImageIcon(piece)); // add the image to the animal's
																					 // location
				}
			}

		}
	}

	/**
	 * Create a menu that will allow the user to choose if they want help or quit.
	 */
	private void addMenuItems() {
		// Create menu bar
		menuBar = new JMenuBar();

		// Add undo button
		menuItemUndo = new JMenuItem("Undo");
		menuItemUndo.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuBar.add(menuItemUndo);

		// Add redo button
		menuItemRedo = new JMenuItem("Redo");
		menuItemRedo.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuBar.add(menuItemRedo);

		// Add help button
		menuItemHelp = new JMenuItem("Help");
		menuItemHelp.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuItemHelp.addActionListener(e -> {displayMessage(game.printGameInstructions());});
		menuBar.add(menuItemHelp);

		// Add reset button
		menuItemReset = new JMenuItem("Reset");
		menuItemReset.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuItemReset.addActionListener(e -> {dispose(); Main.main(null);});
		menuBar.add(menuItemReset);

		// Add quit button
		menuItemQuit = new JMenuItem("Quit");
		menuItemQuit.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuItemQuit.addActionListener(e -> {displayMessage(game.quitMessage()); dispose();});
		menuBar.add(menuItemQuit);

		menuItemHint = new JMenuItem("Hint", KeyEvent.VK_H);
		menuItemHint.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuItemHint.addActionListener(e -> {(new Solver(game)).getHint();});
		menuBar.add(menuItemHint);

		add(menuBar, BorderLayout.NORTH);
	}

	/*
	 * The board will be created then we are adding the buttons in side it and also
	 * adding background color
	 */
	private void createBoard() {
		board = new JButton[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				JButton button = new JButton();
				board[i][j] = button;
				button.setIcon(null);
				button.setPreferredSize(new Dimension(200, 200));
				button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
				button.setBackground(new Color(0, 204, 0));// the backGround of the buttons
				button.setOpaque(true);
				container.add(button);
			}
		}
	}

	/*
	 * All the animals, mushrooms and holes will be added by using this method
	 */
	private void instantiateIcons() {
		// we are adding the holes on the board
		hole = new ImageIcon(this.getClass().getResource("/JumpIn/black-hole.png")).getImage();

		// instantiate the mushroom image
		mushroom = new ImageIcon(this.getClass().getResource("/JumpIn/mushroom.png")).getImage();

		// instantiate the white rabbit image
		whiteRabbit = new ImageIcon(this.getClass().getResource("/JumpIn/whiteRabbit.png")).getImage();

		// instantiate the grey rabbit image
		greyRabbit = new ImageIcon(this.getClass().getResource("/JumpIn/rabbit.png")).getImage();

		// instantiate the yellow rabbit image
		yellowRabbit = new ImageIcon(this.getClass().getResource("/JumpIn/yellowandBlack.png")).getImage();

		// instantiate the fox face image
		foxface = new ImageIcon(this.getClass().getResource("/JumpIn/foxface.png")).getImage();

		// instantiate the fox tail image
		foxtail = new ImageIcon(this.getClass().getResource("/JumpIn/foxtail.png")).getImage();
	}

	/**
	 * Remove all current icons and then Update the view
	 * 
	 */
	public void update() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j].setIcon(null);
				board[i][j].setBackground(new Color(0, 204, 0));
			}
		}
		putIconsOnBoard();
	}

	/**
	 * Resets the game and then updates the board accordingly
	 * @param game
	 */
	public void resetView(Game game) {
		this.game = game;
		update();
	}
	
	/**
	 * Displays the hint on the board 
	 * 
	 * @param c
	 */
	public void displayHint(Command c) {
		// Highlights the piece to be moved
		board[game.getPieceFromCommand(c).getYPos()][game.getPieceFromCommand(c).getXPos()]
				.setBackground(Color.BLUE);
		
		// Highlights piece destination
		board[c.getY()][c.getX()].setBackground(Color.BLUE);
	}

	/**
	 * Will display a message to the user. Expect message to be on of the following:
	 * (a) invalid move (b) quit goodbye message (c) help instructions message
	 * 
	 * @param message
	 */
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	/**
	 * If undo button is pressed
	 * 
	 * @param a
	 * @author Nazifa Tanzim
	 */
	public void addUndoListener(ActionListener a) {
		menuItemUndo.addActionListener(a);
	}

	/**
	 * if redo button is pressed
	 * 
	 * @param a
	 * @author Nazifa Tanzim
	 */
	public void addRedoListener(ActionListener a) {
		menuItemRedo.addActionListener(a);
	}

	public void addHintListener(ActionListener a) {
		menuItemHint.addActionListener(a);
	}

	/**
	 * if reset button is pressed
	 * 
	 * @param a
	 * @author Nazifa Tanzim
	 */
	public void addResetListener(ActionListener a) {
		menuItemReset.addActionListener(a);
	}
}
