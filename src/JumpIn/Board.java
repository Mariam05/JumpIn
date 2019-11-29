package JumpIn;

import java.awt.Color;
import java.util.HashMap;

import JumpIn.Fox.FoxType;

/**
 * This class creates the board of the game with all the static objects that go
 * on it
 * 
 * @author Mariam Almalki
 * 
 *         Create a new enum class for boardlevels.
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

	private HashMap<String, Piece> animalPieces;

	private HashMap<Piece, String> allPossiblePieces;

	Piece fox1, fox2, fox1T, fox2T, rabbit1, rabbit2, rabbit3;

	private HashMap<Piece, String> rabbits; // this hashmap is to keep track of the number of rabbits added to the board

	/**
	 * Instantiates the array of squares and sets the hole positions Holes are set
	 * here because they are part of the board (i.e. cannot be moved/ are not
	 * pieces)
	 */
	public Board() {

		numOfRabbitsInHoles = 0;
		rabbits = new HashMap<>();
		animalPieces = new HashMap<>();

		board = new Square[SIZE][SIZE];

		// Initialize the board, which is a grid of squares
		for (int i = 0; i < SIZE; i++) { // row
			for (int j = 0; j < SIZE; j++) { // column
				board[i][j] = new Square();
			}
		}

		// Set the holes of the board. Hole positions don't change
		board[0][0].addHole();
		board[0][4].addHole();
		board[4][0].addHole();
		board[4][4].addHole();
		board[2][2].addHole();
		
		// addAllInitialPieces();
	}

	public void addDefaultPieces() {
		Mushroom mushroom1 = new Mushroom("MSH");
		Mushroom mushroom2 = new Mushroom("MSH");
		addPiece(mushroom1, 2, 4);
		addPiece(mushroom2, 3, 1);

		// Instantiate the pieces on the board

		fox1 = new Fox("F1H", Fox.FoxType.HORIZONTAL, true);
		fox1T = ((Fox) fox1).getAssociatedPart();

		fox2 = new Fox("F2V", Fox.FoxType.VERTICAL, true);
		fox2T = ((Fox) fox2).getAssociatedPart();

		rabbit1 = new Rabbit("RA1", Color.WHITE);
		rabbit2 = new Rabbit("RA2", Color.GRAY);
		rabbit3 = new Rabbit("RA3", Color.YELLOW);

		// Add the pieces to the piece hashmap
		animalPieces.put(fox1.toString(), fox1);
		animalPieces.put(fox2.toString(), fox2);
		animalPieces.put(fox1T.toString(), fox1T);
		animalPieces.put(fox2T.toString(), fox2T);
		animalPieces.put(rabbit1.toString(), rabbit1);
		animalPieces.put(rabbit2.toString(), rabbit2);
		animalPieces.put(rabbit3.toString(), rabbit3);

		// Add the pieces to the board. Coordinates are row col
		addPiece(fox1, 4, 3);
		addPiece(fox1T, fox1T.getXPos(), fox1T.getYPos()); // add fox1 tail

		addPiece(fox2, 1, 1);
		addPiece(fox2T, fox2.getXPos(), fox2.getYPos() - 1); // add fox2 tail

		addPiece(rabbit1, 3, 0);
		addPiece(rabbit2, 4, 2);
		addPiece(rabbit3, 1, 4);
	}

	/**
	 * Factory method to make a new board based on a string representation
	 * 
	 * @param boardString
	 * @return
	 */
	public static Board makeBoard(String boardString) {
		Board newBoard = new Board();

		String[] elements = boardString.split(" ");

		int i = 0;
		for (int y = 0; y < newBoard.SIZE; y++) { // row
			for (int x = 0; x < newBoard.SIZE; x++) { // col
				String currPiece = elements[i];
				FoxType type = null;
				Piece temp;
				switch (currPiece) {
				case ("RA1"): // send down
				case ("RA2"): // send down
				case ("RA3"):
					temp = new Rabbit(currPiece); // white rabbit
					newBoard.addPiece(temp, x, y); // the levels.json has them listed by columns
					break;
				case ("F1H"): // horizontal fox
					type = FoxType.HORIZONTAL; // send down
				case ("F2V"): // vertical fox
					type = (type == null) ? FoxType.VERTICAL : type;
					temp = new Fox(currPiece, type, true);
					Piece tail = ((Fox) temp).getAssociatedPart();
					newBoard.addPiece(temp, x, y);
					newBoard.addPiece(tail, tail.getXPos(), tail.getYPos());
					break;
				case ("MSH"): // mushroom
					temp = new Mushroom(currPiece);
					newBoard.addPiece(temp, x, y);
					break;
				}

				i++;
			}
		}

		return newBoard;
	}

	/**
	 * Adds a piece to the board
	 * 
	 * @param piece The piece to add
	 * @param x     the x position to add the piece to (i.e. the column)
	 * @param y     the y position to add the piece to (i.e. the row)
	 */
	public void addPiece(Piece piece, int x, int y) {
		board[y][x].addPiece(piece);
		piece.setPosition(x, y);
		if (piece instanceof Rabbit) {
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
		if (numInHoles == rabbits.size()) { // if all the rabbits are in a hole return true
			return true;
		}
		return false;
	}

	/**
	 * Returns the list of animals on the board and their identification string
	 * 
	 * @return hashmap of animals on board
	 */
	public HashMap<String, Piece> getAnimalsOnBoard() {
		return animalPieces;
	}

	/**
	 * Remove the piece at the specified square's location
	 * 
	 * @param x
	 * @param y
	 */
	public Piece removePiece(int x, int y) {
		Piece temp = board[y][x].getPiece();
		board[y][x].removePiece();
		return temp;
	}

	/**
	 * Put a hole on the board
	 * 
	 * @param x
	 * @param y
	 */
	public void addHole(int x, int y) {
		board[y][x].addHole();
	}

	/**
	 * Method to reduce chaining
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hasPiece(int x, int y) {
		return board[y][x].hasPiece();
	}

	/**
	 * Method to reduce chaining
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isHole(int x, int y) {
		return board[y][x].isHole();
	}

	/**
	 * Returns the square at the specific position
	 * 
	 * @param x the square's row number
	 * @param y the square's column number
	 * @return the desired square
	 */
	public Square getSquare(int x, int y) {
		return board[y][x];
	}

	/**
	 * Returns the piece on the specified coordinate of the board
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Piece getPieceOnBoard(int x, int y) {
		return board[y][x].getPiece();
	}

	/**
	 * Checks if a user inputted coordination is out of range
	 * 
	 * @param x
	 * @param y
	 * @return true if destination is out of range
	 */
	public boolean isOutOfRange(int x, int y) {
		if (x >= SIZE || x < 0 || y >= SIZE || y < 0)
			return true;
		return false;
	}

	public String getStringRepresentation() {
		String s = "";
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				s += board[i][j].individualString() + " ";
			}
		}
		return s;
	}

	/**
	 * Print the current state of the board (for text based purposes only)
	 */
	public void printBoard() {

		System.out.println("        0       1       2       3       4   \n");

		for (int i = 0; i < SIZE; i++) {
			System.out.print(i + "    ");
			for (int j = 0; j < SIZE; j++) {
				System.out.print("|  " + board[i][j].toString() + "  ");
			}
			System.out.print("|\n");
		}
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

	public static void main(String[] args) {
		Board b = new Board();
		b.addDefaultPieces();
		System.out.println(b.getStringRepresentation());
		Board.makeBoard("X FTR1 X MSH X X FHR1 X X X X X MSH X X MSH RA3 X X RA2 X X RA1 X X");
	}

}