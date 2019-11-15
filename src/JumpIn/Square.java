package JumpIn;
/**
 * This class helps the Board class to create it's (5 * 5) board grid formed of
 * squares. In addition, identifies what is on the square and if the square is
 * empty or occupied by animal or an object
 * 
 * @author Mariam Almalki
 * @version 2.0
 */
public class Square {


	private Piece piece;
	private boolean hasPiece;
	private boolean isHole;
	private int x, y;
	/**
	 * The Constructor will create an empty square and specify its position by using
	 * i and j that the user will pass. Also, set isHole and isOccupied to false
	 * 
	 * @param i, j the position of the square
	 */
	public Square() {
		hasPiece = false;
	}

	/**
	 * Add a piece to the square
	 * @param piece
	 */
	public void addPiece(Piece piece) {
		this.piece = piece;
		hasPiece = true;
	}
	
	/**
	 * Retrieve the piece
	 * @return Piece the piece on the square or null if none exists
	 */
	public Piece getPiece() {
		if (hasPiece) return piece;
		return null;
	}
	
	/**
	 * Return the string representation of the piece on the square, or null if there is no piece
	 * @return String of piece
	 */
	public String getPieceString() {
		if (hasPiece) return piece.toString();
		return "";
	}
	
	public String toString() {
		if(hasPiece && isHole) return "| " + piece.toString() + "*  ";
		if (hasPiece) return "| " + piece.toString() + "  ";
		if (isHole) return "|  HOL ";
		return "|      ";
	}

	/**
	 * If square has piece
	 * @return true if it has a piece 
	 */
	public boolean hasPiece() {
		return hasPiece;
	}
	
	/**
	 * Make this square a hole
	 */
	public void addHole() {
		isHole = true;
	}
	
	/**
	 * Check if this square is a hole
	 * @return true if it is a hole
	 */
	public boolean isHole() {
		return isHole;
	}
	
	/**
	 * Remove the piece from the square
	 */
	public void removePiece() {
		this.hasPiece = false;
		piece = null;
		
	}

}