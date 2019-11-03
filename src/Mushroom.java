/**
 * Class for mushroom objects, which are stationary. 
 * @author tomar
 *
 */
public class Mushroom extends Piece {

	protected Mushroom(String pieceName) {
		super(pieceName);
	}

	/**
	 * Return a symbol to use on the board
	 */
	@Override
	public String toString() {
		return "MS";
	}

	/**
	 * Also return false because you can't move a mushroom. 
	 */
	@Override
	protected boolean validateMove(Board board, Piece animal, int currPos, int destinationPos) {
		// TODO Auto-generated method stub
		return false;
	}

}
