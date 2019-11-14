package JumpIn;

import java.awt.Color;

/**
 * This class represents a Rabbit piece on the board. 
 * Rabbits can only jump over other objects so they cannot move directly
 * to the any of the squares beside them. 
 * @author Mariam Almalki
 *
 */
public class Rabbit extends Piece {

	/**
	 * The colour of the rabbit
	 */
	private Color colour;
	
	/**
	 * Create a new rabbit object. 
	 * The default colour is white
	 * @param pieceName
	 */
	public Rabbit(String pieceName) {
		this(pieceName, Color.WHITE); //Default colour is white
	}
	
	/**
	 * Create a rabbit object but specify colour
	 * @param pieceName
	 * @param colour
	 */
	public Rabbit(String pieceName, Color colour) {
		super(pieceName, Type.RABBIT);
		this.colour = colour; 
	}

	/**
	 * Set the colour of the rabbit
	 * @param colour Color object
	 */
	public void setColour(Color colour) {
		this.colour = colour;
	}
	
	/**
	 * Get the colour of the rabbit
	 * @return Color colour of rabbit
	 */
	public Color getColour() {
		return colour;
	}
	
	/**
	 * Handle a move of a rabbit
	 * 
	 * @param rabbit
	 * @param command
	 * @return
	 */
	public boolean handleMove(Board board, int newX, int newY) {
		int currX = getXPos();
		int currY = getYPos();

		if (!validateMove(newX, newY)) {
			return false;
		}

		// If destination is already filled
		if (board.getSquare(newX, newY).hasPiece())
			return false;

		// Checking of the paths are filled for each direction
		if (currX < newX) { // moving right
			for (int k = currX + 1; k < newX; k++) { // check for empty squares
				if (!(board.hasPiece(k, currY)))
					return false;
			}
		} else if (currX > newX) { // moving left
			for (int k = currX - 1; k > newX; k--) { // check for empty squares
				if (!(board.hasPiece(k, currY)))
					return false;
			}
		} else if (currY > newY) { // moving up
			for (int k = currY - 1; k > newY; k--) { // check for empty squares
				if (!(board.hasPiece(currX, k)))
					return false;
			}
		} else if (currY < newY) { // moving down
			for (int k = currY + 1; k < newY; k++) { // check for empty squares
				if (!(board.hasPiece(currX, k)))
					return false;
			}

		}

		// Move is validated, complete the action
		board.removePiece(currX, currY);
		board.addPiece(this, newX, newY);

		if (board.isHole(currX, currY)) {
			board.decreaseNumRabbitsInHoles(); // if the rabbit was in a hole and now is not
		}
		if (board.isHole(newX, newY)) {
			board.increaseNumRabbitsInHoles(); // if rabbit entered a hole
		}

		return true;
	}
	
	/**
	 * Check if a rabbit's move is valid. 
	 * An invalid move is if it is simply moving to one of the squares beside it
	 */
	public boolean validateMove(int x, int y) {
		int currX = getXPos();
		int currY = getYPos();
		
		if ((Math.abs(currX - x) < 2) && currY == y) return false; //moving one square horizontally
		if ((Math.abs(currY - y) < 2) && currX == x) return false; //moving one square vertically
		if (!((currX != x && currY == y) || (currX == x && currY != y))) return false; //moving vertically
		
		return true;
	}
}