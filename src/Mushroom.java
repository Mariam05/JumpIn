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
	public boolean validateMove(int destinationPos) {
		return false;
	}

}
