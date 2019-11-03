/**
 * This class creates the board of the game with all the static objects that go on it
 * @author Mariam Almalki
 *
 */
public class Board {
	
	/**
	 * A 2D array of squares that makes up the board
	 */
	private Square[][] board; 
	
	/**
	 * The width of the board (i.e. num of columns)
	 */
	private static final int WIDTH = 5;
	
	/**
	 * The length of the board (i.e. num of rows)
	 */
	private static final int LENGTH = 5;
	
	/**
	 * Instantiates the array of squares and sets certain squares to be either holes or mushrooms
	 */
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
	
	public void addPiece(Piece piece, int x, int y) {
		board[y][x].addPiece(piece);
	}
	
	/**
	 * Returns the square at the specific position
	 * @param x the square's row number
	 * @param y the square's column number
	 * @return the desired square
	 */
	public Square getSquare(int x, int y) {
		return board[x][y];
	}
	
	
	/**
	 * Checks if a spot on the board is occupied by a piece
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isSpotEmpty(int x, int y) {
		//TODO: IMPLEMENT THIS
		return false;
	}
	
	/**
	 * Checks if a user inputted coordination is out of range
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean outOfRange(int x, int y) {
		//TODO: IMPLEMENT THIS
		return false;
	}
	
	/**
	 * Print the current state of the board
	 */
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
