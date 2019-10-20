import java.util.Scanner;

/**
 * Puts board and pieces together
 * Gets and validates moves
 * 
 * @author tomar
 *
 */
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
		this.board = new Board();
		
		//instantiate pieces
		rabbit1 = new Animal(AnimalEnum.R1);
		rabbit2 = new Animal(AnimalEnum.R2);
		rabbit3 = new Animal(AnimalEnum.R3);
		
		fox1 = new Animal(AnimalEnum.F1);
		fox2 = new Animal(AnimalEnum.F2);
		
		//Add pieces to board. Fox positions are specified by their head
		board.getSquare(1, 1).addAnimal(fox1); 
		board.getSquare(0, 1).addAnimal(fox1); 
		board.getSquare(3, 3).addAnimal(fox2);
		board.getSquare(3, 4).addAnimal(fox2);
		board.getSquare(0, 3).addAnimal(rabbit1);
		board.getSquare(2, 4).addAnimal(rabbit2);
		board.getSquare(4, 1).addAnimal(rabbit3);
		
		rabbitsInHoles = 0;
		
	}
	
	public boolean hasWon() {
		return rabbitsInHoles == 3;
	}
	
	public int getCurrPositiontoMove() {
		Scanner scanner = new Scanner(System.in);
		int currentLocation;
		System.out.println("What piece would you like to move? Enter position as xy ");
		try { 
			currentLocation = scanner.nextInt();
			if (currentLocation / 100 != 0) { //Error handling: if more than 2 digits entered
				System.out.println("Incorrect input. Try again.");
				return getCurrPositiontoMove();
			}
		} catch (Exception e) {
			System.out.println("Incorrect input. Try again.");
			return getCurrPositiontoMove();
		}
		
		return currentLocation;
	}
	
	public int getPositiontoGo() {
		Scanner scanner = new Scanner(System.in);
		int newLocation;
		System.out.println("Where would you like to move it? Enter position as xy ");
		try { 
			newLocation = scanner.nextInt(); //Error handling: if more than 2 digits entered
			if (newLocation / 100 != 0) {
				System.out.println("Incorrect input. Try again.");
				return getPositiontoGo();
			}
		} catch (Exception e) {
			System.out.println("Incorrect input. Try again.");
			return getPositiontoGo();
		}
		
		return newLocation;
	}
	
	public void startNewRound() {
		this.board.printBoard();
		this.getCurrPositiontoMove();
		this.getPositiontoGo();
	}
	
	
	public void printGameInstructions() {
		//TODO: implement method and call it at beginning of game
	}
	
	//put board and pieces together
	//validate moves
	public static void main(String[] args) {
		
		GameEngine newGame = new GameEngine();
		
		newGame.startNewRound();
		int pos = newGame.getCurrPositiontoMove();
		int currX = pos % 10; //x is j and y is i in board[i][j]
		int currY = pos / 10;
		
		System.out.println("X: " + currX + "Y: " + currY);

	}
}
