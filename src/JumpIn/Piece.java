package JumpIn;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class contains information regarding the pieces on the board
 * @author Mariam Almalki
 *
 */
public abstract class Piece implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Used to identify which piece it is
	 */
	private String pieceName;
	
	
	/**
	 * Where it is currently located on the board
	 */
	private int x, y;
	
	/**
	 * Constructor to make a generic piece with a name and associated type
	 * @param pieceName
	 * @param pieceType
	 */
	public Piece(String pieceName) {
		this.pieceName = pieceName;
	}
	
	
	/**
	 * Get all the positions that a piece can move to.
	 * The positions are stored in an array of 2 elements (first is x and second is y)
	 * 
	 * @param board
	 * @return the arraylist holding the positions of all the valid moves.
	 */
	public ArrayList<Command> getAllValidCommands(Board board) {
		ArrayList<Command> validCommands = new ArrayList<>();

		for (int i = 0; i < board.SIZE; i++) { //row
			for (int j = 0; j < board.SIZE; j++) { //column
				if (validateMove(board, j, i)) {
					validCommands.add(new Command("move", toString(), j + "" + i));
				}
			}
		}

		return validCommands;
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
	

	/**
	 * Check if the move is valid
	 * @param board
	 * @param newX
	 * @param newY
	 * @return true if the move is valid
	 */
	public abstract boolean validateMove(Board board, int newX, int newY);
	
	/**
	 * This is a factory method to make a new piece based on the string representation of another piece (deep copy)
	 * @param str the string representation
	 * @return the new Piece
	 */
	public abstract Piece manufacturePiece(String str);
	
	/**
	 * Return a string representation that can be used to uniquely identify the state of a piece
	 * @return
	 */
	public abstract String getStringRepresentation();
	
	
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getStringRepresentation() == null) ? 0 : getStringRepresentation().hashCode());
		return result;
	}
	
}