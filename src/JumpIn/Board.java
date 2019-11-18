package JumpIn;

import java.util.HashMap;

/**
 * This class creates the board of the game with all the static objects that go on it
 * @author Mariam Almalki
 * 
 * Create a new enum class for boardlevels. 
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
	public final int SIZE = 5;
	
	
	private int numOfRabbitsInHoles;
	
	private HashMap<Piece, String> rabbits; //this hashmap is to keep track of the number of rabbits added to the board
	
	/**
	 * Instantiates the array of squares and sets the hole positions
	 * Holes are set here because they are part of the board (i.e. cannot be moved/ are not pieces)
	 */
	public Board() {	
			
		
		numOfRabbitsInHoles = 0;
		rabbits = new HashMap<>();
		
		board = new Square[SIZE][SIZE];
		
		// Initialize the board, which is a grid of squares
		for (int i = 0; i< SIZE; i++) { //row
			for (int j = 0; j < SIZE; j++) { //column
				board[i][j] = new Square();
			}
		}
		
		// Set the holes of the board. Hole positions don't change
		board[0][0].addHole();
		board[0][4].addHole();
		board[4][0].addHole();
		board[4][4].addHole();
		board[2][2].addHole();
		
		Mushroom mushroom1 = new Mushroom("MSH");
		Mushroom mushroom2 = new Mushroom("MSH");
		addPiece(mushroom1, 2, 4);
		addPiece(mushroom2, 3, 1);
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
		if(piece instanceof Rabbit) {
			rabbits.put(piece, piece.toString());
		}
	}
	
	public boolean hasWon() {
		int numInHoles = 0;
		for (Piece p : rabbits.keySet()) {
			if (getSquare(p.getXPos(), p.getYPos()).isHole()) {
				numInHoles++;
			}
		}
		if (numInHoles == rabbits.size()) { //if all the rabbits are in a hole return true
			return true;
		}
		return false;
	}
	
	/**
	 * Remove the piece at the specified square's location
	 * @param x
	 * @param y
	 */
	public Piece removePiece(int x, int y) {
		Piece temp = board[y][x].getPiece();
		board[y][x].removePiece();
		return temp;
	}
	
	
	public void increaseNumRabbitsInHoles() {
		numOfRabbitsInHoles++;
	}
	
	public void decreaseNumRabbitsInHoles() {
		numOfRabbitsInHoles--;
	}
	
	public int getNumRabbitsInHoles() {
		return numOfRabbitsInHoles;
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
	 * Method to reduce chaining
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hasPiece(int x, int y) {
		return board[y][x].hasPiece();
	}
	
	/**
	 * Method to reduce chaining
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isHole(int x, int y) {
		return board[y][x].isHole();
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
	 * Returns the piece on the specified coordinate of the board
	 * @param x
	 * @param y
	 * @return
	 */
	public Piece getPieceOnBoard(int x, int y) {
		return board[y][x].getPiece();
	}
	
	
	/**
	 * Checks if a user inputted coordination is out of range
	 * @param x
	 * @param y
	 * @return true if destination is out of range
	 */
	public boolean isOutOfRange(int x, int y) {
		if (x >= SIZE || x < 0 || y >= SIZE || y < 0) return true;
		return false;
	}
	
	/**
	 * Print the current state of the board (for text based purposes only)
	 */
	public void printBoard() {
		
		System.out.println("        0       1       2       3       4   \n");
		
		for (int i = 0; i < SIZE; i++) {
			System.out.print(i+ "    ");
			for (int j = 0; j < SIZE; j++) {
				System.out.print(board[i][j].toString());
			}
			System.out.print("|\n");
		}
	}
	
	
}