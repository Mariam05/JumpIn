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
		RABBIT, FOX, MUSHROOM, HOLE
	}
	
	/**
	 * Used to identify which piece it is
	 */
	protected String pieceName;
	
	/**
	 * What kind of piece is it. This may not be necessary...
	 */
	protected Type pieceType;
	
	/**
	 * Where it is currently located on the board
	 */
	protected int currPosition;
	
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
	public abstract boolean validateMove(int currPos, int destinationPos);
	
	/**
	 * Each piece must return a string that will symbolize it on the baord
	 */
	@Override
	public abstract String toString();
	
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
	 * Return the name of the piece. 
	 * @return
	 */
	public String getName() {
		return this.pieceName;
	}
	
}
