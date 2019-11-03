<<<<<<< HEAD
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
	private int currPosition;
	
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
	 * Each piece is responsible for validating its own move.
	 * @param currPos
	 * @param destinationPos
	 * @return
	 */
	public abstract boolean validateMove(int destinationPos);
	
	
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
	public void setPosition(int pos) {
		this.currPosition = pos;
	}
	
	/**
	 * Get the position of the piece in the form of rowCol
	 * @return int currPosition
	 */
	public int getPosition() {
		return this.currPosition;
	}
	
	/**
	 * Get the piece's x position
	 * @return int x position
	 */
	public int getXPos() {
		return getPosition() / 10; //the first digit in the entered pos
	}
	
	
	/**
	 * Get the piece's y position
	 * @return int y position
	 */
	public int getYPos() {
		return getPosition() % 10; //the second digit in the entered pos
	}
	
	/**
	 * Return a string representation of the piece that can be used
	 * to symbolize it on the baord.
	 */
	@Override
	public String toString() {
		return this.pieceName;
	}
	
	
=======
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
	private int currPosition;
	
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
	 * Each piece is responsible for validating its own move.
	 * @param currPos
	 * @param destinationPos
	 * @return
	 */
	public abstract boolean validateMove(int destinationPos);
	
	
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
	public void setPosition(int pos) {
		this.currPosition = pos;
	}
	
	/**
	 * Get the position of the piece in the form of rowCol
	 * @return int currPosition
	 */
	public int getPosition() {
		return this.currPosition;
	}
	
	/**
	 * Get the piece's x position
	 * @return int x position
	 */
	public int getXPos() {
		return getPosition() / 10; //the first digit in the entered pos
	}
	
	
	/**
	 * Get the piece's y position
	 * @return int y position
	 */
	public int getYPos() {
		return getPosition() % 10; //the second digit in the entered pos
	}
	
	/**
	 * Return a string representation of the piece that can be used
	 * to symbolize it on the baord.
	 */
	@Override
	public String toString() {
		return this.pieceName;
	}
	
	
>>>>>>> ada47d1d590091184cd420e0e49786311fcb0bcf
}