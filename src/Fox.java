/**
 * 
 * @author tomar
 *
 */
public class Fox extends Piece {

	public static enum FoxType {
		HORIZONTAL, VERTICAL
	};

	private FoxType foxType;
	private int tailXPos;
	private int tailYPos;

	protected Fox(String pieceName, FoxType foxType) {
		super(pieceName, Type.FOX);
		this.foxType = foxType;
		
	}

	/**
	 * 
	 * @return the type of the fox
	 */
	public FoxType getFoxType() {
		return this.foxType;
	}
	/**
	 * Each fox is refered to by its head. This method sets its tail position.
	 */
	private void setTailPos() {
		if (foxType.compareTo(FoxType.HORIZONTAL) == 0) {
			tailXPos = getXPos() - 1; //The head is to the right of the tail on the board
		} else if (foxType.compareTo(FoxType.VERTICAL) == 0) {
			tailYPos = getYPos() - 1; //The head is below the tail on the board
		}
	}
	
	/**
	 * When you set a position for a fox, you set the position to it's head location.
	 * This method will also set it's tail position by calling the 
	 * internal setTailPos() helper method. 
	 */
	@Override
	public void setPosition(int pos) {
		super.setPosition(pos);
		setTailPos();
	}
	
	/**
	 * Get the X position of the tail
	 * @return int of X position
	 */
	public int getTailXPos() {
		return tailXPos;
	}
	
	/**
	 * Get the Y position of the tail
	 * @return int of Y position
	 */
	public int getTailYPos() {
		return tailYPos;
	}
	
	/**
	 * Make sure that the fox's move is valid. i.e. that the user is not trying to
	 * move it in a direction that isn't valid, especially with respect to the
	 * actual fox.
	 * BIG note: it does not check if there are obstacles in the way, 
	 * or if it's going off the board. That's not this method's responsibility.
	 * 
	 * @param destinationPos the position that the player wants the fox to move.
	 */
	@Override
	public boolean validateMove(int destinationPos) {
		int currX = getXPos();
		int currY = getYPos();
		int newX = destinationPos / 10;
		int newY = destinationPos % 10;

		if (foxType.compareTo(FoxType.HORIZONTAL) == 0) {
			if (currX == newX) return true;
		} else if (foxType.compareTo(FoxType.VERTICAL) == 0) {
			if (currY == newY) return true;
		}
		return false;
	}
	

}