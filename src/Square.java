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

	public void addPiece(Piece piece) {
		this.piece = piece;
		hasPiece = true;
	}

	public boolean hasPiece() {
		return hasPiece;
	}
	
	public void addHole() {
		isHole = true;
	}
	
	public boolean isHole() {
		return isHole;
	}

	/**
	 * This method will print what is on the square.
	 * 
	 * @return the string representation of the square
	 */
	@Override
	public String toString() {
		
		if(isHole() && hasPiece()) {
			return "|  " + piece.toString() + "* ";
		}
		if (isHole()) {
			return "|  HO  ";
		}
		if (hasPiece) {
			return "|  " + piece.toString() + "  ";
		}
		return "|      ";
	}


}