package JumpIn;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is the main class for the JumpIn came. Acts as the controller
 * 
 * @author Mariam Almalki, Nazifa Tanzim
 * @version 2.0 of JumpIn
 *
 */
public class Game {

	private Parser parser;
	private CommandWord commandWords;
	private Board board;
	private Piece fox1, fox2, fox1T, fox2T, mushroom1, mushroom2, rabbit1, rabbit2, rabbit3;
	private HashMap<String, Piece> animalPieces;
	private int rabbitsInHoles;
	private boolean quitGame;
	private List<ButtonListener> listenerButtons;
	private GameView view;

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
		listenerButtons = new ArrayList<>();

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
		rabbit1 = new Rabbit("RA1"); //colour is white (default)
	
		rabbit2 = new Rabbit("RA2");
		((Rabbit) rabbit2).setColour(Color.GRAY);
		rabbit3 = new Rabbit("RA3");
		((Rabbit) rabbit3).setColour(Color.YELLOW);
		
		
		// Add the pieces to the piece hashmap
		animalPieces.put(fox1.toString(), fox1);
		animalPieces.put(fox2.toString(), fox2);
		animalPieces.put(fox1T.toString(), fox1T);
		animalPieces.put(fox2T.toString(), fox2T);
		animalPieces.put(rabbit1.toString(), rabbit1);
		animalPieces.put(rabbit2.toString(), rabbit2);
		animalPieces.put(rabbit3.toString(), rabbit3);

		// Add the pieces to the board. Coordinates are row col
		// Note: Foxs will now be referred to by their tails
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

	public Board getBoard() {
		return this.board;
	}

	public HashMap<String, Piece> getAnimalsOnBoard() {
		return animalPieces;
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void startNewRound() {
		board.printBoard();
		Command command = parser.getCommand();
		while (processCommand(command) && rabbitsInHoles != 3) {
			board.printBoard();
			command = parser.getCommand();
		}
	}

	private boolean hasWon() {
		if (rabbitsInHoles == 3) {
			System.out.println("Congrats! You solved the puzzle!");
			return true;
		}
		return false;
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
	 * Re - prompts user for correct input if their move is invalid
	 */
	private boolean invalidCommandMessage() {
		System.out.println("Invalid command. Please try again.");
		return false;
	}

	/**
	 * Given a command, process (that is: execute) the command.
	 * 
	 * @param command The command to be processed.
	 * @return false If the command ends the game, true otherwise.
	 */
	public boolean processCommand(Command command) {

		if (command.isUnknown()) {
			invalidCommandMessage();
			return false;
		}

		String commandWord = command.getCommandWord();

		if (commandWord.equalsIgnoreCase("help")) {
			printGameInstructions();

		} else if (commandWord.equalsIgnoreCase("move")) {

			if (!validatePieceSelected(command))
				return invalidCommandMessage();
			if (!validateDestination(command.getX(), command.getY()))
				return invalidCommandMessage();

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
		if (piece instanceof Fox) {
			return handleFoxMove(piece, command);
		} else if (piece instanceof Rabbit) {
			handleRabbitMove(piece, command);
		}

		return true;
	}

	/**
	 * return true if move was handled TODO: Move common code outside of the
	 * specific if statment
	 * 
	 * @param fox
	 * @param command
	 * @return true if fox moved succesfully
	 */
	public boolean handleFoxMove(Piece fox, Command command) {
		Fox f = (Fox) fox;
		int newX = command.getX();
		int newY = command.getY();
		int currX = f.getXPos();
		int currY = f.getYPos();
		boolean isTail = !f.isHead();

		int start, end;
		if (!fox.validateMove(newX, newY))
			return invalidCommandMessage(); // Check that the move type is legal for the animal

		// If fox moves horizontally, check horizontal path on board
		if (f.isHorizontal()) {

			if (currX < newX) { // moving right
				if (!isTail) { // if head, start at square to right and continue to destination
					start = currX + 1;
					end = newX;
				} else { // if tail, start 2 squares to right and end at new+1 (which is where head will
							// be)
					start = currX + 2;
					end = newX + 1;
				}

				if (!validateDestination(end, currY))
					return invalidCommandMessage();

				for (int i = start; i <= end; i++) { // ensure path is clear
					if (board.getSquare(i, currY).hasPiece())
						return invalidCommandMessage();
				}

			} else { // moving left
				if (!isTail) { // if head, start at square left to tail and check to where the tail would reach
					start = currX - 2;
					end = newX - 1;
				} else { // if tail, start at square left to tail and check to where the tail will go
					start = currX - 1;
					end = newX;
				}

				if (!validateDestination(end, currY))
					return invalidCommandMessage();

				for (int i = start; i >= end; i--) { // reprompt if path isn't clear
					if (board.getSquare(i, currY).hasPiece())
						return invalidCommandMessage();
				}

			}

			if (isTail) {
				board.removePiece(currX, currY); // remove tail of fox
				board.removePiece(currX + 1, currY); // remove head of fox

				board.addPiece(fox, newX, currY); // add tail of fox
				board.addPiece(f.getAssociatedPart(), newX + 1, currY); // add head of fox
			} else {
				board.removePiece(currX, currY); // remove head of fox
				board.removePiece(currX - 1, currY); // remove tail of fox

				board.addPiece(fox, newX, currY); // add head of fox
				board.addPiece(f.getAssociatedPart(), newX - 1, currY); // add tail of fox
			}
			
		} else if (!f.isHorizontal()) { // this fox moves vertically
			if (currY > newY) { // moving up

				if (f.isHead()) { // if head (head is below tail for vertical fox)
					start = currY - 2; // start at square above the tail
					end = newY - 1; // end at square above final head destination
				} else {
					start = currY - 1;
					end = newY;
				}

				if (!validateDestination(end, currY))
					return invalidCommandMessage();

				for (int i = start; i < end; i++) { // reprompt if path isn't clear
					if (board.getSquare(i, currY).hasPiece())
						return invalidCommandMessage();
				}

			} else { // moving down

				if (!isTail) { // if head, start at square below head
					start = currY + 1;
					end = newY;
				} else { // if tail, start at 2 squares below curr location (to override head)
					start = currY + 2;
					end = newY + 1;
				}

				if (!validateDestination(end, currY))
					return invalidCommandMessage();

				for (int i = start; i <= end; i++) { // reprompt if path isn't clear
					if (board.getSquare(currX, i).hasPiece())
						return invalidCommandMessage();
				}

			}
			if (f.isHead()) {
				board.removePiece(currX, currY); // remove fox head
				board.removePiece(currX, currY - 1); // remove fox tail

				board.addPiece(fox, newX, newY); // add head
				board.addPiece(f.getAssociatedPart(), newX, newY - 1); // add tail
			} else { // dealing with the tail
				board.removePiece(currX, currY); // remove fox tail
				board.removePiece(currX, currY + 1); // remove fox head

				board.addPiece(fox, newX, newY); // add tail
				board.addPiece(f.getAssociatedPart(), newX, newY + 1); // add head
			}

		}
		return true;
	}

	/**
	 * Handle a move of a rabbit
	 * 
	 * @param rabbit
	 * @param command
	 * @return
	 */
	public boolean handleRabbitMove(Piece rabbit, Command command) {
		Rabbit r = (Rabbit) rabbit;
		int newX = command.getX();
		int newY = command.getY();
		int currX = r.getXPos();
		int currY = r.getYPos();

		if (!rabbit.validateMove(newX, newY)) {
			System.out.println("Got here");
			return invalidCommandMessage();
		}

		// If destination is already filled
		if (board.getSquare(newX, newY).hasPiece())
			return invalidCommandMessage();

		// Checking of the paths are filled for each direction
		if (currX < newX) { // moving right
			for (int k = currX + 1; k < newX; k++) { // check for empty squares
				if (!(board.getSquare(k, currY).hasPiece()))
					return invalidCommandMessage();
			}
		} else if (currX > newX) { // moving left
			for (int k = currX - 1; k > newX; k--) { // check for empty squares
				if (!(board.getSquare(k, currY).hasPiece()))
					return invalidCommandMessage();
			}
		} else if (currY > newY) { // moving up
			for (int k = currY - 1; k > newY; k--) { // check for empty squares
				if (!(board.getSquare(currX, k).hasPiece()))
					return invalidCommandMessage();
			}
		} else if (currY < newY) { // moving down
			for (int k = currY + 1; k < newY; k++) { // check for empty squares
				if (!(board.getSquare(currX, k).hasPiece()))
					return invalidCommandMessage();
			}

		}

		// Move is validated, complete the action
		board.removePiece(currX, currY);
		board.addPiece(rabbit, newX, newY);

		if (board.getSquare(currX, currY).isHole())
			rabbitsInHoles--; // if the rabbit was in a hole and now is not

		if (board.getSquare(newX, newY).isHole()) {
			rabbitsInHoles++; // if rabbit entered a hole
			System.out.println("You got a rabbit in a hole!");
		}

		return true;
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

	/**
	 * The client of the game
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
//		Game game = new Game();
//		game.play();
//	}
}