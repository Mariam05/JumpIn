package JumpIn;

import java.awt.Color;
import java.util.HashMap;

/**
 * This is the main class for the JumpIn came. 
 * It acts as the model in the MVC pattern
 * 
 * @author Mariam Almalki, Nazifa Tanzim
 * @version 2.0 of JumpIn
 *
 */
public class Game {

	private Parser parser;
	private Board board;
	private Piece fox1, fox2, fox1T, fox2T, mushroom1, mushroom2, rabbit1, rabbit2, rabbit3;
	private HashMap<String, Piece> animalPieces;
	private int numOfRabbits;
	private boolean quitGame;
	
	
	/**
	 * Instantiate the parser and commandWords objects. Set up the board with the
	 * pieces
	 */
	public Game() {
		parser = new Parser();
		animalPieces = new HashMap<>();
		board = new Board();
		quitGame = false;

		// Instantiate the pieces on the board

		// For the foxes, associated each head with it's tail and each tail with it's
		// head.
		fox1 = new Fox("F1H", Fox.FoxType.HORIZONTAL, true);
		fox1T = new Fox("F1T", Fox.FoxType.HORIZONTAL, false);
		((Fox) fox1).addAssociatedPart((Fox) fox1T);
		((Fox) fox1T).addAssociatedPart((Fox) fox1);
		fox2 = new Fox("F2H", Fox.FoxType.VERTICAL, true);
		fox2T = new Fox("F2T", Fox.FoxType.VERTICAL, false);
		((Fox) fox2).addAssociatedPart((Fox) fox2T);
		((Fox) fox2T).addAssociatedPart((Fox) fox2);

		mushroom1 = new Mushroom("MSH");
		mushroom2 = new Mushroom("MSH");
		
		rabbit1 = new Rabbit("RA1", Color.WHITE); 
		rabbit2 = new Rabbit("RA2", Color.GRAY);
		rabbit3 = new Rabbit("RA3", Color.YELLOW);
		numOfRabbits = 3;
		
		
		// Add the pieces to the piece hashmap
		animalPieces.put(fox1.toString(), fox1);
		animalPieces.put(fox2.toString(), fox2);
		animalPieces.put(fox1T.toString(), fox1T);
		animalPieces.put(fox2T.toString(), fox2T);
		animalPieces.put(rabbit1.toString(), rabbit1);
		animalPieces.put(rabbit2.toString(), rabbit2);
		animalPieces.put(rabbit3.toString(), rabbit3);

		// Add the pieces to the board. Coordinates are row col
		board.addPiece(fox1, 4, 3);
		board.addPiece(fox1T, fox1.getXPos() - 1, fox1.getYPos()); // add fox1 tail

		board.addPiece(fox2, 1, 1);
		board.addPiece(fox2T, fox2.getXPos(), fox2.getYPos() - 1); // add fox2 tail

		board.addPiece(rabbit1, 3, 0);
		board.addPiece(rabbit2, 4, 2);
		board.addPiece(rabbit3, 1, 4);

		board.addPiece(mushroom1, 2, 4);
		board.addPiece(mushroom2, 3, 1);

	}

	/**
	 * Returns the board used in the game
	 * @return
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Returns the list of animals on the board and their identification string
	 * @return hashmap of animals on board
	 */
	public HashMap<String, Piece> getAnimalsOnBoard() {
		return animalPieces;
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void startNewRound() {
		board.printBoard();
		Command command = parser.getCommand();
		while (processCommand(command) && !hasWon()) {
			board.printBoard();
			command = parser.getCommand();
		}
	}

	/**
	 * Checks if player has won
	 * @return true if they have won
	 */
	public boolean hasWon() {
		return board.getNumRabbitsInHoles() == numOfRabbits;
	}

	/**
	 * Play the game.
	 */
	public void play() {
		printGameInstructions();
		while (!hasWon() & !quitGame) {
			startNewRound();
		}
	}


	/**
	 * Given a command, process (that is: execute) the command.
	 * 
	 * @param command The command to be processed.
	 * @return false If the command ends the game, true otherwise.
	 */
	public boolean processCommand(Command command) {

		if (command.isUnknown()) {
			return false;
		}

		String commandWord = command.getCommandWord();

		if (commandWord.equalsIgnoreCase("help")) {
			printGameInstructions();

		} else if (commandWord.equalsIgnoreCase("move")) {

			if (!validatePieceSelected(command))
				return false;
			if (!validateDestination(command.getX(), command.getY()))
				return false;

			return handleMove(command);
		} else if (commandWord.equals("quit")) {
			System.out.println(quitMessage());
			quitGame = true;
			return false;
		}

		return false;
	}

	/**
	 * Return a message to the user if they want to quit.
	 * 
	 * @return String message
	 */
	public String quitMessage() {
		return "Thanks for playing. Goodbye";
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
	 * Checks if the user entered a valid destination to go to. i.e. if it is within
	 * the constraints of the board
	 * 
	 * @param newX the col to go to
	 * @param newY the row to go to
	 * @return true if the destination is valid
	 */
	private boolean validateDestination(int newX, int newY) {
		return !board.isOutOfRange(newX, newY);
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

	/**
	 * Handle a move command. Gets the piece that the user wants to move and
	 * processes the command based on the type of piece (i.e. rabbit or fox)
	 * 
	 * @param command
	 */
	public boolean handleMove(Command command) {
		Piece piece = getPieceFromCommand(command);
		return piece.handleMove(board, command.getX(), command.getY());

		
	}


	public String printGameInstructions() {
		String title = "JumpIN Instructions: \n\n";
		String obstacles = "\tThe pieces are: Mushroom, Fox, Rabbit, Hole.\n\n"
				+ "\tFoxes take up two spaces, head and tail. " + "All other obstacles occupy one square.\n\n";

		String movements = "MOVEMENT RULES: The following explains how the obstacles move around the board:\n\n"

				+ "\tRabbit:\n"

				+ "\t\tRabbits can only move by jumping over one adjacent obstacle, empty holes are NOT obstacles.\n"
				+ "\t\tOnce a rabbit is in a hole, it can be jumped over by other rabbits.\n"
				+ "\t\tSide note: Rabbits can jump out of their holes to faciliate another rabbit's path.\n\n"
				+ "\t\tRabbits can jump over a fox's waist, or from its head to tail or tail to head.\n\n"

				+ "\tFoxes: \n"

				+ "\t\tFoxes can slide depending on their initial direction, however many spots needed.\n\n"

				+ "\tMushrooms and holes are stationary.\n\n";

		String objective = "GAME OBJECTIVE: The objective of the game is to move the rabbits and foxes, through a series of movements\n"
				+ "around the obstacles untill all the rabbits are safely in their hole.\n";
		String howTo = "\n HOW TO PLAY: First, select the piece you'd like to move. Next, select the square you want to move it to.";

		// System.out.println(title + obstacles + movements + objective + abbreviations
		// + commands);
		return title + obstacles + movements + objective + howTo;

	}

}