package JumpIn;

import java.io.Serializable;
import java.util.Scanner;

/**
 * Class for mushroom objects, which are stationary. 
 * @author tomar
 *
 */
public class Mushroom extends Piece implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Mushroom(String pieceName) {
		super(pieceName);
	}


	/**
	 * The player isn't allowed to move the mushroom. 
	 * For the level builder, we put the mushrooms using the setPosition()
	 */
	@Override
	public void handleMove(Board board, int newX, int newY) {
		// 
	}


	@Override
	public boolean validateMove(Board board, int newX, int newY) {
		return !board.hasPiece(newX, newY); //if it doesn't have a piece it can be moved there
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