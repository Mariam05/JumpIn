
public class Rabbit extends Piece {

	protected Rabbit(String pieceName) {
		super(pieceName, Type.RABBIT);
	}

	/**
	 * Check if a rabbit's move is valid. 
	 * An invalid move is if it is simply moving to the square beside it
	 */
	@Override
	public boolean validateMove(int destinationPos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
