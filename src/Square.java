/**
 * 
 * @author Mariam Almalki
 *
 */
public class Square {
	
	public enum squareType {PIECE, EMPTY, HOLE}; //the reason mushrooms and animals are both piece is so that it's easier to validate moves
	private int x, y;
	private Animal p;
	private boolean hasAnimal;
	private boolean hasMushroom;
	private boolean isHole;
	
	private squareType type;
	
	public Square(int i, int j) {
		this.x = i;
		this.y = j;
		hasMushroom = false;
		hasAnimal = false;
		type = squareType.EMPTY;
	}
		
	public void addAnimal(Animal p) {
		this.p = p;
		p.setPosition(x, y);
		type = squareType.PIECE;
		hasAnimal = true;
	}
	
	public void removeAnimal() {
		if (hasAnimal) {
			hasAnimal = false;
		}
		
		type = squareType.EMPTY;
	}
	
	public boolean hasAnimal() {
		return hasAnimal;
	}
	
	public boolean hasMushroom() {
		return hasMushroom;
	}
	
	public squareType getSquareType() {
		return type;
	}
	
	//I don't like that this method returns null.. Think of another way to implement it @me
	public Animal getAnimal() {
		if (hasAnimal) {
			return p;
		}
		return null;
	}
	
	public void setHole() {
		type = squareType.HOLE;
		isHole = true;
	}
	
	public boolean isHole() {
		return isHole;
	}
	
	public void setMushroom() {
		hasMushroom = true;
		type = squareType.PIECE;
	}
	
	public int getXPosition() {
		return x;
	}
	
	public int getYPosition() {
		return y;
	} 
	
	public String toString() {
		
		String squareString = "";
		
		switch(type) {
		case EMPTY: 
			squareString = "|     "; 
			break;
		case HOLE: 
			squareString = "|  O  "; 
			break;
		case PIECE: 
			if(hasMushroom) {
				squareString = "| MS  ";
			} else {
			squareString = "| " + p.toString()+ "  ";
			}
			break;
		default: 
			squareString = "|     ";
		}
		
		return squareString;
	}
	
	
}
