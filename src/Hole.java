
public class Hole extends Piece {

	protected Hole(String pieceName) {
		super(pieceName, Type.HOLE);
	}

	/**
	 * Validate the move of a hole. 
	 * Will always be false because a hole can't move. 
	 */
	@Override
	public boolean validateMove(int destinationPos) {
		return false;
	}

	
	
}
