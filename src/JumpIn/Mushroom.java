package JumpIn;
/**
 * Class for mushroom objects, which are stationary. 
 * @author tomar
 *
 */
public class Mushroom extends Piece {

	public Mushroom(String pieceName) {
		super(pieceName, Type.MUSHROOM);
	}


	/**
	 * Currently not moving the mushrooms. May need to implement this for the levels part
	 */
	@Override
	public boolean handleMove(Board board, int newX, int newY) {
		// TODO Auto-generated method stub
		return false;
	}

}