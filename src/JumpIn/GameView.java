package JumpIn;
//package viewformilestone2;

//package viewformilestone2;

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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameView extends JFrame {

	private static final long serialVersionUID = 1L;
	private Container container;
	JButton board[][];// This will be a board of squares

	private int size; // The size of the board

	Image piece, whiteRabbit, yellowRabbit, greyRabbit, mushroom, foxface, foxtail, hole;

	/**
	 * this will be used twice firstly to add holes and secondly to change the
	 * background of buttons
	 */
	private int dimensions[] = { 0, 0, 0, 4, 4, 0, 4, 4, 2, 2 };

	private Game game;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItemHelp, menuItemQuit, menuItemReset;
	private Command command;

	private HashMap<String, Image> images;

	public GameView(Game model) {
		super();

		this.game = model;
		images = new HashMap<>();

		size = model.getBoard().SIZE;

		container = new Container();
		container.setLayout(new GridLayout(size, size));

		add(container);

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

	public int getBoardSize() {
		return this.size;

	}

	/**
	 * This method checks the location of all the animals and places an image of
	 * them on the board .
	 */
	public void putIconsOnBoard() {
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

		HashMap<String, Piece> animals = game.getAnimalsOnBoard();
		for (String s : animals.keySet()) {
			Piece animal = animals.get(s);
			int animalRow = animal.getYPos();
			int animalCol = animal.getXPos();
			if (animal instanceof Rabbit) {
				Rabbit r = (Rabbit) animal;
				if (r.getColour() == Color.WHITE)
					piece = whiteRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
				if (r.getColour() == Color.GRAY)
					piece = greyRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
				if (r.getColour() == Color.YELLOW)
					piece = yellowRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
			} else if (animal instanceof Fox) {
				Fox f = (Fox) animal;
				if (f.isHead()) {
					piece = foxface.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
				} else {
					piece = foxtail.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
				}
			}

			board[animalRow][animalCol].setIcon(new ImageIcon(piece));
		}
	}

	public void addMenuItems() {
		menuBar = new JMenuBar();
		menu = new JMenu("JumpIn Menu");
		menuBar.add(menu);

		menuItemHelp = new JMenuItem("Help");
		menu.add(menuItemHelp);

		menuItemQuit = new JMenuItem("Quit");
		menu.add(menuItemQuit);

		// TODO: MAKE SURE THIS WORKS. THIS FUNCTIONALITY IS UNTESTED!!
		menuItemReset = new JMenuItem("Reset");
		menu.add(menuItemReset);
		menuItemReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createBoard();
			}

		});

		add(menuBar, BorderLayout.NORTH);
	}

	/*
	 * The board will be created then we are adding the buttons in side it and also
	 * adding background color
	 */
	public void createBoard() {
		board = new JButton[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				JButton button = new JButton();
				board[i][j] = button;
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
	public void instantiateIcons() {
		// we are adding the holes on the board
		hole = new ImageIcon(this.getClass().getResource("/black-hole.png")).getImage();

		// instantiate the mushroom image
		mushroom = new ImageIcon(this.getClass().getResource("/mushroom.png")).getImage();

		// instantiate the white rabbit image
		whiteRabbit = new ImageIcon(this.getClass().getResource("/whiteRabbit.png")).getImage();

		// instantiate the grey rabbit image
		greyRabbit = new ImageIcon(this.getClass().getResource("/rabbit.png")).getImage();

		// instantiate the yellow rabbit image
		yellowRabbit = new ImageIcon(this.getClass().getResource("/yellowandBlack.png")).getImage();

		// instantiate the fox face image
		foxface = new ImageIcon(this.getClass().getResource("/foxface.png")).getImage();

		// instantiate the fox tail image
		foxtail = new ImageIcon(this.getClass().getResource("/foxtail.png")).getImage();
	}

	/**
	 * Remove all current icons and then Update the view
	 * 
	 * @param x
	 * @param y
	 * @param name
	 */
	public void update() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j].setIcon(null);
			}
		}
		putIconsOnBoard();
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

	public void addHelpListener(ActionListener a) {
		menuItemHelp.addActionListener(a);
	}

	public void addQuitListener(ActionListener a) {
		menuItemQuit.addActionListener(a);
	}

}
