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

public class GameView extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private Container container;
	JButton board[][];// This will be a board of squares
	
	private int size; //The size of the board
	
	Image piece,whiteRabbit,yellowRabbit,greyRabbit,mushroom,fox1,fox2,hole;
	private int dimensions[] = {0,0,0,4,4,0,4,4,2,2};// this will be used twice firstly to add holes and secondly
	// to change the background of buttons
	
	private Game game;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItemHelp,menuItemQuit,menuItemReset ;
	private Command command;
	
	private HashMap<String, Image> images;
	
	
	
	
	public GameView(Game model) {
		super();
		
		this.game = model;
		images = new HashMap<>();
		
		size = model.getBoard().SIZE;
		
		container = new Container();
		container.setLayout(new GridLayout(size,size));
		
		add(container);
		
		setTitle("JumpIn Game");
		setSize(700,700);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		addMenuItems();
	    createBoard();
	    addPiecesOnBoard();
	}
	
	public int getBoardSize() {
		return this.size;
		
	}
	
	public void addMenuItems() {
		menuBar = new JMenuBar();
		menu = new JMenu("JumpIN");
		menuBar.add(menu);
		
		menuItemHelp = new JMenuItem("Help");
		menu.add(menuItemHelp);
		
		menuItemQuit = new JMenuItem("Quit");
		menu.add(menuItemQuit);
		
		//TODO: MAKE SURE THIS WORKS. THIS FUNCTIONALITY IS UNTESTED!!
		menuItemReset = new JMenuItem("Reset");
		menu.add(menuItemReset);
		menuItemReset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createBoard();	
			}
			
		});
		
		add(menuBar, BorderLayout.NORTH);
	}
	
	/*
	 * The board will be created then we are adding the buttons in side it and
	 * also adding background color
	 */
	public void createBoard() {
		board = new JButton[size][size];
		for(int i=0; i < size; i++) {
			for(int j=0; j < size; j++) {
				JButton button = new JButton();
				board[i][j] = button;
				button.setPreferredSize(new Dimension(200, 200));
				button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
				button.setBackground(new Color(0,204,0));// the backGround of the buttons
				button.setOpaque(true);
                container.add(button);
                //button.addActionListener(new ButtonListener(i,j));
			}
		}
		
		// We are changing the holes background color to make it darker green
		for(int i=1; i < dimensions.length;i++) {
	    	 board[dimensions[i-1]][dimensions[i]].setBackground(new Color(0, 102, 0));
	    }
	}
	
	/**
	 * Give the animal buttons names so that the names can be passed to a command
	 */
	public void setInitialButtonNames() {
		board[4][1].setName("R1");
		board[0][3].setName("R2");
		board[2][4].setName("R3");
		board[3][3].setName("F1");
		board[1][1].setName("F2");
		board[3][4].setName("F1");
		board[0][1].setName("F2");
	}
	
	/*
	 * All the animals, mushrooms and holes will be added by using this method
	 */
	public void addPiecesOnBoard() {
		//we are adding the holes on the board
		hole = new ImageIcon(this.getClass().getResource("/black-hole.png")).getImage();
	    piece = hole.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
	    for(int i=1; i < dimensions.length;i++) {
	    	 board[dimensions[i-1]][dimensions[i]].setIcon(new ImageIcon(piece));
	    }
	    //we are adding the mushrooms
	    mushroom = new ImageIcon(this.getClass().getResource("/mushroom.png")).getImage();
	    piece = mushroom.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
	    board[1][3].setIcon(new ImageIcon(piece));
	    board[4][2].setIcon(new ImageIcon(piece));
	    
	    //we are adding the white-rabbits
	    whiteRabbit = new ImageIcon(this.getClass().getResource("/whiteRabbit.png")).getImage();
	    piece = whiteRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
	    board[4][1].setIcon(new ImageIcon(piece));
	    //we are adding the greyRabbit
	    whiteRabbit = new ImageIcon(this.getClass().getResource("/rabbit.png")).getImage();
	    piece = whiteRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
	    board[0][3].setIcon(new ImageIcon(piece));
	    //we are adding the black and yellowrabbit
	    fox1 = new ImageIcon(this.getClass().getResource("/yellowandBlack.png")).getImage();
	    piece = fox1.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
	    board[2][4].setIcon(new ImageIcon(piece));
	    //We are adding the foxes heads
	    fox1 = new ImageIcon(this.getClass().getResource("/fox.png")).getImage();
	    piece = fox1.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
	    board[3][3].setIcon(new ImageIcon(piece));
	   
	    fox2 = new ImageIcon(this.getClass().getResource("/foxface.png")).getImage();
	    piece = fox2.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
	    board[1][1].setIcon(new ImageIcon(piece));
	    
	    //we are adding the foxes tails
	    fox1 = new ImageIcon(this.getClass().getResource("/foxtail.png")).getImage();
	    piece = fox1.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
	    board[3][4].setIcon(new ImageIcon(piece));
	    board[0][1].setIcon(new ImageIcon(piece));
	}
	
	/**
	 * Update the view
	 * @param x
	 * @param y
	 * @param name
	 */
	public void update(int startCoordinate, int endCoordinate, String name) {
		int currX = startCoordinate / 10;
		int currY = startCoordinate % 10;
		int newX = endCoordinate / 10;
		int newY = endCoordinate % 10;
		
		// Adding piece to new button location
		board[newX][newY].setText(name); // Setting new button name
		board[newX][newY].setIcon(board[currX][currY].getIcon()); // Setting icon to new location
		// Removing piece from previous button location
		board[currX][currY].setText("empty"); // Clearing name
		board[currX][currY].setIcon(null); // Clearing icon
	}
	
	
	/**
	 * Will display a message to the user. 
	 * Expect message to be on of the following: 
	 * (a) invalid move
	 * (b) quit goodbye message
	 * (c) help instructions message
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


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
