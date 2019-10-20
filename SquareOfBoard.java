package milestone1;

/**
* This class helps the GameBoard class to create it's (5 * 5) 
* board formed of squares
* And output
* @version 8.9
* @author Taher Shabaan
*/

public class SquareOfBoard {
	
	private enum typeOfSquare {HOLE, EMPTY, MUSHROOM};
	private int xPosition, yPosition; // Position of a square
	private boolean isOccupied, isHole;
	private typeOfSquare squareType;
	private Animals animal;
	
	
	
	/*
	 * SquareOfBoard Constructor will set the square position by using the
	 * x-position and the y-position that the user will pass.
	 * Also, set isHole and isOccupied to false
	 * 
	 */
	public SquareOfBoard(int xPosition1, int yPosition1) {
		if(xPosition1 < 0 || yPosition1 < 0) {
			throw new IllegalArgumentException("Dimensions of a square can not be negative");
		}
		this.xPosition = xPosition1;
		this.yPosition = yPosition1;
		this.isOccupied = false;
		this.isHole = false;
	}
	/*
	 * Check if the square has animal or not
	 */
	public boolean hasAnimal() {
		if(animal != null) return true; return false;
	}
	/*
	 * Add animal either fox or rabbit on the square 
	 */
	public void addAnimal(Animals animal) {
		if(this.animal == null) this.animal=animal; return;
		//we also should set the position of the animal
	}
	
	public void setMushrooms() {
		if(this.squareType != typeOfSquare.EMPTY) {
			this.squareType = typeOfSquare.MUSHROOM;
		}else {
			return;
		}
	}	
	
	public boolean hasMushroom() {
		if(this.squareType == typeOfSquare.MUSHROOM) return true; return false;
	}
	
	public void digHole() {
		squareType = typeOfSquare.HOLE;
	}
	public boolean isHole() {
		if(this.isHole) return true; return false;
	}
	
	public int getXposition(){
		return this.xPosition;
	}
	
	/*
	 * This method will return the y-position of the square
	 */
	public int getYposition(){
		return this.yPosition;
	}
	
	/*
	 * This method will print the position of the square
	 *  and what it is occupied by
	 */
	public String toString() {
		return "The dimenesion of the square is "
				+ "(" +this.xPosition+","+this.yPosition +") "
				+ " and it's occupied by "; //+ this.obstacle;
	}
	
	/*
	 * This method will check if the square is occupied or not occupied.
	 */
	public boolean isEmpty() {
		if(squareType == typeOfSquare.EMPTY) return true; else return false;
	}
}

