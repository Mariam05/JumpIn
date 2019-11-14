package JumpIn;

/**
 * This class represents a Fox object. A fox can either have horizontal or
 * vertical functionality. A fox is split into two parts: either a head or a
 * tail and is associated with it's counterpart.
 * 
 * X is col, Y is row
 * 
 * @author Mariam
 *
 */
public class Fox extends Piece {

	/**
	 * The type of fox, whether it's horizontal or vertical Specifies how it moves.
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
	 * The counterpart to this fox (i.e. if it's a head its associated part will be
	 * the tail)
	 */
	Fox associatedPart;

	/**
	 * Create a new fox object. Must specify the name, type, and part.
	 * 
	 * @param pieceName String name of fox
	 * @param foxType   FoxType for whether it moves horizontally or vertically
	 * @param isHead    bool representing whether its a head or tail
	 */
	public Fox(String pieceName, FoxType foxType, boolean isHead) {
		super(pieceName, Type.FOX);
		this.foxType = foxType;
		this.isHead = isHead;

	}

	/**
	 * Determine if it's a head or tail
	 * 
	 * @return true if this fox object represents the head
	 */
	public boolean isHead() {
		return isHead;
	}

	/**
	 * Return true if added part succesfully. It is unsuccessful if the user
	 * attempts to add the same type of Fox (ex. head/tail) as the associatedPart.
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
	 * 
	 * @return Fox type of associated part
	 */
	public Fox getAssociatedPart() {
		return associatedPart;
	}

	/**
	 * Get whether fox moves horizontally or vertically
	 * 
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
	public boolean validateMove(Board board, int newX, int newY) {

		int currX = getXPos();
		int currY = getYPos();
		boolean isTail = !isHead();

		int start, end;
		
		// If destination is already filled
		if (board.hasPiece(newX, newY))
			return false;

		// check that we're not trying to move diagonally
		if (foxType.compareTo(FoxType.HORIZONTAL) == 0) {
			if (currY != newY)
				return false;
		} else if (foxType.compareTo(FoxType.VERTICAL) == 0) {
			if (currX != newX)
				return false;
		}

		// If fox moves horizontally, check horizontal path on board
		if (isHorizontal()) {

			if (currX < newX) { // moving right
				if (!isTail) { // if head, start at square to right and continue to destination
					start = currX + 1;
					end = newX;
				} else { // if tail, start 2 squares to right and end at new+1 (which is where head will
							// be)
					start = currX + 2;
					end = newX + 1;
				}

				if (board.isOutOfRange(end, currY))
					return false;

				for (int i = start; i <= end; i++) { // ensure path is clear
					if (board.getSquare(i, currY).hasPiece())
						return false;
				}

			} else { // moving left
				if (!isTail) { // if head, start at square left to tail and check to where the tail would reach
					start = currX - 2;
					end = newX - 1;
				} else { // if tail, start at square left to tail and check to where the tail will go
					start = currX - 1;
					end = newX;
				}

				if (board.isOutOfRange(end, currY))
					return false;

				for (int i = start; i >= end; i--) { // reprompt if path isn't clear
					if (board.getSquare(i, currY).hasPiece())
						return false;
				}

			}
			

			
		} else if (!isHorizontal()) { // this fox moves vertically
			if (currY > newY) { // moving up

				if (isHead()) { // if head (head is below tail for vertical fox)
					start = currY - 2; // start at square above the tail
					end = newY - 1; // end at square above final head destination
				} else {
					start = currY - 1;
					end = newY;
				}

				if (board.isOutOfRange(end, currY))
					return false;

				for (int i = start; i < end; i++) { // reprompt if path isn't clear
					if (board.getSquare(i, currY).hasPiece())
						return false;
				}

			} else { // moving down

				if (!isTail) { // if head, start at square below head
					start = currY + 1;
					end = newY;
				} else { // if tail, start at 2 squares below curr location (to override head)
					start = currY + 2;
					end = newY + 1;
				}

				if (board.isOutOfRange(end, currY))
					return false;

				for (int i = start; i <= end; i++) { // reprompt if path isn't clear
					if (board.getSquare(currX, i).hasPiece())
						return false;
				}

			}

		}
		return true;

	}

	/**
	 * return true if move was handled TODO: Move common code outside of the
	 * specific if statment
	 * 
	 * @param fox
	 * @param command
	 * @return true if fox moved succesfully
	 */
	public void handleMove(Board board, int newX, int newY) {

		int currX = getXPos();
		int currY = getYPos();
		
		board.removePiece(currX, currY);
		
		if(!isHorizontal()) {
			if (isHead()) { 
				board.removePiece(currX, currY - 1); // remove fox tail

				board.addPiece(this, newX, newY); // add head
				board.addPiece(getAssociatedPart(), newX, newY - 1); // add tail

			} else { // dealing with the tail
				board.removePiece(currX, currY); // remove fox tail
				board.removePiece(currX, currY + 1); // remove fox head

				board.addPiece(this, newX, newY); // add tail
				board.addPiece(getAssociatedPart(), newX, newY + 1); // add head
			}

		} else {
			if (!isHead()) {
				board.removePiece(currX + 1, currY); // remove head of fox

				board.addPiece(this, newX, currY); // add tail of fox
				board.addPiece(getAssociatedPart(), newX + 1, currY); // add head of fox
			} else {
				board.removePiece(currX, currY); // remove head of fox
				board.removePiece(currX - 1, currY); // remove tail of fox

				board.addPiece(this, newX, currY); // add head of fox
				board.addPiece(getAssociatedPart(), newX - 1, currY); // add tail of fox
			}
		}
	}

}