/*
 * Each square has a type: piece, whole, or empty
 */
public class Square {
	
	private enum squareType {PIECE, EMPTY, HOLE};
	private int x, y;
	private Animal p;
	private boolean hasAnimal;
	private boolean hasMushroom;
	
	private squareType type;
	
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
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
	}
	
	public void setHole() {
		type = squareType.HOLE;
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
