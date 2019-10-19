
public class Square {
	
	private enum squareType {PIECE, EMPTY, HOLE};
	private int x, y;
	private Animal p;
	private boolean hasAnimal;
	
	private squareType type;
	
	
	//do we need positions? Because they are technically determined by the array indices
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
		type = squareType.EMPTY;
	}
		
	public void addAnimal(Animal p) {
		this.p = p;
		p.setPosition(x, y);
		type = squareType.PIECE;
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
		type = squareType.PIECE;
	}
	
	public int getXPosition() {
		return x;
	}
	
	public int getYPosition() {
		return y;
	} 
	
	
}
