/**
 * Change this class to animal
 * @author tomar
 *
 */
public class Animal {

	public AnimalEnum type;
	int x, y;
	
	public Animal(AnimalEnum type) {	
		this.type = type;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * this method will call validate rabbit move and validate fox move
	 * @param numOfSquares
	 * @return
	 */
	public boolean move() {
		//first check if it is rabbit or fox, then validate the move before moving it
		return false;
	}
	
	private boolean isRabbit() {
		return this.type.compareTo(AnimalEnum.R1) == 0 || 
				this.type.compareTo(AnimalEnum.R2) == 0 ||
						this.type.compareTo(AnimalEnum.R3) == 0;
	}
	
	private boolean isFox() {
		return this.type.compareTo(AnimalEnum.F1) ==  0 ||
				this.type.compareTo(AnimalEnum.F2) ==  0;
	}
	
	private boolean validateRabbitMove() {
		return false;
	}
	
	private boolean validateFoxMove() {
		return false;
	}
	
	public AnimalEnum getAnimalType() {
		return type;
	}
	
	public String toString() {
		return type.toString();
	}
	
	
}
