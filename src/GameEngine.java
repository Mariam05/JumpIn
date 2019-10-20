import java.util.HashMap;
import java.util.Scanner;

/**
 * Puts board and pieces together Gets and validates moves
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

	private enum Direction {
		UP, DOWN, RIGHT, LEFT, INVALID
	};

	private HashMap<AnimalEnum, Animal> animals;

	private int rabbitsInHoles;

	/**
	 * Assemble the board with the pieces
	 */
	public GameEngine() {
		this.board = new Board();
		animals = new HashMap<>();

		// instantiate pieces
		rabbit1 = new Animal(AnimalEnum.R1);
		rabbit2 = new Animal(AnimalEnum.R2);
		rabbit3 = new Animal(AnimalEnum.R3);

		fox1 = new Animal(AnimalEnum.F1);
		fox2 = new Animal(AnimalEnum.F2);

		animals.put(AnimalEnum.R1, rabbit1);
		animals.put(AnimalEnum.R2, rabbit2);
		animals.put(AnimalEnum.R3, rabbit3);
		animals.put(AnimalEnum.F1, fox1);
		animals.put(AnimalEnum.F2, fox2);

		// Add pieces to board. Fox positions are specified by their head
		board.getSquare(0, 1).addAnimal(fox1);
		board.getSquare(1, 1).addAnimal(fox1);
		board.getSquare(3, 4).addAnimal(fox2);
		board.getSquare(3, 3).addAnimal(fox2);
		board.getSquare(0, 3).addAnimal(rabbit1);
		board.getSquare(2, 4).addAnimal(rabbit2);
		board.getSquare(4, 1).addAnimal(rabbit3);

		rabbitsInHoles = 0;

	}

	public boolean hasWon() {
		return rabbitsInHoles == 3;
	}

//	public int getCurrPositiontoMove() {
//		Scanner scanner = new Scanner(System.in);
//		int currentLocation;
//		System.out.println("What piece would you like to move? Enter position as xy ");
//		try { 
//			currentLocation = scanner.nextInt();
//			//Error handling: if more than 2 digits entered or incorrect index
//			if (currentLocation / 100 != 0 || currentLocation%10 > 4 || 
//					currentLocation % 10 < 0 || currentLocation / 10 < 0 || currentLocation / 10 > 4 ) { 
//				System.out.println("Incorrect input. Try again.");
//				return getCurrPositiontoMove();
//			}
//		} catch (Exception e) {
//			System.out.println("Incorrect input. Try again.");
//			return getCurrPositiontoMove();
//		}
//		
//		return currentLocation;
//	}

	public Animal getAnimalToMove() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Which piece would you like to move? Enter the corresponding number: \n"
				+ "1 - R1\n2 - R2\n3 - R3\n4 - F1\n5 - F2");

		int piece;
		try {
			piece = scanner.nextInt();
			// Error handling: if invalid number:
			if (piece > 5 || piece < 0) {
				System.out.println("Incorrect input. Try again.");
				return getAnimalToMove();
			}

			for (AnimalEnum a : animals.keySet()) {
				if (a.ordinal() == piece - 1) {
					return animals.get(a); // pass new coordinates in move()
				}
			}

		} catch (Exception e) {
			System.out.println("Incorrect input. Try again.");
			return getAnimalToMove();
		}

		return null; // Can we prevent this?
	}

	// TODO: Store results in global vars so that they can be used in the move
	// method
	public int getPositiontoGo() {
		Scanner scanner = new Scanner(System.in);
		int newLocation;
		System.out.println("Where would you like to move it? Enter position as xy ");
		try {
			newLocation = scanner.nextInt();
			// Error handling: if more than 2 digits entered
			if (newLocation / 100 != 0 || newLocation % 10 > 4 || newLocation % 10 < 0 || newLocation / 10 < 0
					|| newLocation / 10 > 4) {
				System.out.println("Incorrect input. Try again.");
				return getPositiontoGo();
			}

		} catch (Exception e) {
			System.out.println("Incorrect input. Try again.");
			return getPositiontoGo();
		}

		return newLocation;
	}
	
	private Direction getDirection(int currX, int currY, int newX, int newY) {
		Direction d = Direction.INVALID;

		boolean valid = false;
		while (!valid) {
			// How are we getting the animal's position
			if (currX == newX) { // moving vertically - now check if it up or down
				if (currY == newY) {
					System.out.println("You're already here. Please enter another position.");
					this.getPositiontoGo();
				} else if (currY < newY) {
					return Direction.DOWN;
		
				} else {
					return Direction.UP;
					//valid = true;
				}
			} else if (currY == newY) { // moving horizontally
				if (currX == newX) {
					System.out.println("You're already here. Please enter another position.");
					this.getPositiontoGo();
				} else if (currX < newX) {
					// moving right
					return Direction.RIGHT;
					//valid = true;
				} else {
					// moving left
					return Direction.LEFT;
					//valid = true;
				}
			} else {
				System.out.println("Invalid direction. Please enter another position.");
				this.getPositiontoGo();
			}
		}
		
		return d;
	}

	private boolean validateFoxMove(Animal animal, int newX, int newY) {
		int currX = animal.getXPosition();
		int currY = animal.getYPosition();
		
		Direction d = getDirection(currX, currY, newX, newY);

		return false;
	}

	private boolean validateRabbitMove(Animal animal, int newX, int newY) {
		int currX = animal.getXPosition();
		int currY = animal.getYPosition();

		Direction d = getDirection(currX, currY, newX, newY);
		
		return false;
	}

	public void startNewRound() {
		this.board.printBoard();
	}

	public void moveAnimal(Animal animal, int xy) {

		int newX = xy % 10; // x is j and y is i in board[i][j]
		int newY = xy / 10;

		if (animal.isFox()) {
			validateFoxMove(animal, newX, newY);
		} else if (animal.isRabbit()) {
			validateRabbitMove(animal, newX, newY);
		}

	}

	public void printGameInstructions() {
		// TODO: implement method and call it at beginning of game
	}

	// put board and pieces together
	// validate moves
	public static void main(String[] args) {

		GameEngine newGame = new GameEngine();

		newGame.startNewRound();
		newGame.moveAnimal(newGame.getAnimalToMove(), newGame.getPositiontoGo());
	}
}
