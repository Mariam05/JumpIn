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
	 * Check if a rabbit's move is valid. 
	 * An invalid move is if it is simply moving to one of the squares beside it
	 */
	@Override
	public boolean validateMove(int x, int y) {
		int currX = getXPos();
		int currY = getYPos();
		
		if ((Math.abs(currX - x) < 2) && currY == y) return false; //moving one square horizontally
		if ((Math.abs(currY - y) < 2) && currX == x) return false; //moving one square vertically
		if (!((currX != x && currY == y) || (currX == x && currY != y))) return false; //moving vertically
		
		return true;
	}
}