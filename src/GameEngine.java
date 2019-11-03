import java.util.HashMap;
import java.util.Scanner;

/**
 * Assembles board and pieces together. Gets and validates moves. 
 * 
 * BIG NOTE: X values correspond to the row. Y values correspond to the column.
 * 
 * @author Mariam Almalki, Nazifa Tanzim
 *
 */
public class GameEngine {

	//The board and animal objects
	private Board board;
	private Rabbit rabbit1, rabbit2, rabbit3;
	private Fox fox1, fox2;

	private static final int NUM_TO_WIN = 3; //The number of rabbits in the game needed to go in a hole

	/**
	 * Maps the animal with their type, ensures there isn't more than one animal per type
	 */
	private HashMap<AnimalEnum, Piece> animals; 

	/**
	 * The number of rabbits currently in a hole
	 */
	private int rabbitsInHoles; 

	/**
	 * Assemble the board with the pieces
	 */
	public GameEngine() {
		this.board = new Board();
		animals = new HashMap<>();

		// instantiate pieces
		rabbit1 = new Rabbit("R1");
		rabbit2 = new Rabbit("R2");
		rabbit3 = new Rabbit("R3");

		fox1 = new Fox("F1");
		fox2 = new Fox("F2");

		// Storing pieces in a hash map
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

	/**
	 * Checks if the player has won based on the number of rabbits in a hole
	 * @return true if the player has won
	 */
	public boolean hasWon() {
		return rabbitsInHoles == NUM_TO_WIN;
	}

	/**
	 * Prompts the user to choose which animal they want to move
	 * @return the animal to move
	 */
	public Piece getAnimalToMove() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Which piece would you like to move? Enter the corresponding number: \n"
				+ "1 - R1\n2 - R2\n3 - R3\n4 - F1\n5 - F2");

		int piece;
		try {
			piece = scanner.nextInt();
			// Error handling: if invalid number:
			if (piece > 5 || piece < 1) {
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

	/**
	 * Prompts the user for the position they want the animal to go to
	 * @return an integer (RowCol) representing the position
	 */
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

	/**
	 * Re - prompts user for correct input if their move is invalid
	 */
	private void invalidDirectionMessage() {
		System.out.println("Invalid destination. Please try again.");
		this.startNewRound();
	}

	/**
	 * Prints the current board and prompts the user to move an animal
	 */
	public void startNewRound() {
		this.board.printBoard();
		this.moveAnimal(this.getAnimalToMove(), this.getPositiontoGo());
	}

	/**
	 * Will determine whether the animal chosen is a rabbit or fox and then move them accordingly
	 * @param animal the animal to move
	 * @param xy the destination to move them to as RowCol
	 */
	public void moveAnimal(Piece animal, int xy) {

		int newX = xy / 10; // x is j and y is i in board[i][j]
		int newY = xy % 10;

		if(animal.validateMove(this.board, animal, animal.getPosition(), xy)) {
			if(animal.getPieceType().compareTo(rabbit1.getPieceType()) == 0) {
				if(animal.isInHole()) {
					this.rabbitsInHoles++;
				}
				else {
					this.rabbitsInHoles--;
				}
			}
			this.startNewRound();
		}
		else {
			this.invalidDirectionMessage();
		}
	}

	/**
	 * Print out the game instructions at the beginning of the game
	 * @author Abdulla Al - wazzan
	 */
	public void printGameInstructions() {
		String title = "JumpIN Instructions: \n\n";
		String obstacles = "\tThe obstacles are: Mushroom, Fox, Rabbit, Hole.\n\n"
						 + "\tFoxes take up two spaces, head and tail. "
						 + "All other obstacles occupy one square.\n\n";
		
		String movements = "The following explains how the obstacles move around the board:\n\n"
				
						 + "\tRabbit:\n"
				
						 + "\t\tRabbits can only move by jumping over one adjacent obstacle, empty holes are NOT obstacles.\n"
						 + "\t\tOnce a rabbit is in a hole, it can be jumped over by other rabbits.\n"
						 + "\t\tSide note: Rabbits can jump out of their holes to faciliate another rabbit's path.\n\n"
						 + "\t\tRabbits can jump over a fox's waist, or from its head to tail or tail to head.\n\n"
						 
						 + "\tFoxes: \n"
						 
						 + "\t\tFoxes can slide depending on their initial direction, however many spots needed.\n\n"
						
						 + "\tMushrooms and holes are stationary.\n\n";
		
		String objective = "The objective of the game is to move the rabbits and foxes, through a series of movements\n"
				 		 + "around the obstacles untill all the rabbits are safely in their hole.\n";
		String abbreviations = "In this game, the following abbreviations are used to represent each object on the board: " +
				 		 "\n\tR1 - Rabbit 1\n\tR2 - Rabbit 2\n\tR3 - Rabbit 3\n\tF1 - Fox 1\n\tF2 - Fox 2\n\tMS - Mushroom\n\t O - hole \n\t" +
				 		 "When a rabbit has an asterisk beside it means that it is in a hole";
		
		
		System.out.println(title + obstacles + movements  + objective + abbreviations);
		
	}

	/**
	 * The client for the game. Creates a new game and keeps prompting player for input until they have won
	 * @param args
	 */
	public static void main(String[] args) {

		GameEngine newGame = new GameEngine();

		newGame.printGameInstructions();
		
		while (!newGame.hasWon()) {
			newGame.startNewRound();
		}
		
		System.out.println("GOOD JOB! YOU SOLVED THE PUZZLE!"); //make them feel good 
	}
}
