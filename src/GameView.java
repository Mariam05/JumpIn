//package viewformilestone2;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container container;
	private JButton board[][];// This will be a board of squares
	private final int SIZE = 5;// This is the size of the board 5 X 5
	Image piece,whiteRabbit,yellowRabbit,greyRabbit,mushroom,fox1,fox2,hole;
	private int dimensions[] = {0,0,0,4,4,0,4,4,2,2};// this will be used twice firstly to add holes and secondly
	// to change the background of buttons
	
	public GameView() {
		super();
		container = getContentPane();
		container.setLayout(new GridLayout(SIZE,SIZE));
		setTitle("JumpIn Game");
		setSize(700,700);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	    createBoard();
	    addPiecesOnBoard();
	}
	/*
	 * The board will be created then we are adding the buttons in side it and
	 * also adding background color
	 */
	public void createBoard() {
		board = new JButton[SIZE][SIZE];
		for(int i=0; i < SIZE; i++) {
			for(int j=0; j < SIZE; j++) {
				board[i][j] = new JButton();
				board[i][j].setPreferredSize(new Dimension(200, 200));
				board[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
				board[i][j].setBackground(new Color(0,204,0));// the backGround of the buttons
				board[i][j].setOpaque(true);
                container.add(board[i][j]);
			}
		}
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
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                 new GameView();
            }
        });
	}
}
