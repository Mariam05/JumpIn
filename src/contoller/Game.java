package contoller;
import java.util.HashMap;

import javax.swing.SwingUtilities;

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
	private Piece fox1, fox2, mushroom1, mushroom2, rabbit1, rabbit2, rabbit3;
	private HashMap<String, Piece> animalPieces;
	private int rabbitsInHoles;
	private boolean quitGame;
	private GameView view;

	/**
	 * Instantiate the parser and commandWords objects. Set up the board with the
	 * pieces
	 */
	public Game(GameView view) {
		this.view = view;
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
		// Note: Foxs will now be referred to by their tails
		board.addPiece(fox1, 4, 3);
		board.addPiece(fox1, fox1.getXPos() - 1, fox1.getYPos()); // add fox1 tail

		board.addPiece(fox2, 1, 1);
		board.addPiece(fox2, fox2.getXPos(), fox2.getYPos() - 1); // add fox2 tail

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
		while (processCommand(command)) {
			board.printBoard();
			command = parser.getCommand();
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
			if (!validateDestinationSelected(command))
				return invalidCommandMessage();

			return handleMove(command);
		} else if (commandWord.equals("quit")) {
			quitGame = true;
		}

		return false;
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
	 * @param command the command that the user entered
	 * @return true if the destination is valid
	 */
	private boolean validateDestinationSelected(Command command) {
		return !board.isOutOfRange(command.getX(), command.getY());
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
	 * return true if move was handled
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
		
		
		this.view.update((f.getXPos()),f.getYPos(),(command.getX()),command.getY(), fox.toString());
		
		if (!fox.validateMove(newX, newY)) {
			return invalidCommandMessage(); // Check that the move type is legal for the animal
		}
		
		
		
		
		// If fox moves horizontally, check horizontal path on board
		if (f.getFoxType().compareTo(Fox.FoxType.HORIZONTAL) == 0) {
			if (currX < newX) { // moving right
				for (int i = currX + 2; i <= newX; i++) { // add 2 because tail is on the left of head
					if (board.getSquare(i, currY).hasPiece())
						return invalidCommandMessage();
				}

				board.removePiece(currX, currY); // remove tail of fox
				board.removePiece(currX + 1, currY); // remove head of fox

				board.addPiece(fox, newX, currY); // add head of fox
				board.addPiece(fox, newX - 1, currY); // add tail of fox

			} else { // moving left

				for (int i = currX - 1; i <= newX; i++) { // reprompt if path isn't clear
					if (board.getSquare(i, currY).hasPiece())
						return invalidCommandMessage();
				}

				board.removePiece(currX, currY); // remove tail of fox
				board.removePiece(currX + 1, currY); // remove head of fox

				board.addPiece(fox, newX + 1, currY); // add head of fox
				board.addPiece(fox, newX, currY);

			}
		} else if (f.getFoxType().compareTo(Fox.FoxType.VERTICAL) == 0) { // this fox moves vertically
			if (currY > newY) { // moving up
				for (int i = newY; i < currY; i++) { // reprompt if path isn't clear
					if (board.getSquare(i, currY).hasPiece())
						return invalidCommandMessage();
				}

				board.removePiece(currX, currY); // remove fox tail
				board.removePiece(currX, currY + 1); // remove fox head

				board.addPiece(fox, newX, newY - 1);
				board.addPiece(fox, newX, newY); // add tail

			} else { // moving down
				for (int i = currY + 2; i <= newY; i++) { // reprompt if path isn't clear
					if (board.getSquare(currX, i).hasPiece())
						return invalidCommandMessage();
				}

				board.removePiece(currX, currY); // remove fox tail
				board.removePiece(currX, currY + 1); // remove fox head

				board.addPiece(fox, newX, newY); // add head
				board.addPiece(fox, newX, newY - 1); // add tail
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
		
		this.view.update((r.getXPos()),r.getYPos(),(command.getX()),command.getY(), rabbit.toString());
		
		// If destination is already filled
		if (board.getSquare(newX, newY).hasPiece()) 
			return invalidCommandMessage();

		// Checking of the paths are filled for each direction
		if (currX < newX) { // moving right
			System.out.println("Moving right");
			for (int k = currX + 1; k < newX; k++) { // check for empty squares
				if (!(board.getSquare(k, currY).hasPiece()))
					invalidCommandMessage();
			}
		} else if (currX > newX) { // moving left
			System.out.println("Moving left");
			for (int k = currX - 1; k > newX; k--) { // check for empty squares
				if (!(board.getSquare(k, currY).hasPiece()))
					invalidCommandMessage();
			}
		} else if (currY > newY) { // moving up
			System.out.println("Moving up");
			for (int k = currY - 1; k > newY; k--) { // check for empty squares
				if (!(board.getSquare(currX, k).hasPiece()))
					invalidCommandMessage();
			}
		} else if (currY < newY) { // moving down
			System.out.println("Moving down");
			for (int k = currY + 1; k < newY; k++) { // check for empty squares
				if (!(board.getSquare(currX, k).hasPiece()))
					invalidCommandMessage();
			}
		}

		// Move is validated, complete the action
		board.removePiece(currX, currY);
		board.addPiece(rabbit, newX, newY);

		if (board.getSquare(newX, newY).isHole())
			rabbitsInHoles++; // if rabbit entered a hole
		if (board.getSquare(currX, currY).isHole())
			rabbitsInHoles--; // if the rabbit was in a hole and now is not

		return true;
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
		String commands = "The following are the list of command words you can use: \n" + commandWords.getCommandWords()
				+ "\n"
				+ "For move, the format is: move [name of piece to move] [position to move it to in the format of ColRow]\n";

		System.out.println(title + obstacles + movements + objective + abbreviations + commands);

	}

	/**
	 * Game is over if either (a) player quits or (b) player wins
	 * 
	 * @return true if game is over
	 */
	public boolean gameOver() {
		if (rabbitsInHoles == 3) { // all rabbits in a hole, player won
			System.out.println("Congrats! You solved the puzzle!");
			return true;
		}
		if (quitGame) {
			System.out.println("Thanks for playing. Goodbye");
			return true;
		}
		return false;
	}

	/**
	 * The client of the game
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
////		Game game = new Game();
////		game.printGameInstructions();
////
////		while (!game.gameOver()) {
////			game.startNewRound();
////		}
//		SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                Game game = new Game(new GameView()); 
//            }
//        });
//	}

}

