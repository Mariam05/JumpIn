package JumpIn;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

	private HashMap<String, Piece> piecesOnBoard;

	private List<Piece> pieceObjectsList;

	Piece fox1, fox2, fox1T, fox2T, rabbit1, rabbit2, rabbit3;

	/**
	 * Instantiates the array of squares and sets the hole positions Holes are set
	 * here because they are part of the board (i.e. cannot be moved/ are not
	 * pieces)
	 */
	public Board() {

		piecesOnBoard = new HashMap<>();
		pieceObjectsList = new ArrayList<>();

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

	public List<Piece> getPieceObjects() {
		return pieceObjectsList;
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
		piecesOnBoard.put(fox1.toString(), fox1);
		piecesOnBoard.put(fox2.toString(), fox2);
		piecesOnBoard.put(fox1T.toString(), fox1T);
		piecesOnBoard.put(fox2T.toString(), fox2T);
		piecesOnBoard.put(rabbit1.toString(), rabbit1);
		piecesOnBoard.put(rabbit2.toString(), rabbit2);
		piecesOnBoard.put(rabbit3.toString(), rabbit3);

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

		String[] elements = boardString.split("[ ]+");

		int i = 0, k = 0;
		Color c = Color.WHITE;
		for (int y = 0; y < newBoard.SIZE; y++) { // row
			for (int x = 0; x < newBoard.SIZE; x++) { // col
				String currPiece = elements[i];
				FoxType type = null;
				Piece temp;
				switch (currPiece) {
				
				case ("RA1"): // send down, white rabbit
					c = Color.WHITE;
					temp = new Rabbit(currPiece, c); // white rabbit
					newBoard.addPiece(temp, y, x); // the levels.json has them listed by columns
					newBoard.piecesOnBoard.put(currPiece, temp);
					newBoard.pieceObjectsList.add(temp);
					break;
					
				case ("RA2"): // send down, grey rabbit
					c = Color.GRAY;
					temp = new Rabbit(currPiece, c); // grey rabbit
					newBoard.addPiece(temp, y, x); // the levels.json has them listed by columns
					newBoard.piecesOnBoard.put(currPiece, temp);
					newBoard.pieceObjectsList.add(temp);
					break;
					
				case ("RA3"):
					c = Color.YELLOW;
					temp = new Rabbit(currPiece, c); // yellow rabbit
					newBoard.addPiece(temp, y, x); // the levels.json has them listed by columns
					
					newBoard.piecesOnBoard.put(currPiece, temp);
					newBoard.pieceObjectsList.add(temp);
					break;
					
				case ("F1H"): // horizontal fox
					type = FoxType.HORIZONTAL; // send down
				
				case ("F2V"): // vertical fox
					type = (type == null) ? FoxType.VERTICAL : type;
					temp = new Fox(currPiece, type, true);
					Piece tail = ((Fox) temp).getAssociatedPart();
					
					newBoard.addPiece(temp, y, x);
					newBoard.addPiece(tail, tail.getXPos(), tail.getYPos());
					
					newBoard.piecesOnBoard.put(currPiece, temp);
					newBoard.piecesOnBoard.put(tail.toString(), tail);
					
					newBoard.pieceObjectsList.add(temp);
					newBoard.pieceObjectsList.add(tail);
					break;
					
				case ("MSH"): // mushroom
					temp = new Mushroom(currPiece);
					newBoard.addPiece(temp, y, x);
					newBoard.piecesOnBoard.put(currPiece + k, temp);
					k++; // k is just to give the mushrooms diff names so that the hashmap contains them
							// all
					newBoard.pieceObjectsList.add(temp);
					break;
				}
				// System.out.println("i: " + currPiece + " y: " + y + " x: " + x);
				i++;
			}
		}

		return newBoard;
	}

	public static Board makeBoardFromLevel(String levelname) {
		return makeBoard(LevelsParser.getLevel(levelname));
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
			if (board[y][x].isHole())
				((Rabbit) piece).setInHole();
		}
	}

	public boolean hasWon() {

		for (String s : piecesOnBoard.keySet()) {
			Piece p = piecesOnBoard.get(s);
			if (p instanceof Rabbit) {
				if (!(((Rabbit) p).isInHole()))
					return false;
			}
		}
		return true;
	}

	/**
	 * Returns the list of animals on the board and their identification string
	 * 
	 * @return hashmap of animals on board
	 */
	public HashMap<String, Piece> getPiecesOnBoard() {
		return piecesOnBoard;
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

	/**
	 * Represent the pieces on this board with a string. 
	 * Tails are not shown in the string representation
	 * 
	 * @return
	 */
	public String getStringRepresentation() {
		String s = "";
		for (int i = 0; i < SIZE; i++) { // row
			for (int j = 0; j < SIZE; j++) { // column
				if ((board[j][i].getPiece() instanceof Fox) && !((Fox) board[j][i].getPiece()).isHead()) { //if tail
					s += "X ";
				} else {
					s += board[j][i].getPieceString() + " "; // adds by column
				}
			}
		}
		return s;
	}

	/**
	 * Print the current state of the board (for text based purposes only)
	 */
	public void printBoard() {

		System.out.println("          0         1         2         3         4   \n");

		for (int i = 0; i < SIZE; i++) {
			System.out.print(i + "    ");
			for (int j = 0; j < SIZE; j++) {
				System.out.print("  " + board[i][j].toString() + " ");
			}
			System.out.print("|\n");
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getStringRepresentation() == null) ? 0 : getStringRepresentation().hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Board b = (Board)obj;
		return this.getStringRepresentation().equals(b.getStringRepresentation());
	}

}