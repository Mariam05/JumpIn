/**
 * Class for mushroom objects, which are stationary. 
 * @author tomar
 *
 */
public class Mushroom extends Piece {

	protected Mushroom(String pieceName) {
		super(pieceName, Type.MUSHROOM);
	}

	/**
	 * Also return false because you can't move a mushroom. 
	 */
	@Override
	public boolean validateMove(int currPos, int destinationPos) {
		return false;
	}

	/**
	 * Return a symbol to use on the board
	 */
	@Override
	public String toString() {
		return "MS";
	}

}
