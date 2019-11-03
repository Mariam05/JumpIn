/**
 * This class represents a Rabbit piece on the board. 
 * Rabbits can only jump over other objects so they cannot move directly
 * to the any of the squares beside them. 
 * @author Mariam Almalki
 *
 */
public class Rabbit extends Piece {

	protected Rabbit(String pieceName) {
		super(pieceName, Type.RABBIT);
	}

	/**
	 * Check if a rabbit's move is valid. 
	 * An invalid move is if it is simply moving to one of the squares beside it
	 */
	@Override
	public boolean validateMove(int destinationPos) {
		int currX = getXPos();
		int currY = getYPos();
		int newX = destinationPos / 10;
		int newY = destinationPos % 10;
		
		if (Math.abs(currX - newX) < 2) return false; 
		if (Math.abs(currY - newY) < 2) return false;
		
		return true;
	}
}