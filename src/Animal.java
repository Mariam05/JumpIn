/**
 * This class is used to represent an animal on the board (in this case, either a rabbit or fox)
 * @author Hassan 
 *
 */
public class Animal {

	/**
	 * The type of animal is used to describe the specific animal piece (F1, F2, R1, etc.)
	 */
	public AnimalEnum type;
	
	/**
	 * Where the animal is located on the board
	 */
	int x, y;
	
	/**
	 * Constructs a new animal object of a specified type
	 * @param type
	 */
	public Animal(AnimalEnum type) {	
		this.type = type;
	}
	
	/**
	 * Sets the position of the animal if it is on the board
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
			
	/**
	 * Determines whether the animal is a rabbit
	 * @return true if it is a rabbit
	 */
	public boolean isRabbit() {
		return this.type.compareTo(AnimalEnum.R1) == 0 || 
				this.type.compareTo(AnimalEnum.R2) == 0 ||
						this.type.compareTo(AnimalEnum.R3) == 0;
	}
	
	/**
	 * Determines whether the animal is a fox
	 * @return ture if it is a fox
	 */
	public boolean isFox() {
		return this.type.compareTo(AnimalEnum.F1) ==  0 ||
				this.type.compareTo(AnimalEnum.F2) ==  0;
	}
	
	/**
	 * @return the animal's current row position on the board
	 */
	public int getXPosition() {
		return x;
	}
	
	/**
	 * @return the animal's current column position on the board
	 */
	public int getYPosition() {
		return y;
	}
	
	/**
	 * @return what type the animal is
	 */
	public AnimalEnum getAnimalType() {
		return type;
	}
	
	/**
	 * Get a string representation of which animal it is
	 */
	public String toString() {
		return type.toString();
	}
	
	
}
