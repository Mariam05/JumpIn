package contoller;
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
	 * Instantiates the array of squares and sets the hole positions
	 */
	public Board() {		
		
		board = new Square[LENGTH][WIDTH];
		
		// Initialize the board, which is a grid of squares
		for (int i = 0; i< LENGTH; i++) { //row
			for (int j = 0; j < WIDTH; j++) { //column
				board[i][j] = new Square();
			}
		}
		
		// Set the holes of the board. Hole positions don't change
		board[0][0].addHole();
		board[0][4].addHole();
		board[4][0].addHole();
		board[4][4].addHole();
		board[2][2].addHole();

	}
	
	/**
	 * Adds a piece to the board
	 * @param piece The piece to add
	 * @param x the x position to add the piece to (i.e. the column)
	 * @param y the y position to add the piece to (i.e. the row)
	 */
	public void addPiece(Piece piece, int x, int y) {
		board[y][x].addPiece(piece);
		piece.setPosition(x, y);
	}
	
	/**
	 * Remove the piece at the specified square's location
	 * @param x
	 * @param y
	 */
	public void removePiece(int x, int y) {
		board[y][x].removePiece();
	}
	
	/**
	 * Put a hole on the board
	 * @param x
	 * @param y
	 */
	public void addHole(int x, int y) {
		board[y][x].addHole();
	}
	
	/**
	 * Returns the square at the specific position
	 * @param x the square's row number
	 * @param y the square's column number
	 * @return the desired square
	 */
	public Square getSquare(int x, int y) {
		return board[y][x];
	}
	
	
	/**
	 * Checks if a spot on the board is occupied by a piece
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isSpotEmpty(int x, int y) {
		return board[y][x].hasPiece();
	}
	
	/**
	 * Checks if a user inputted coordination is out of range
	 * @param x
	 * @param y
	 * @return true if destination is out of range
	 */
	public boolean isOutOfRange(int x, int y) {
		if (x > WIDTH || x < 0 || y > LENGTH || y < 0) return true;
		return false;
	}
	
	/**
	 * Print the current state of the board
	 */
	public void printBoard() {
		
		System.out.println("        0      1      2      3      4   \n");
		
		for (int i = 0; i < LENGTH; i++) {
			System.out.print(i+ "    ");
			for (int j = 0; j < WIDTH; j++) {
				System.out.print(board[i][j].toString());
			}
			System.out.print("|\n");
		}
	}
	
	
}