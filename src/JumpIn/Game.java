
package JumpIn;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * This is the main class for the JumpIn came. It acts as the model in the MVC
 * pattern.
 * 
 * TODO: add a method that will just take names of the pieces and assembles the
 * board based on that location
 * 
 * @author Mariam Almalki, Nazifa Tanzim
 * @version 3.0 of JumpIn
 *
 */
public class Game implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static ArrayList<ActionListener> gameListeners;

	private Board board;

	String filename = "file.ser"; 

	private HashMap<String, Piece> pieces;

	private Stack<Command> undo, redo; // Stores commands to revert moves either way

	/**
	 * Instantiate the parser and commandWords objects. Set up the board with the
	 * pieces
	 */
	public Game(String level) {
		LevelsParser.createExternFile();
		setBoard(level); // Init to level 1 by default
		undo = new Stack<Command>();
		redo = new Stack<Command>();
		gameListeners = new ArrayList<>();
		

	}
	
	public void addGameListener(ActionListener a) {
		gameListeners.add(a);
	}

	/**
	 * Returns the board used in the game
	 * 
	 * @return
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Create the board associated with the level name
	 * 
	 * @param levelName
	 */
	public void setBoard(String levelName) {
		board = Board.makeBoardFromLevel(levelName);
		pieces = board.getPiecesOnBoard();
	}

	/**
	 * Returns the list of animals on the board and their identification string
	 * 
	 * @return hashmap of animals on board
	 */
	public HashMap<String, Piece> getAnimalsOnBoard() {
		return board.getPiecesOnBoard();
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
			if (!validateLocation(command.getX(), command.getY()))
				return false;
			undo.add(getRevertCommand(command));
			return handleMove(command); 

		} else if (commandWord.equals("quit")) {
			System.out.println(quitMessage());
			return false;
		}

		return false;
	}
	
	private void notifyListeners() {
		boolean won = board.hasWon();
		for (ActionListener a : gameListeners) {
			if (a instanceof GameView) {
				((GameView)a).update(this, won);
			}
		}
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
		for (String s : pieces.keySet()) {
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
	private boolean validateLocation(int newX, int newY) {
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
	public Piece getPieceFromCommand(Command command) {

		String pieceString = command.getPiece();

		for (String s : pieces.keySet()) {
			if (pieceString.equalsIgnoreCase(s))
				return pieces.get(s);
		}
		return null;

	}

	/**
	 * Handle a move command. Gets the piece that the user wants to move and
	 * processes the command based on the type of piece (i.e. rabbit or fox)
	 * 
	 * @param command
	 */
	public boolean handleMove(Command command) {
		Piece piece = getPieceFromCommand(command);
		if (!piece.validateMove(board, command.getX(), command.getY())) {
			return false;
		}

		piece.handleMove(board, command.getX(), command.getY());
		
		notifyListeners();
		return true;
	}

	/**
	 * Gets the command to reverse another
	 * 
	 * @param cmd
	 * @return command to revert command passed as an argument
	 * @author Nazifa Tanzim
	 */
	private Command getRevertCommand(Command cmd) {
		// Getting the piece that is being moved
		Piece p = getPieceFromCommand(cmd);
		if (p instanceof Rabbit || p instanceof Fox) {
			String originalLocation = p.getXPos() + "" + p.getYPos(); // Getting current position of piece (before it's
																		// // moved)
			return new Command("move", p.toString(), originalLocation);
		} else {
			return null; // Return null if the piece is not an animal
		}

	}

	/**
	 * Allows the user to undo a move
	 * 
	 * @return the previous game state
	 */
	public boolean undo() {
		// Check if the stack is empty
		if (undo.isEmpty()) {
			return false;
		}
		Command c = undo.pop(); // Get the most recent undo command
		redo.add(getRevertCommand(c)); // Add command to reverse undo
		handleMove(c); // Process undo move. we already know it's valid, doesn't need to go through
						// process command
		return true;
	}

	/**
	 * Allows the user to re-do a move/revert an undo
	 * 
	 * @return the game state that was undone
	 */
	public boolean redo() {
		// Check if stack is empty
		if (redo.isEmpty()) {
			return false;
		}
		Command c = redo.pop(); // Get the most recent redo command
		processCommand(c); // Process redo move.
		return true; // Removes previous state from redo stack and returns it
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

	public void saveGame() {
		try {
			// Saving of object in a file
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);

			// Method for serialization of object
			out.writeObject(this);

			out.close();
			file.close();

		}

		catch (IOException ex) {
			System.out.println("IOException is caught. Couldn't save.\n");
			ex.printStackTrace();
		}
	}

	public Game loadGame() {

		Game object1 = null;

		try {
			// Reading the object from a file
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);

			// Method for deserialization of object
			object1 = (Game) in.readObject();

			in.close();
			file.close();

			return object1;
		}

		catch (IOException ex) {
			System.out.println("IOException is caught. Couldn't load.");
			return object1;
		}

		catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException is caught");
			return object1;
		}
	}
}