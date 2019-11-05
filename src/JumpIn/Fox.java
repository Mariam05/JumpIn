package JumpIn;
/**
 * This class represents a Fox object. 
 * A fox can either have horizontal or vertical functionality.
 * A fox is split into two parts: either a head or a tail and is associated 
 * with it's counterpart. 
 * 
 * X is col, Y is row
 * 
 * @author Mariam
 *
 */
public class Fox extends Piece {

	/**
	 * The type of fox, whether it's horizontal or vertical
	 * Specifies how it moves.
	 */
	public static enum FoxType {
		HORIZONTAL, VERTICAL
	};

	/**
	 * Used to determine whether this fox is a head or a tail
	 */
	private boolean isHead;

	/**
	 * The type of fox
	 */
	private FoxType foxType;
	
	/**
	 * The counterpart to this fox 
	 * (i.e. if it's a head its associated part will be the tail)
	 */
	Fox associatedPart;

	/**
	 * Create a new fox object. Must specify the name, type, and part.
	 * @param pieceName String name of fox
	 * @param foxType FoxType for whether it moves horizontally or vertically
	 * @param isHead bool representing whether its a head or tail
	 */
	public Fox(String pieceName, FoxType foxType, boolean isHead) {
		super(pieceName, Type.FOX);
		this.foxType = foxType;
		this.isHead = isHead;

	}

	/**
	 * Determine if it's a head or tail
	 * @return true if this fox object represents the head
	 */
	public boolean isHead() {
		return isHead;
	}


	/**
	 * Return true if added part succesfully.
	 * It is unsuccessful if the user attempts to add the same type of Fox (ex. head/tail)
	 * as the associatedPart. 
	 * 
	 * @param associatedPart the other part of the fox
	 * @return true if they added the correct part. 
	 */
	public boolean addAssociatedPart(Fox associatedPart) {
		if (associatedPart.isHead == this.isHead)
			return false;
		this.associatedPart = associatedPart;
		return true;
	}
	
	/**
	 * Get the other part of the fox
	 * @return Fox type of associated part
	 */
	public Fox getAssociatedPart() {
		return associatedPart;
	}


	/**
	 * Get whether fox moves horizontally or vertically
	 * @return true if moves horizontally
	 */
	public boolean isHorizontal() {
		return foxType.compareTo(FoxType.HORIZONTAL) == 0;
	}

	/**
	 * Make sure that the fox's move is valid. i.e. that the user is not trying to
	 * move it in a direction that isn't valid, especially with respect to the
	 * actual fox. BIG note: it does not check if there are obstacles in the way, or
	 * if it's going off the board. That's not this method's responsibility.
	 * 
	 * @param destinationPos the position that the player wants the fox to move.
	 */
	@Override
	public boolean validateMove(int x, int y) {
		int currX = getXPos();
		int currY = getYPos();

		if (foxType.compareTo(FoxType.HORIZONTAL) == 0) {
			if (currY == y)
				return true;
		} else if (foxType.compareTo(FoxType.VERTICAL) == 0) {
			if (currX == x)
				return true;
		}
		return false;
	}

}