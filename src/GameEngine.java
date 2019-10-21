import java.util.HashMap;
import java.util.Scanner;


/**
 * Puts board and pieces together. Gets and validates moves. BIG NOTE: X values
 * correspond to the row. Y values correspond to the column.
 * 
 * @author Mariam Almalki
 *
 */
public class GameEngine {

	private Board board;
	private Animal rabbit1;
	private Animal rabbit2;
	private Animal rabbit3;
	private Animal fox1;
	private Animal fox2;

	private static final int NUM_TO_WIN = 3;

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
		board.getSquare(1, 1).addAnimal(fox1); // head
		board.getSquare(3, 3).addAnimal(fox2);
		board.getSquare(3, 4).addAnimal(fox2); // head
		board.getSquare(0, 3).addAnimal(rabbit1);
		board.getSquare(2, 4).addAnimal(rabbit2);
		board.getSquare(4, 1).addAnimal(rabbit3);

		rabbitsInHoles = 0;

	}

	public boolean hasWon() {
		return rabbitsInHoles == 3;
	}

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

	// TODO: Store results in global variables so that they can be used in the move
	// method
	public int getPositiontoGo() {
		Scanner scanner = new Scanner(System.in);
		int newLocation;
		System.out.println("Where would you like to move it? Enter position as RowCol ");
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
			if (currX == newX) { // moving horizontally - now check if it is right or left
				if (currY == newY) {
					invalidDirectionMessage();
				} else if (currY < newY) {
					return Direction.RIGHT;

				} else {
					return Direction.LEFT;
				}
			} else if (currY == newY) { // moving vertically
				if (currX == newX) {
					invalidDirectionMessage();
				} else if (currX < newX) {
					return Direction.DOWN;
				} else {
					return Direction.UP;
				}
			} else {
				invalidDirectionMessage();
			}
		}

		return d;
	}

	private void invalidDirectionMessage() {
		System.out.println("Invalid destination. Please try again.");
		this.startNewRound();
	}

	/**
	 * Make sure that the fox's move is valid, and if yes then move it.
	 * 
	 * @param animal the fox to move
	 * @param newX   the new X position (row) to go to
	 * @param newY   the new Y position (column) to go to
	 * @return
	 */
	private void validateFoxMove(Animal animal, int newX, int newY) {
		int currX = animal.getXPosition();
		int currY = animal.getYPosition();

		Direction d = getDirection(currX, currY, newX, newY);

		// If it is fox1
		if (animal.type.compareTo(AnimalEnum.F1) == 0) {
			this.handleFox1Move(animal, currX, currY, newX, d);
		}

		// If it is fox2
		if (animal.type.compareTo(AnimalEnum.F2) == 0) {
			this.handleFox2Move(animal, currY, currX, newY, d);
		}
	}

	private void handleFox1Move(Animal animal, int currX, int currY, int newX, Direction d) {
		// Ensure that the player isn't asking us to move it horizontally
		if (d.compareTo(Direction.RIGHT) == 0 || d.compareTo(Direction.LEFT) == 0) {
			invalidDirectionMessage();
		}

		if (d.compareTo(Direction.UP) == 0) {
			int headPos = currX - 1;
			// Make sure that every square in between is empty before moving the fox
			for (int i = newX; i < headPos; i++) {
				if (!(board.getSquare(i, currY).isEmpty())) {
					invalidDirectionMessage();
				}
			}

			board.getSquare(newX, currY).addAnimal(animal); // fox's tail
			board.getSquare(newX + 1, currY).addAnimal(animal); // fox's head. This will be set as f1's position

		} else if (d.compareTo(Direction.DOWN) == 0) { // otherwise the fox is sliding down

			for (int i = currX + 1; i <= newX; i++) {
				if (!(board.getSquare(i, currY).isEmpty())) {
					invalidDirectionMessage();
				}
			}
			board.getSquare(newX - 1, currY).addAnimal(animal); // fox's tail
			board.getSquare(newX, currY).addAnimal(animal); // fox's head. This will be set as f1's position
		}
		board.getSquare(currX, currY).removeAnimal();
		board.getSquare(currX - 1, currY).removeAnimal();
	}

	private void handleFox2Move(Animal animal, int currY, int currX, int newY, Direction d) {

		// Ensure that the player isn't asking us to move it vertically
		if (d.compareTo(Direction.UP) == 0 || d.compareTo(Direction.DOWN) == 0) {
			invalidDirectionMessage();
		}

		if (d.compareTo(Direction.LEFT) == 0) {
			int headPos = currY - 1;
			// Make sure that every square in between is empty before moving the fox
			for (int i = newY; i < headPos; i++) {
				if (!(board.getSquare(currX, i).isEmpty())) {
					invalidDirectionMessage();
				}
			}

			board.getSquare(currX, newY).addAnimal(animal); // fox's tail
			board.getSquare(currX, newY + 1).addAnimal(animal); // fox's head. This will be set as f1's position

		} else if (d.compareTo(Direction.RIGHT) == 0) { // otherwise the fox is sliding down

			for (int i = currY + 1; i <= newY; i++) {
				if (!(board.getSquare(currX, i).isEmpty())) {
					invalidDirectionMessage();
				}
			}
			board.getSquare(currX, newY - 1).addAnimal(animal); // fox's tail
			board.getSquare(currX, newY).addAnimal(animal); // fox's head. This will be set as f1's position
		}
		board.getSquare(currX, currY).removeAnimal();
		board.getSquare(currX - 1, currY).removeAnimal();
	}

	private void handleRabbitMove(Animal animal, int newX, int newY, int currX, int currY) {
		
		//Check if the rabbit is currently in a hole
		if (board.getSquare(currX, currY).isHole()) {
			this.rabbitsInHoles--;
		}
		
		
		
		//If new position is a hole
		if (board.getSquare(newX, newY).isHole()) {
			this.rabbitsInHoles++;
			System.out.println("You got a rabbit in a hole!");
		}
		
		//Remove the animal from it's current position
		board.getSquare(currX, currY).removeAnimal();
		board.getSquare(newX, newY).addAnimal(animal);
	}

	private void validateRabbitMove(Animal animal, int newX, int newY) {
		int currX = animal.getXPosition();
		int currY = animal.getYPosition();

		// If destination is already filled
		if (board.getSquare(newX, newY).hasAnimal() || board.getSquare(newX, newY).hasMushroom()) {
			this.invalidDirectionMessage();
		}
		
		// If destination is empty, check if the path rabbit takes is full of obstacles
		else {
			// Getting the proposed direction
			Direction d = getDirection(currX, currY, newX, newY);
			// Checking of the paths are filled for each direction
			if (d.compareTo(Direction.UP) == 0) {
				for (int k = currY; k < newY; k++) {
					if (board.getSquare(currX, k).isEmpty()
							|| (board.getSquare(currX, k).isHole() && !board.getSquare(currX, k).hasAnimal())) {
						this.invalidDirectionMessage();
					} 
				}
			} else if (d == Direction.DOWN) {
				for (int k = currY; k > newY; k--) {
					if (board.getSquare(currX, k).isEmpty() || 
							(board.getSquare(currX, k).isHole() && !board.getSquare(currX, k).hasAnimal())) {
						this.invalidDirectionMessage();
					}
				}
			} else if (d == Direction.LEFT) {
				for (int k = currX; k > newX; k--) {
					if (board.getSquare(k, currY).isEmpty() ||
							board.getSquare(k, currY).isHole() && !board.getSquare(k, currY).hasAnimal()){
						this.invalidDirectionMessage();
					} 
				}
			} else if (d == Direction.RIGHT) {
				for (int k = currX; k < newX; k++) {
					if (board.getSquare(k, currY).isEmpty() || 
							board.getSquare(k, currY).isHole() && !board.getSquare(k, currY).hasAnimal()) {
						this.invalidDirectionMessage();
					} 
				}
			}
		}

		this.handleRabbitMove(animal, newX, newY, currX, currY);
	}

	public void startNewRound() {
		this.board.printBoard();
		this.moveAnimal(this.getAnimalToMove(), this.getPositiontoGo());
	}

	public void moveAnimal(Animal animal, int xy) {

		int newX = xy / 10; // x is j and y is i in board[i][j]
		int newY = xy % 10;

		if (animal.isFox()) {
			validateFoxMove(animal, newX, newY);
		} else if (animal.isRabbit()) {
			validateRabbitMove(animal, newX, newY);
		}

	}

	public void printGameInstructions() {
		// TODO: implement method and call it at beginning of game
		// Get Abdulla to do this
	}

	// put board and pieces together
	// validate moves
	public static void main(String[] args) {

		GameEngine newGame = new GameEngine();

		newGame.startNewRound();

		while (!newGame.hasWon()) {
			newGame.startNewRound();
		}
	}
}
