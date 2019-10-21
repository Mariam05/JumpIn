/**
 * 
 * @author Mariam Almalki
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
			
	public boolean isRabbit() {
		return this.type.compareTo(AnimalEnum.R1) == 0 || 
				this.type.compareTo(AnimalEnum.R2) == 0 ||
						this.type.compareTo(AnimalEnum.R3) == 0;
	}
	
	public int getXPosition() {
		return x;
	}
	
	public int getYPosition() {
		return y;
	}
	
	public boolean isFox() {
		return this.type.compareTo(AnimalEnum.F1) ==  0 ||
				this.type.compareTo(AnimalEnum.F2) ==  0;
	}
	

	
	public AnimalEnum getAnimalType() {
		return type;
	}
	
	public String toString() {
		return type.toString();
	}
	
	
}
