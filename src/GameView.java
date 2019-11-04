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

public class GameView extends JFrame{
	
	Game gameModel = new Game();
	private static final long serialVersionUID = 1L;
	private Container container;
	private JButton board[][];// This will be a board of squares
	private final int SIZE = 5;// This is the size of the board 5 X 5
	Image piece,whiteRabbit,yellowRabbit,greyRabbit,mushroom,fox1,fox2,hole;
	private int dimensions[] = {0,0,0,4,4,0,4,4,2,2};// this will be used twice firstly to add holes and secondly
	// to change the background of buttons
	private Game game;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItemHelp;
	private JMenuItem menuItemQuit;
	private Command command;
	
	
	
	public GameView(Game model) {
		super();
		
		this.game = model;
		
		container = new Container();
		container.setLayout(new GridLayout(SIZE,SIZE));
		
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
	
	
	public void addMenuItems() {
		menuBar = new JMenuBar();
		menu = new JMenu("JumpIN");
		menu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu);
		
		menuItemHelp = new JMenuItem("Help");
		menu.add(menuItemHelp);
		//This can also be implemented by adding an action listener on the list that will remove the one that is selected
//		menuItem.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				command = new Command("help", null, null);
//				game.processCommand(command);
//			}
//			
//		});
		
		menuItemQuit = new JMenuItem("Quit");
		menu.add(menuItemQuit);
		//This can also be implemented by adding an action listener on the list that will remove the one that is selected
		menuItemQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				command = new Command("quit", null, null);
				game.processCommand(command);
			}
			
		});
		
		add(menuBar, BorderLayout.NORTH);
	}
	/*
	 * The board will be created then we are adding the buttons in side it and
	 * also adding background color
	 */
	public void createBoard() {
		board = new JButton[SIZE][SIZE];
		for(int i=0; i < SIZE; i++) {
			for(int j=0; j < SIZE; j++) {
				JButton button = new JButton();
				board[i][j] = button;
				button.setPreferredSize(new Dimension(200, 200));
				button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
				button.setBackground(new Color(0,204,0));// the backGround of the buttons
				button.setOpaque(true);
                container.add(button);
                button.addActionListener(new ButtonListener(i,j));
			}
		}
		//gameModel.add
		// We are changing the holes background color to make it darker green
		for(int i=1; i < dimensions.length;i++) {
	    	 board[dimensions[i-1]][dimensions[i]].setBackground(new Color(0, 102, 0));
	    }
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
		System.out.println("Added listener");
		menuItemHelp.addActionListener(a);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                 new GameView(game);
            }
        });
	}
}
