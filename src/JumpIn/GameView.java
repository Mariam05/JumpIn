package JumpIn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	private JPanel startPage, levelsPage;
	JButton board[][], levels[][], newGameBtn, loadGameBtn, levelBtn, buildLvlBtn;// This will be a board of squares

	private int size; // The size of the board

	private Image piece, whiteRabbit, yellowRabbit, greyRabbit, mushroom, foxface, foxtail, hole;

	/**
	 * this will be used twice firstly to add holes and secondly to change the
	 * background of buttons
	 */
	private int dimensions[] = { 0, 0, 0, 4, 4, 0, 4, 4, 2, 2 };

	private Game game;
	private JMenuBar menuBar, defaultLvl, customLvl;
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

		// Creating start page
		createStartPage();		
		
		container = new Container();
		container.setLayout(new GridLayout(size, size));

		setTitle("JumpIn Game");
		setSize(700, 700);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

	}
	
	public void goToGame() {
		remove(levelsPage);
		displayGame();
	}
	
	/**
	 * 
	 */
	public void goToLevelPage() {
		remove(startPage);
		createLevelsPage();
	}

	/**
	 * Setting up the game board
	 */
	private void displayGame() {
		add(container); // add the container to the jframe
		addMenuItems();
		createBoard();
		instantiateIcons();
		putIconsOnBoard();
	}
	
	/**
	 * Creating start page
	 */
	private void createStartPage() {
		startPage = new JPanel();
		startPage.setLayout(null);
		startPage.setBackground(new Color(0, 204, 0));
		add(startPage);
		
		newGameBtn = new JButton("Start a New Game");
		newGameBtn.setBounds(250, 350, 210, 50);
		newGameBtn.setFont(new Font ("Monospaced", Font.BOLD, 20));
		newGameBtn.setBackground(Color.WHITE);
		newGameBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		newGameBtn.setForeground(Color.BLUE);
		
		loadGameBtn = new JButton("Load Previous Game");
		loadGameBtn.setBounds(230, 450, 250, 50);
		loadGameBtn.setFont(new Font ("Monospaced", Font.BOLD, 20));
		loadGameBtn.setBackground(Color.WHITE);
		loadGameBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		loadGameBtn.setForeground(Color.BLUE);
		
		JLabel title = new JLabel("JUMP IN");
		title.setFont(new Font ("Monospaced", Font.BOLD, 125));
		title.setBounds(70, 100, 900, 100);
		title.setForeground(Color.WHITE);
		
		startPage.add(title);
		startPage.add(newGameBtn);
		startPage.add(loadGameBtn);
		
		loadGameBtn.addActionListener(e -> {displayMessage("load saved game");});
		
	}
	
	/** 
	 * creating levels page
	 * 
	 */
	private void createLevelsPage() {
		// JPanel to store all the levels options and labels
		levelsPage = new JPanel();
		levelsPage.setLayout(null);
		levelsPage.setBackground(new Color(0, 204, 0));
		add(levelsPage);
		
		// Header for the default levels
		defaultLvl = new JMenuBar();
		defaultLvl.add(new JLabel("  Default Levels"));
		defaultLvl.setBounds(0, 0, 700, 30);
		levelsPage.add(defaultLvl);
		
		// Creating a grid to store all the default levels
		JPanel defaultLvlSection = new JPanel();
		defaultLvlSection.setLayout(new GridLayout(4,5));
		defaultLvlSection.setBounds(0, 30, 700, 300);
		levelsPage.add(defaultLvlSection);
		
		// Filling the grid with buttons to take user to corresponding game
		levels = new JButton[5][4];
		int count = 0;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 4; j++) {
				count++;
				levels[i][j] = new JButton("Level " + count);
				levels[i][j].setFont(new Font ("Monospaced", Font.BOLD, 20));
				levels[i][j].setBackground(Color.WHITE);
				levels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				levels[i][j].setForeground(Color.BLUE);
				levels[i][j].setName("" + count); // To compare to "name" in json
				defaultLvlSection.add(levels[i][j]);
				
			}
		}
		
		// Header for custom levels section
		JMenuBar customLvl = new JMenuBar();
		customLvl.add(new JLabel("  Custom Levels   "));
		customLvl.setBounds(0, 330, 700, 30);
		levelsPage.add(customLvl);
		
		// Adding buld/customize level button
		buildLvlBtn = new JButton("Build A Level");
		customLvl.add(buildLvlBtn);
		// TODO add back/return to main menu button
		
		setVisible(true);
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

		// Add the animals
		HashMap<String, Piece> pieces = game.getAnimalsOnBoard(); // get the animals from the model
		for (String s : pieces.keySet()) { // for each animal
			Piece p = pieces.get(s);
			int pieceRow = p.getYPos();
			int pieceCol = p.getXPos();
			if (p instanceof Rabbit) { // if it's a rabbit, associate it with the appropriate rabbit image
				Rabbit r = (Rabbit) p;
				if (r.getColour() == Color.WHITE)
					piece = whiteRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
				if (r.getColour() == Color.GRAY)
					piece = greyRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
				if (r.getColour() == Color.YELLOW)
					piece = yellowRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
				board[pieceRow][pieceCol].setIcon(new ImageIcon(piece)); // add the image to the animal's location
				
			} else if (p instanceof Fox) { // if it's a fox, associate it with the appropriate fox image depending
												// on whether it's a head/tail
				Fox f = (Fox) p;
				
				if (f.isHead()) {
					Fox ft = f.getAssociatedPart();
					
					piece = foxface.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
					board[pieceRow][pieceCol].setIcon(new ImageIcon(piece)); // add the image to the animal's location

					piece = foxtail.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
					board[ft.getYPos()][ft.getXPos()].setIcon(new ImageIcon(piece)); // add the image to the animal's
																					 // location
				}
			} else if (p instanceof Mushroom) {// Add the mushrooms
				piece = mushroom.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
				board[pieceRow][pieceCol].setIcon(new ImageIcon(piece));
				
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
		menuItemUndo.addActionListener(e -> {undo();});
		menuBar.add(menuItemUndo);

		// Add redo button
		menuItemRedo = new JMenuItem("Redo");
		menuItemRedo.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuItemRedo.addActionListener(e -> {redo();});
		menuBar.add(menuItemRedo);

		// Add help button
		menuItemHelp = new JMenuItem("Help");
		menuItemHelp.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuItemHelp.addActionListener(e -> {displayMessage(game.printGameInstructions());});
		menuBar.add(menuItemHelp);

		// TODO fix it so that it only resets the level and not the entire game
		// potentially do this via serialization i.e. store initial state and retrieve it when necessary
		
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

		//Add hint button
		menuItemHint = new JMenuItem("Hint", KeyEvent.VK_H);
		menuItemHint.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuItemHint.addActionListener(e -> {displayHint((new Solver(game)).getHint());});
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
		hole = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/black-hole.png")).getImage();

		// instantiate the mushroom image
		mushroom = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/mushroom.png")).getImage();

		// instantiate the white rabbit image
		whiteRabbit = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/whiteRabbit.png")).getImage();

		// instantiate the grey rabbit image
		greyRabbit = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/rabbit.png")).getImage();

		// instantiate the yellow rabbit image
		yellowRabbit = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/yellowandBlack.png")).getImage();

		// instantiate the fox face image
		foxface = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/foxface.png")).getImage();

		// instantiate the fox tail image 
		foxtail = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/foxtail.png")).getImage();
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
	 * Undoes current move and stores command to revert it in redo
	 * 
	 * @author Nazifa Tanzim
	 */
	private void undo() {
		if(!game.undo()) {
			displayMessage("No more undo's left");
		} else {
			update(); // Update the view
		}
	}

	/**
	 * Allows user to redo a move
	 * 
	 * @author Nazifa Tanzim
	 */
	private void redo() {
		if(!game.redo()) {
			displayMessage("No more redo's left");
		} else{
			update(); // Update the view
		}
		
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
	 * Add listener to each level option
	 * 
	 * @param a
	 */
	public void addLevelListener(ActionListener a) {
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 4; j++) {
				levels[i][j].addActionListener(a);
			}
		}
	}
	
	/**
	 * Listens for when user wants to start a new game
	 * 
	 * @param a
	 */
	public void addNewGameListener(ActionListener a) {
		newGameBtn.addActionListener(a);
	}

}
