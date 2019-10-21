/**
 * This class creates the board of the game with all piece configurations on it
 * @author Mariam Almalki
 *
 */
public class Board {
	
	private Square[][] board;
	
	private static final int WIDTH = 5;
	private static final int LENGTH = 5;
	
	public Board() {		
		
		board = new Square[LENGTH][WIDTH];
		
		//Initialize the board, which is a grid of squares
		for (int i = 0; i< LENGTH; i++) {
			for (int j = 0; j < WIDTH; j++) {
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

	}
	
	public Square getSquare(int x, int y) {
		return board[x][y];
	}
	
	public void placePiece(int x, int y, Animal p) {
		board[x][y].addAnimal(p);
	}
	
	public void printBoard() {
		
		System.out.println("        0     1     2     3     4   \n");
		
		for (int i = 0; i < LENGTH; i++) {
			System.out.print(i+ "    ");
			for (int j = 0; j < WIDTH; j++) {
				System.out.print(board[i][j].toString());
			}
			System.out.print("|\n");
		}
	}
	
	
}
