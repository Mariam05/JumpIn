package contoller;


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

	
	private static final long serialVersionUID = 1L;
	private Container container;
	private JButton board[][];// This will be a board of squares
	private final int SIZE = 5;// This is the size of the board 5 X 5
	Image piece,whiteRabbit,yellowRabbit,greyRabbit,mushroom,fox1,fox2,hole;
	
	private Game model;
	
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
	
	public void createBoard() {
		board = new JButton[SIZE][SIZE];
		for(int i=0; i < SIZE; i++) {
			for(int j=0; j < SIZE; j++) {
				board[i][j] = new JButton("Empty");
				
				board[i][j].setPreferredSize(new Dimension(200, 200));
				//board[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
				board[i][j].setBackground(new Color(0,204,0));// the backGround of the buttons
				board[i][j].setForeground(new Color(0,204,0));
				board[i][j].setOpaque(true);
                container.add(board[i][j]);
                board[i][j].addActionListener(new Listener((i*10)+j));
			}
		}
		// We are changing the holes background color to make it darker
		board[0][0].setBackground(new Color(0, 102, 0));
		board[0][4].setBackground(new Color(0, 102, 0));
		board[4][0].setBackground(new Color(0, 102, 0));
		board[4][4].setBackground(new Color(0, 102, 0));
		board[2][2].setBackground(new Color(0, 102, 0));
		
		board[0][0].setForeground(new Color(0, 102, 0));
		board[0][4].setForeground(new Color(0, 102, 0));
		board[4][0].setForeground(new Color(0, 102, 0));
		board[4][4].setForeground(new Color(0, 102, 0));
		board[2][2].setForeground(new Color(0, 102, 0));
		board[0][0].setText("Hole");
		board[0][4].setText("Hole");
		board[4][0].setText("Hole");
		board[4][4].setText("Hole");
		board[2][2].setText("Hole");
		board[1][3].setText("mushroom");
		board[4][2].setText("mushroom");
		board[4][1].setText("R1");
		board[0][3].setText("R2");
		board[2][4].setText("R3");
		board[3][3].setText("F1");
		board[1][1].setText("F2");
		board[3][4].setText("F1");
		board[0][1].setText("F2");
	}
	
	public void addPiecesOnBoard() {
		//we are adding the holes on the board
		hole = new ImageIcon(this.getClass().getResource("/black-hole.png")).getImage();
	    piece = hole.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
	    board[0][0].setIcon(new ImageIcon(piece));
	    board[0][4].setIcon(new ImageIcon(piece));
	    board[4][0].setIcon(new ImageIcon(piece));
	    board[4][4].setIcon(new ImageIcon(piece));
	    board[2][2].setIcon(new ImageIcon(piece));
	    
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
	    greyRabbit = new ImageIcon(this.getClass().getResource("/rabbit.png")).getImage();
	    piece = greyRabbit.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
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

