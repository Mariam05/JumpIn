/**
* This class helps the Board class to create it's (5 * 5) 
* board grid formed of squares. 
* In addition, identifies what is on the square and 
* if the square is empty or occupied by animal or an object
* @author Taher Shabaan
*/
public class Square {

	public enum squareType {
		PIECE, EMPTY, HOLE
	}; // the reason mushrooms and animals are both piece is so that it's easier to validate moves

	private int x, y; //the position of the square on the board. Not entirely necessary to have but kept preventatively.
	private Animal p;
	private boolean hasAnimal; //whether an animal is occupying the square
	private boolean hasMushroom; //whether a mushroom is occupying a square
	private boolean isHole; //Whether there is a hole in the square

	private squareType type; //The type of square, as defined by the enums above. 

	/**
	 * The Constructor will create an empty square and specify its position by using
	 * i and j that the user will pass.
	 * Also, set isHole and isOccupied to false
	 * @param i, j the position of the square
	 */
	public Square(int i, int j) {
		this.x = i;
		this.y = j;
		hasMushroom = false;
		hasAnimal = false;
		type = squareType.EMPTY;
	}

	/**
	 * This method Will add animal p on the square
	 * @param p the Animal object to add
	 */
	public void addAnimal(Animal p) {
		this.p = p;
		p.setPosition(x, y);
		type = squareType.PIECE;
		hasAnimal = true;
	}

	/**
	 * This method will remove the animal which is located on the square
	 * by setting hasAnimal to false
	 */
	public void removeAnimal() {
		if (hasAnimal)
			hasAnimal = false;

		if (isHole) {
			type = squareType.HOLE;
		} else {
			type = squareType.EMPTY;
		}

	}

	/**
	 * This method will check if the square is occupied by animal or not
	 * @return true if it has an animal
	 */
	public boolean hasAnimal() {
		return hasAnimal;
	}

	/**
	 * This method will check if the square is occupied by mushroom or not
	 * @return true if there is a mushroom
	 */
	public boolean hasMushroom() {
		return hasMushroom;
	}

	/**
	 * @return the type of (object or animal) that occupies the square
	 */
	public squareType getSquareType() {
		return type;
	}

	/**
	 * This method will return the animal that of occupies the square
	 * if no animal occupies the square then it'll return null
	 * @return the animal on the square
	 */
	public Animal getAnimal() {
		if (hasAnimal) {
			return p;
		}
		return null;
	}

	/**
	 * This method will add the mushroom on the square by
	 * changing variable "type" to be equal to squareType.HOLE
	 */
	public void setHole() {
		type = squareType.HOLE;
		isHole = true;
	}

	/**
	 * This method will check if the square is occupied by hole or not
	 */
	public boolean isHole() {
		return isHole;
	}

	/**
	 * This method will check if the square is empty or not
	 */
	public boolean isEmpty() {
		if (type.compareTo(squareType.EMPTY) == 0)
			return true;
		return false;
	}

	/**
	 * This method will add a mushroom on the square by
	 * changing variable "type" to be equal to squareType.PIECE
	 */
	public void setMushroom() {
		hasMushroom = true;
		type = squareType.PIECE;
	}
	
	/**
	 * @return This method will return the x value of the square
	 */
	public int getXPosition() {
		return x;
	}

	/**
	 * @return This method will return the Y value of the square
	 */
	public int getYPosition() {
		return y;
	}

	/**
	 * This method will print what is on the square. 
	 * If a rabbit is in a hole then it will have an asterisk beside it. 
	 * @return the string representation of teh square
	 */
	public String toString() {

		String squareString = "";

		switch (type) {
		case EMPTY:
			squareString = "|     ";
			break;
		case PIECE:
			if (hasMushroom) {
				squareString = "| MS  ";
			} else if (isHole) {
				squareString = "| " + p.toString() + "* "; //asterisk means that it's in a hole
			} else {
				squareString = "| " + p.toString() + "  ";
			}
			break;
		case HOLE:
			squareString = "|  O  ";
			break;
		default:
			squareString = "|     ";
		}

		return squareString;
	}

}
