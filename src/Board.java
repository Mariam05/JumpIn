/**
 * This class creates the board of the game with all piece configurations on it
 * @author Mariam Almalki
 *
 */
public class Board {
	
	private Square[][] board;
	
	private static final int WIDTH = 5;
	private static final int LENGTH = 5;
	
	private Animal rabbit1;
	private Animal rabbit2;
	private Animal rabbit3;
	private Animal fox1;
	private Animal fox2;
	
	private int rabbitsInHoles;
	
	 
	
	public Board() {
		
		rabbitsInHoles = 0;
		
		board = new Square[WIDTH][LENGTH];
		
		//Initialize the board, which is a grid of squares
		for (int i = 0; i< WIDTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				board[i][j] = new Square(i,j);
			}
		}
		
		//set which squares contain holes. 
		board[0][0].setHole(); //left upper corner
		board[0][4].setHole(); //right upper corner
		board[4][0].setHole(); //left bottom corner
		board[2][2].setHole(); //right bottom corner
		board[4][4].setHole(); //middle
		
		//set which squares contain the mushrooms
		board[1][3].setMushroom();
		board[4][2].setMushroom();
		
		//instantiate pieces
		rabbit1 = new Animal(AnimalEnum.R1);
		rabbit2 = new Animal(AnimalEnum.R2);
		rabbit3 = new Animal(AnimalEnum.R3);
		
		fox1 = new Animal(AnimalEnum.F1);
		fox2 = new Animal(AnimalEnum.F2);
		
		//Add pieces to board. Fox positions are specified by their head
		board[1][1].addAnimal(fox1); 
		board[3][4].addAnimal(fox2);
		board[0][3].addAnimal(rabbit1);
		board[2][4].addAnimal(rabbit2);
		board[4][1].addAnimal(rabbit3);
	}
	
	public Square getSquare(int x, int y) {
		return board[x][y];
	}
	
	public void placePiece(int x, int y, Animal p) {
		board[x][y].addAnimal(p);
	}
	
	public void printBoard() {
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < LENGTH; j++) {
				System.out.print("|"+ board[i][j]);
			}
			System.out.println();
		}
	}
	
	
}
