package JumpIn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This is the view class for the GUI. It has a model (Game object) and updates
 * whenever the model updates
 * 
 * @author Taher Shabaan, Hassan Hassan, Mariam Almalki, Nazifa Tanzim
 *
 */
public class GameView extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel container, gamePanel, currPanel;

	JButton board[][], startBtn; // This will be a board of squares

	public int size; // The size of the board

	Image piece, whiteRabbit, yellowRabbit, greyRabbit, mushroom, foxface, foxtail, hole, grassPic;

	/**
	 * this will be used twice firstly to add holes and secondly to change the
	 * background of buttons
	 */
	private int dimensions[] = { 0, 0, 0, 4, 4, 0, 4, 4, 2, 2 };

	public Game game;
	private JMenuBar menuBar;
	private JMenuItem menuItemHelp, menuItemQuit, menuItemReset, menuItemUndo, menuItemRedo, menuItemHint, menuItemBack,
			menuItemSave;

	private Stack<JPanel> previousPanels;

	/**
	 * Create a new view
	 * 
	 * @param model pass the model to the view
	 */
	public GameView(Game model) {
		super();
		model.addGameListener(this);

		this.setLayout(new BorderLayout());

		this.game = model;
		size = game.getBoard().SIZE;

		previousPanels = new Stack<>();

		instantiateIcons();

		// Creating start page
		new StartPagePanel(this);

		setTitle("JumpIn Game");
		setSize(700, 700);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

	}

	/*
	 * Switches to the previous game from start page
	 */
	public void goToLoadedGame(Game game) {
		currPanel.setVisible(false);
		remove(currPanel);
		this.game = game;
	//	this.game.addGameListener(this);
		displayGame();
	}

	@Override
	public Component add(Component c) {

		((JPanel) c).setVisible(true);
		super.add((JPanel) c);
		if (c instanceof JPanel) {
			previousPanels.push((JPanel) c);
			currPanel = (JPanel) c;
		}
		return c;

	}

	/**
	 * Setting up the game board
	 */
	public void displayGame() {
		container = new JPanel();
		container.setLayout(new GridLayout(size, size));

		gamePanel = new JPanel(new BorderLayout());
		gamePanel.add(container);

		addMenuItems();

		createBoard();
		putIconsOnBoard();

		gamePanel.setVisible(true);
		gamePanel.setName("GamePanel");
		add(gamePanel); // add the container to the jframe

	}

	/**
	 * Get the size of the view's board
	 * 
	 * @return int of size (in one way bc it's a square)
	 */
	public int getBoardSize() {
		return this.size;

	}

	public void goBack() {

		if (previousPanels.pop() != null) {

			currPanel.setVisible(false);
			remove(currPanel);

			JPanel newPanel = previousPanels.pop();
			add(newPanel);
		}
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

		// Add the pieces
		List<Piece> pieceList = game.getBoard().getPieceObjects();
		for (Piece p : pieceList) {
			int pieceRow = p.getYPos();
			int pieceCol = p.getXPos();
			if (p instanceof Rabbit) { // if it's a rabbit, associate it with the appropriate rabbit image
				Rabbit r = (Rabbit) p;
				if (r.toString().equals("RA1"))
					piece = whiteRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
				if (r.toString().equals("RA2"))
					piece = greyRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
				if (r.toString().equals("RA3"))
					piece = yellowRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
				board[pieceRow][pieceCol].setIcon(new ImageIcon(piece));

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
		menuItemUndo.addActionListener(e -> {
			undo();
		});
		menuItemUndo.setAccelerator(KeyStroke.getKeyStroke('u')); // can activae undo by pressing u
		menuBar.add(menuItemUndo);

		// Add redo button
		menuItemRedo = new JMenuItem("Redo");
		menuItemRedo.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuItemRedo.addActionListener(e -> {
			redo();
		});
		menuItemRedo.setAccelerator(KeyStroke.getKeyStroke('r')); // can activate redo by pressing r
		menuBar.add(menuItemRedo);

		// Add help button
		menuItemHelp = new JMenuItem("Help");
		menuItemHelp.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuItemHelp.addActionListener(e -> {
			displayMessage(game.printGameInstructions());
		});
		menuBar.add(menuItemHelp);

		// TODO fix it so that it only resets the level and not the entire game
		// potentially do this via serialization i.e. store initial state and retrieve
		// it when necessary

		// Add reset button
		menuItemReset = new JMenuItem("Reset");
		menuItemReset.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuItemReset.addActionListener(e -> {
			dispose();
			Main.main(null);
		});
		menuBar.add(menuItemReset);

		// Add quit button
		menuItemQuit = new JMenuItem("Quit");
		menuItemQuit.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuItemQuit.addActionListener(e -> {
			displayMessage(game.quitMessage());
			dispose();
		});
		menuItemQuit.setAccelerator(KeyStroke.getKeyStroke('q')); // can activate quit by pressing q
		menuBar.add(menuItemQuit);

		// Add hint button
		menuItemHint = new JMenuItem("Hint", KeyEvent.VK_H);
		menuItemHint.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuItemHint.addActionListener(e -> {
			displayHint((new Solver(game.getBoard())).getHint());
		});
		menuItemHint.setAccelerator(KeyStroke.getKeyStroke('h')); // can activate hint by pressing h
		menuBar.add(menuItemHint);

		// Add back button
		menuItemBack = new JMenuItem("Back");
		menuItemBack.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuItemBack.addActionListener(e -> menuItemBackPressed());
		menuBar.add(menuItemBack);

		// Add save BUTTON
		menuItemSave = new JMenuItem("Save");
		menuItemSave.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		menuBar.add(menuItemSave);
		menuItemSave.addActionListener(e -> {
			displayMessage("Saved");
			game.saveGame();
		});

		gamePanel.add(menuBar, BorderLayout.NORTH);
	}

	private void menuItemBackPressed() {
		int i = JOptionPane.showConfirmDialog(this,
				"Would you like to save before leaving? Warning: this will override your previous save");
		if (i == 2)
			return; // cancel, don't do anything
		if (i == 0) { // used said yes
			// game.saveGame();
			displayMessage("Saved");
		}
		goBack();
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
				button.addActionListener(new GameController(i, j, game));
				button.setOpaque(true);
				container.add(button);
			}
		}
	}

	/**
	 * All the animals, mushrooms and holes will be added by using this method
	 */
	public void instantiateIcons() {
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

		grassPic = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/grass.png")).getImage();
	}

	/**
	 * Remove all current icons and then Update the view
	 * 
	 */
	public void update(Game g, boolean won) {
		this.game = g;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j].setIcon(null);
				board[i][j].setBackground(new Color(0, 204, 0));
			}
		}
		putIconsOnBoard();

		if (won) {
			int i = JOptionPane.showConfirmDialog(this,
					"CONGRATS! You solved the puzzle!" + " Would you like to go back?");
			if (i == 0) { // user said yes
				goBack();
			}
		}
	}

	/**
	 * Displays the hint on the board
	 * 
	 * @param c
	 */
	public void displayHint(Command c) {
		// Highlights the piece to be moved
		board[game.getPieceFromCommand(c).getYPos()][game.getPieceFromCommand(c).getXPos()].setBackground(Color.BLUE);

		// Highlights piece destination
		board[c.getY()][c.getX()].setBackground(Color.BLUE);
	}

	/**
	 * Undoes current move and stores command to revert it in redo
	 * 
	 * @author Nazifa Tanzim
	 */
	private void undo() {
		if (!game.undo()) {
			displayMessage("No more undo's left");
		} else {
			update(game, false); // Update the view
		}
	}

	/**
	 * Allows user to redo a move
	 * 
	 * @author Nazifa Tanzim
	 */
	private void redo() {
		if (!game.redo()) {
			displayMessage("No more redo's left");
		} else {
			update(game, false); // Update the view
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}