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
	 * To hold the direction that the player wants to move
	 */
	protected enum Direction {
		UP, DOWN, RIGHT, LEFT, INVALID
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
	protected int x, y;
		
	/**
	 * Constructor to make a generic piece with a name and associated type
	 * @param pieceName
	 * @param pieceType
	 */
	// Temporarily removing Type argument
	protected Piece(String pieceName) {
		this.pieceName = pieceName;
		if(pieceName.contains("R")) {
			this.pieceType = Type.RABBIT;
		}
		else if(pieceName.contains("F")) {
			this.pieceType = Type.FOX;
		}
		else if(pieceName.contains("M")) {
			this.pieceType = Type.MUSHROOM;
		}
		else {
			this.pieceType = Type.HOLE;
			
		}
	}
		
	/**
	 * Each piece is responsible for validating its own move.
	 * @param currPos
	 * @param destinationPos
	 * @return
	 */
	protected abstract boolean validateMove(Board board, Piece animal, int currPos, int destinationPos);
	
	/**
	 * Returns whether the piece is in a hole
	 * @return
	 */
	protected boolean isInHole() {
		return false;
	}
	/**
	 * Each piece must return a string that will symbolize it on the board
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
	 * Set the position of the piece
	 * @param position in the form of rowCol
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the position of the piece in the form of rowCol
	 * @return currPosition
	 */
	public int getPosition() {
		return (this.x * 10) + this.y;
	}
	
	/**
	 * Return the name of the piece. 
	 * @return
	 */
	public String getName() {
		return this.pieceName;
	}
	
	/**
	 * Determines the direction that the player wants the animal to move in
	 * @param currX the animal's current row position
	 * @param currY the animal's current column position
	 * @param newX the destination's row position
	 * @param newY the destination's column position
	 * @return the direction to move in
	 */
	protected Direction getDirection(int currX, int currY, int newX, int newY) {
		Direction d = Direction.INVALID;

		boolean valid = false;
		while (!valid) {
			// How are we getting the animal's position
			if (currX == newX) { // moving horizontally - now check if it is right or left
				if (currY == newY) {
					//invalidDirectionMessage();
				} else if (currY < newY) {
					return Direction.RIGHT;

				} else {
					return Direction.LEFT;
				}
			} else if (currY == newY) { // moving vertically
				if (currX == newX) {
					//invalidDirectionMessage();
				} else if (currX < newX) {
					return Direction.DOWN;
				} else {
					return Direction.UP;
				}
			} else {
				//invalidDirectionMessage();
			}
		}

		return d;
	}
}
