package JumpIn;
/**
 * Class for mushroom objects, which are stationary. 
 * @author tomar
 *
 */
public class Mushroom extends Piece {

	private String stringRep;
	
	public Mushroom(String pieceName) {
		super(pieceName, Type.MUSHROOM);
		stringRep = getStringRepresentation();
	}


	/**
	 * Currently not moving the mushrooms. May need to implement this for the levels part
	 */
	@Override
	public void handleMove(Board board, int newX, int newY) {
		// TODO Auto-generated method stub
	}


	@Override
	public boolean validateMove(Board board, int newX, int newY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Piece manufacturePiece(String str) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getStringRepresentation() {
		return toString() + getXPos() + "" + getYPos() ;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Mushroom other = (Mushroom) obj;
		return stringRep.equals(other.stringRep);
	}
	
	

}