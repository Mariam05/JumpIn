
public class GameEngine {

	
	private Board board;
	private Animal rabbit1;
	private Animal rabbit2;
	private Animal rabbit3;
	private Animal fox1;
	private Animal fox2;
	
	private int rabbitsInHoles;
	
	/**
	 * Assemble the board with the pieces
	 */
	public GameEngine() {
		board = new Board();
		
		//instantiate pieces
		rabbit1 = new Animal(AnimalEnum.R1);
		rabbit2 = new Animal(AnimalEnum.R2);
		rabbit3 = new Animal(AnimalEnum.R3);
		
		fox1 = new Animal(AnimalEnum.F1);
		fox2 = new Animal(AnimalEnum.F2);
		
		//Add pieces to board. Fox positions are specified by their head
		board.getSquare(1, 1).addAnimal(fox1); 
		board.getSquare(0, 1).addAnimal(fox1); 
		board.getSquare(3, 4).addAnimal(fox2);
		board.getSquare(3, 4).addAnimal(rabbit1);
		board.getSquare(2, 4).addAnimal(rabbit2);
		board.getSquare(2, 4).addAnimal(rabbit3);
		
		rabbitsInHoles = 0;
		
	}
	
	private boolean hasWon() {
		return rabbitsInHoles == 3;
	}
	
	//put board and pieces together
	//validate moves
	public static void main(String[] args) {
		
		Board board = new Board();
		board.printBoard();
		
		
	}
}
