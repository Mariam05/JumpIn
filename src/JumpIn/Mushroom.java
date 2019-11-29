package JumpIn;

import java.util.Scanner;

/**
 * Class for mushroom objects, which are stationary. 
 * @author tomar
 *
 */
public class Mushroom extends Piece {

	public Mushroom(String pieceName) {
		super(pieceName);
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
		// Mushrooms can't be moved
		return false;
	}


	@Override
	public Piece manufacturePiece(String str) {
		Scanner dscanner = new Scanner(str).useDelimiter("\\s*,\\s*");

		String name = dscanner.next();
		int xPos = dscanner.nextInt();
		int yPos = dscanner.nextInt();
		
		dscanner.close();
		
		Mushroom newMushroom = new Mushroom(name);
		newMushroom.setPosition(xPos, yPos);
		
		return newMushroom;
	}


	@Override
	public String getStringRepresentation() {
		return toString() + "," + getXPos() + "," + getYPos() ;
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
		return getStringRepresentation().equals(other.getStringRepresentation());
	}
	
	

}