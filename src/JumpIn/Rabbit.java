package JumpIn;

import java.awt.Color;
import java.io.Serializable;
import java.util.Scanner;

/**
 * This class represents a Rabbit piece on the board. Rabbits can only jump over
 * other objects so they cannot move directly to the any of the squares beside
 * them.
 * 
 * @author Mariam Almalki
 *
 */
public class Rabbit extends Piece implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The colour of the rabbit
	 */
	private Color colour;

	private boolean inHole;

	/**
	 * Create a new rabbit object. The default colour is white
	 * 
	 * @param pieceName
	 */
	public Rabbit(String pieceName) {
		this(pieceName, Color.WHITE); // Default colour is white
	}

	/**
	 * Create a rabbit object but specify colour
	 * 
	 * @param pieceName
	 * @param colour
	 */
	public Rabbit(String pieceName, Color colour) {
		super(pieceName);
		this.colour = colour;
		inHole = false;
	}

	public boolean isInHole() {
		return inHole;
	}
	
	public void setInHole(boolean b) {
		inHole = b;
	}
	
	/**
	 * Set the colour of the rabbit
	 * 
	 * @param colour Color object
	 */
	public void setColour(Color colour) {
		this.colour = colour;
	}

	/**
	 * Get the colour of the rabbit
	 * 
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
	public void handleMove(Board board, int newX, int newY) {
		int currX = getXPos();
		int currY = getYPos();
		

		// Move is validated, complete the action
		board.removePiece(currX, currY);

		if (board.isHole(currX, currY)) {
			inHole = false; // if the rabbit was in a hole and now is not
		}

		board.addPiece(this, newX, newY);
		setPosition(newX, newY);
	}

	/**
	 * Check if a rabbit's move is valid.
	 */
	public boolean validateMove(Board board, int newX, int newY) {

		int currX = getXPos();
		int currY = getYPos();

		if ((Math.abs(currX - newX) <= 1) && currY == newY)
			return false; // moving one square horizontally
		if ((Math.abs(currY - newY) <= 1) && currX == newX)
			return false; // moving one square vertically
		if (!((currX != newX && currY == newY) || (currX == newX && currY != newY)))
			return false; // moving diagonally

		// If destination is already filled
		if (board.hasPiece(newX, newY))
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

		return true;
	}

	@Override
	public Piece manufacturePiece(String str) {
		Scanner dscanner = new Scanner(str).useDelimiter("\\s*,\\s*");

		String name = dscanner.next();
		int xPos = dscanner.nextInt();
		int yPos = dscanner.nextInt();
		
		dscanner.close();
		
		Rabbit newRabbit = new Rabbit(name);
		newRabbit.setPosition(xPos, yPos);
		
		return newRabbit;
	}

	/**
	 * Get the string representation of the rabbit in the format of: name, colour, x
	 * pos, y pos
	 */
	@Override
	public String getStringRepresentation() {
		return toString() + ","  + getXPos() + "," + getYPos();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Rabbit other = (Rabbit) obj;

		return getStringRepresentation().equals(other.getStringRepresentation());
	}


}