/**
 * X is col, Y is row
 * 
 * @author tomar
 *
 */
public class Fox extends Piece {

	public static enum FoxType {
		HORIZONTAL, VERTICAL
	};

	private boolean isHead;

	private FoxType foxType;
	Fox associatedPart;

	protected Fox(String pieceName, FoxType foxType, boolean isHead) {
		super(pieceName, Type.FOX);
		this.foxType = foxType;
		this.isHead = isHead;

	}

	public boolean isHead() {
		return isHead;
	}

//	@Override
//	public void setPosition(int x, int y) {
//		super.setPosition(x, y);
//		if (isHead) {
//			if (foxType.compareTo(FoxType.HORIZONTAL) == 0) {
//				associatedPart.setPosition(x - 1, y); // also change the tail location
//			} else if (foxType.compareTo(FoxType.VERTICAL) == 0) {
//				associatedPart.setPosition(x, y - 1); // also change the tail location
//			}
//		} else { //if tail
//			if (foxType.compareTo(FoxType.HORIZONTAL) == 0) {
//				associatedPart.setPosition(x + 1, y); // also change the tail location
//			} else if (foxType.compareTo(FoxType.VERTICAL) == 0) {
//				associatedPart.setPosition(x, y + 1); // also change the tail location
//			}
//		}
//	}

	/**
	 * Return true if added part succesfully.
	 * 
	 * @param associatedPart
	 * @return
	 */
	public boolean addAssociatedPart(Fox associatedPart) {
		if (associatedPart.isHead == this.isHead)
			return false;
		this.associatedPart = associatedPart;
		return true;
	}
	
	public Fox getAssociatedPart() {
		return associatedPart;
	}

	/**
	 * 
	 * @return the type of the fox
	 */
	public FoxType getFoxType() {
		return this.foxType;
	}
	
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