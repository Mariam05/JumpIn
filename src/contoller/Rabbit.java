package contoller;
public class Rabbit extends Piece {

	protected Rabbit(String pieceName) {
		super(pieceName, Type.RABBIT);
	}

	/**
	 * Check if a rabbit's move is valid. 
	 * An invalid move is if it is simply moving to one of the squares beside it
	 */
	@Override
	public boolean validateMove(int x, int y) {
		int currX = getXPos();
		int currY = getYPos();
		
		if ((Math.abs(currX - x) < 2) && currY == y) return false; //moving one square horizontally
		if ((Math.abs(currY - y) < 2) && currX == x) return false; //moving one square vertically
		if (!((currX != x && currY == y) || (currX == x && currY != y))) return false; //moving vertically
		
		return true;
	}
}