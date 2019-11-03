import java.util.HashMap;

/**
 * This is the main class for the JumpIn came. Acts as the controller
 * 
 * @author Mariam Almalki
 * @version 2.0 of JumpIn
 *
 */
public class Game {

	private Parser parser;
	private CommandWord commandWords;
	private Board board;
	private Piece fox1, fox2, mushroom1, mushroom2, rabbit1, rabbit2, rabbit3;
	private HashMap<String, Piece> animalPieces;
	private int rabbitsInHoles;
	private boolean quitGame;

	/**
	 * Instantiate the parser and commandWords objects. Set up the board with the
	 * pieces
	 */
	public Game() {
		parser = new Parser();
		commandWords = new CommandWord();
		animalPieces = new HashMap<>();
		board = new Board();
		quitGame = false;

		// Instantiate the pieces on the board
		fox1 = new Fox("F1", Fox.FoxType.HORIZONTAL);
		fox2 = new Fox("F2", Fox.FoxType.VERTICAL);
		mushroom1 = new Mushroom("MS");
		mushroom2 = new Mushroom("MS");
		rabbit1 = new Rabbit("R1");
		rabbit2 = new Rabbit("R2");
		rabbit3 = new Rabbit("R3");

		// Add the pieces to the piece hashmap
		animalPieces.put(fox1.toString(), fox1);
		animalPieces.put(fox2.toString(), fox2);
		animalPieces.put(rabbit1.toString(), rabbit1);
		animalPieces.put(rabbit2.toString(), rabbit2);
		animalPieces.put(rabbit3.toString(), rabbit3);

		// Add the pieces to the board. Coordinates are row col
		//Note: Fox positions are now referred to by their tails
		board.addPiece(fox1, 4, 3);
		board.addPiece(fox1, ((Fox) fox1 ).getTailXPos(), ((Fox) fox1 ).getTailYPos()); //add fox1 tail
		
		board.addPiece(fox2, 1, 1);
		board.addPiece(fox2, ((Fox) fox2 ).getTailXPos(), ((Fox) fox2 ).getTailYPos()); //add fox2 tail
		
		board.addPiece(rabbit1, 3, 0);
		board.addPiece(rabbit2, 4, 2);
		board.addPiece(rabbit3, 1, 4);
		
		board.addPiece(mushroom1, 2, 4);
		board.addPiece(mushroom2, 3, 1);

	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void startNewRound() {

		board.printBoard();
		Command command = parser.getCommand();
		processCommand(command);

	}

	/**
	 * Re - prompts user for correct input if their move is invalid
	 */
	private void invalidCommandMessage() {
		System.out.println("Invalid command. Please try again.");
		this.startNewRound();
	}

	/**
	 * Given a command, process (that is: execute) the command.
	 * 
	 * @param command The command to be processed.
	 * @return false If the command ends the game, true otherwise.
	 */
	public void processCommand(Command command) {

		if (command.isUnknown()) {
			invalidCommandMessage();
		}

		String commandWord = command.getCommandWord();
		if (commandWord.equalsIgnoreCase("help")) {
			printGameInstructions();
		} else if (commandWord.equalsIgnoreCase("move")) {
			if (!validatePieceSelected(command))
				invalidCommandMessage(); // check if selected piece is valid

			handleMove(command);
		} else if (commandWord.equals("quit")) {
			quitGame = true;
		} else {
			invalidCommandMessage();
		}
	}

	/**
	 * Checks if the user entered a valid piece to move
	 * 
	 * @param command the command that the user entered
	 * @return true if the piece is valid
	 */
	private boolean validatePieceSelected(Command command) {
		String piece = command.getPiece();
		for (String s : animalPieces.keySet()) {
			if (piece.equalsIgnoreCase(s))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * After user input, we need to validate: - the command is valid - the piece
	 * /piece name is valid - the destination coordinates are valid - the move is
	 * valid by invoking validateMove - validate by checking the path
	 * 
	 * 
	 * @param command
	 */
	private Piece getPieceFromCommand(Command command) {
		String pieceString = command.getPiece();
		for (String s : animalPieces.keySet()) {
			if (pieceString.equalsIgnoreCase(s))
				return animalPieces.get(s);
		}

		return null; // The piece is invalid
	}

	public void handleMove(Command command) {
		// get the piece specified
		Piece piece = getPieceFromCommand(command);
		if (piece instanceof Fox) {
			handleFoxMove(piece);
		} else if (piece instanceof Rabbit) {
			handleRabbitMove(piece);
		}
	}

	public void handleFoxMove(Piece fox) {
		Fox f = (Fox) fox;
		// If fox moves horizontally, check horizontal path on board
		if (f.getFoxType().compareTo(Fox.FoxType.HORIZONTAL) == 0) {

		}
		// If fox moves vertically, check vertical path on board

	}

	public void handleRabbitMove(Piece rabbit) {

	}

	public void printGameInstructions() {
		String title = "JumpIN Instructions: \n\n";
		String obstacles = "\tThe obstacles are: Mushroom, Fox, Rabbit, Hole.\n\n"
				+ "\tFoxes take up two spaces, head and tail. " + "All other obstacles occupy one square.\n\n";

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
		String abbreviations = "In this game, the following abbreviations are used to represent each object on the board: "
				+ "\n\tR1 - Rabbit 1\n\tR2 - Rabbit 2\n\tR3 - Rabbit 3\n\tF1 - Fox 1\n\tF2 - Fox 2\n\tMS - Mushroom\n\t O - hole \n\t"
				+ "When a rabbit has an asterisk beside it means that it is in a hole \n";
		String commands = "The following are the list of command words you can use: \n" +
				 commandWords.getCommandWords()  + "\n" 
				 + "For move, the format is: move [name of piece to move] [position to move it to in the format of RowCol]\n";
		
				

		System.out.println(title + obstacles + movements + objective + abbreviations + commands);

	}

	/**
	 * Checks if the player has won based on the number of rabbits in a hole
	 * 
	 * @return true if the player has won
	 */
	public boolean hasWon() {
		return rabbitsInHoles == 3;
	}

	public boolean gameOver() {
		if (hasWon()) {
			System.out.println("Congrats! You solved the puzzle!");
			return true;
		}
		if (quitGame) {
			System.out.println("Thanks for playing. Goodbye");
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.printGameInstructions();

		while (!game.gameOver()) {
			game.startNewRound();
		}
	}

}
