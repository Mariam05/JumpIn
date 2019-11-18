package JumpIn;

import java.util.ArrayList;
import java.util.Scanner;

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
	private Fox associatedPart;

	/**
	 * Create a new fox object. Must specify the name, type, and part.
	 * 
	 * @param pieceName String name of fox
	 * @param foxType   FoxType for whether it moves horizontally or vertically
	 * @param isHead    bool representing whether its a head or tail
	 */
	public Fox(String pieceName, FoxType foxType, boolean isHead) {
		super(pieceName);
		this.foxType = foxType;
		this.isHead = isHead;
		if (isHead) {
			associatedPart = makeTail();
			updateTailPos();
		}

	}

	/**
	 * Construct a tail for this fox's head
	 * 
	 * @return the tail object
	 */
	private Fox makeTail() {
		return new Fox("F" + toString().charAt(1) + "T", foxType, false);
	}

	private void updateTailPos() {
		if (isHorizontal()) {
			associatedPart.setPosition(getXPos() - 1, getYPos());
		} else {
			associatedPart.setPosition(getXPos(), getYPos() - 1);
		}
	}

	/**
	 * Change the position of the fox and it's tail.
	 */
	@Override
	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		if (isHead()) {
			updateTailPos();
		}

	}

	/**
	 * Get the string representation of the fox in the format
	 * name,type,ishead,xPos,yPos. Likely will be renamed to toString()... but for
	 * now we want to keep toString() as returning just the piecename.
	 * 
	 * @return
	 */
	public String getStringRepresentation() {
		return toString() + "," + foxType.toString() + "," + isHead + "," + getXPos() + "," + getYPos();
	}

	/**
	 * This is a factory method used to manufacture a new fox based on string input.
	 * Would like to make this method static but can't with abstract stuff.
	 * 
	 * @param str
	 * @return the new Fox
	 */
	public Fox manufacturePiece(String str) {
		Scanner dscanner = new Scanner(str).useDelimiter("\\s*,\\s*");

		String name = dscanner.next();
		FoxType type = FoxType.valueOf(dscanner.next());
		boolean isHead = dscanner.nextBoolean();

		Fox newFox = new Fox(name, type, isHead);

		int xPos = dscanner.nextInt();
		int yPos = dscanner.nextInt();

		newFox.setPosition(xPos, yPos);

		return newFox;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Fox other = (Fox) obj;

		return getStringRepresentation().equals(other.getStringRepresentation());

	}

	/**
	 * Make sure that the fox's move is valid. i.e. that the user is not trying to
	 * move it in a direction that isn't valid, especially with respect to the
	 * actual fox.
	 * 
	 * @param destinationPos the position that the player wants the fox to move.
	 */
	public boolean validateMove(Board board, int newX, int newY) {

		int currX = getXPos();
		int currY = getYPos();

		int start, end;

		if (currX == newX && currY == newY)
			return false; // to eliminate unnecessary moves

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
				if (isHead()) { // if head, start at square to right and continue to destination
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
				if (isHead()) { // if head, start at square left to tail and check to where the tail would reach
					start = currX - 2;
					end = newX - 1;
				} else { // if tail, start at square left to tail and check to where the tail will go
					start = currX - 1;
					end = newX;
				}

				if (board.isOutOfRange(end, currY))
					return false;

				for (int i = end; i <= start; i++) { // check if path is clear
					if (board.hasPiece(i, currY))
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
				if (isHead()) { // if head, start at square below head
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
	 * return true if move was handled
	 * 
	 * @param fox
	 * @param command
	 * @return true if fox moved succesfully
	 */
	public void handleMove(Board board, int newX, int newY) {

		int currX = getXPos();
		int currY = getYPos();
		validateMove(board, newX, newY);

		board.removePiece(currX, currY);

		if (!isHead()) { //if it's the tail, pass the move job to the head
			if (isHorizontal()) {
				board.getPieceOnBoard(currX + 1, currY).handleMove(board, newX + 1, newY);
			} else {
				board.getPieceOnBoard(currX, currY + 1).handleMove(board, newX, newY + 1);
			}
		} else {
			if (!isHorizontal()) {
				board.removePiece(currX, currY - 1); // remove fox tail

				board.addPiece(this, newX, newY); // add head
				board.addPiece(getAssociatedPart(), newX, newY - 1); // add tail

			} else {

				board.removePiece(currX, currY); // remove head of fox
				board.removePiece(currX - 1, currY); // remove tail of fox

				board.addPiece(this, newX, currY); // add head of fox
				board.addPiece(getAssociatedPart(), newX - 1, currY); // add tail of fox
			}
		}
	}
}