package JumpIn;

import java.util.ArrayList;

/**
 * This class contains information regarding the pieces on the board
 * @author Mariam Almalki
 *
 */
public abstract class Piece {
	
	/**
	 * The type of piece. Is this necessary?
	 * @author tomar
	 *
	 */
	public enum Type {
		RABBIT, FOX, MUSHROOM
	}
	
	/**
	 * Used to identify which piece it is
	 */
	private String pieceName;
	
	/**
	 * What kind of piece is it. This may not be necessary...
	 */
	private Type pieceType;
	
	/**
	 * Where it is currently located on the board
	 */
	private int x, y;
	
	/**
	 * Constructor to make a generic piece with a name and associated type
	 * @param pieceName
	 * @param pieceType
	 */
	protected Piece(String pieceName, Type pieceType) {
		this.pieceName = pieceName;
		this.pieceType = pieceType;
	}
	
	/**
	 * Get all the positions that a piece can move to.
	 * The positions are stored in an array of 2 elements (first is x and second is y)
	 * 
	 * @param board
	 * @return the arraylist holding the positions of all the valid moves.
	 */
	public ArrayList<int[]> getAllValidMoves(Board board) {
		ArrayList<int[]> allValidMoves = new ArrayList<>();
		Command command;
		ArrayList<Command> validCommands = new ArrayList<>();

		for (int i = 0; i < board.SIZE; i++) {
			for (int j = 0; j < board.SIZE; j++) {
				if (validateMove(board, i, j)) {
					int[] coordinates = { i, j };
					allValidMoves.add(coordinates);
					validCommands.add(new Command("move", toString(), i + "" + j));
				}
			}
		}

		return allValidMoves;
	}
	
	/**
	 * Get all the positions that a piece can move to.
	 * The positions are stored in an array of 2 elements (first is x and second is y)
	 * 
	 * @param board
	 * @return the arraylist holding the positions of all the valid moves.
	 */
	public ArrayList<Command> getAllValidCommands(Board board) {
		Command command;
		ArrayList<Command> validCommands = new ArrayList<>();

		for (int i = 0; i < board.SIZE; i++) {
			for (int j = 0; j < board.SIZE; j++) {
				if (validateMove(board, i, j)) {
					validCommands.add(new Command("move", toString(), i + "" + j));
				}
			}
		}

		return validCommands;
	}
	
	
	/**
	 * print all the valid moves, for testing/debugging purposes. 
	 * @param board
	 */
	public void printAllValidMoves(Board board) {
		for (int[] i : getAllValidMoves(board)) {
			System.out.print(i[0] + " " + i[1] + "    ");
		}
	}
	
	/**
	 * This method will actually change the location of the piece to the 
	 * new location.  
	 * @param board
	 * @param newX
	 * @param newY
	 * @return
	 */
	public abstract void handleMove(Board board, int newX, int newY);
	
	
	public abstract boolean validateMove(Board board, int newX, int newY);
	
	public abstract Piece manufacturePiece(String str);
	
	public abstract String getStringRepresentation();
	
	/**
	 * Get the type of piece
	 * @return Type of piece
	 */
	public Type getPieceType() {
		return this.pieceType;
	}
	
	/**
	 * Set the position of thep piece
	 * @param position in the form of rowCol
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the piece's x position
	 * @return int x position
	 */
	public int getXPos() {
		//System.out.println("X pos: " + x);
		return x; //the first digit in the entered pos
	}
	
	
	/**
	 * Get the piece's y position
	 * @return int y position
	 */
	public int getYPos() {
		return y; //the second digit in the entered pos
	}
	
	/**
	 * Return a string representation of the piece that can be used
	 * to symbolize it on the baord.
	 */
	@Override
	public String toString() {
		return this.pieceName;
	}
	
	@Override
	public abstract boolean equals(Object obj);
	
}