package JumpIn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


/**
 * The controller of the MVC Pattern.
 * This class updates the model based on the the player's interactions with the view,
 * and then updates the view with the current model. 
 * 
 * @author Team members of //TODO: Get Team Name
 *
 */
public class GameController {

	private Game game; //the model
	private GameView gameView; //the view
	private int numOfButtonsPressed; //the number of buttons pressed by the user in a round
	private Command command; //The commnd to form
	private String word2, word3; //The words that will make up the command
	private Stack<Command> undo, redo; // Stores commands to revert moves either way
	private static final String COMMAND = "move"; //The default command (other commands are represented by buttons in view)

	/**
	 * Create the game controller instance by adding listeners to the view
	 * @param game the model
	 * @param gameView the view
	 */
	public GameController(Game game, GameView gameView) {
		this.game = game;
		this.gameView = gameView;
		
		undo = new Stack<Command>();
		redo = new Stack<Command>();
		
		numOfButtonsPressed = 0;
		addActionListeners();

	}

	/**
	 * Add a button action listener to each button in the view
	 * Add action listeners to menu buttons
	 */
	private void addActionListeners() {
		// Iterating through board and adding button listeners to every square
		for (int i = 0; i < gameView.getBoardSize(); i++) {
			for (int j = 0; j < gameView.getBoardSize(); j++) {
				gameView.board[i][j].addActionListener(new ButtonListener(i, j));
			}
		}
		
		// Adding listeners to menu items
		gameView.addUndoListener(new UndoListener());
		gameView.addRedoListener(new RedoListener());
	}

	/**
	 * The button listener will call this when it is notified of an event
	 * @param b the button listener that called this method
	 */
	public void reportButtonPressed(ButtonListener b) {
		numOfButtonsPressed++; //A button was pressed, increase this var
		if (numOfButtonsPressed == 1) { //if its the first button pressed in this round it represents the piece to move
			getPieceSelected(b);
		} else if (numOfButtonsPressed == 2) { //if it's the second button pressed in this round it represents the destination
			getDestinationPos(b);
			processCommand(word2, word3); //round is over so process command
			numOfButtonsPressed = 0; // reset to 0 because at 2 a complete play has been made
		}
	}

	/**
	 * Get the piece selected by the user. The information for this is stored in the buttonlistener.
	 * @param b the button listener that received this event
	 */
	private void getPieceSelected(ButtonListener b) {	
		word2 = game.getBoard().getSquare(b.getCol(), b.getRow()).getPieceString();

	}

	/**
	 * Get the destination that the player wants to move the piece to
	 * @param b the button listener that received this event
	 */
	private void getDestinationPos(ButtonListener b) {
		word3 = b.getStringXY();
	}

	/**
	 * Process the command of the user
	 * @param word2 the piece to move
	 * @param word3 the destination
	 */
	private void processCommand(String word2, String word3) {
		command = new Command(COMMAND, word2, word3); //create a command object
		Command revert = getRevertCommand(command); // Storing revert command
		boolean validMove = game.processCommand(command); //send it to the model

		if (!validMove) { // if the move is invalid notify the player
			gameView.displayMessage("Invalid move");
		} else { // otherwise check for winner and update view
			//undo.add(revert); // Only adding revert command if the move is valid
			if (game.getBoard().hasWon()) {
				gameView.update();
				gameView.displayMessage("CONGRATS! You solved the puzzle!");
				gameView.dispose();
			}
			gameView.update();
		}
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
		Piece p = game.getPieceFromCommand(cmd);
		if(p instanceof Rabbit || p instanceof Fox) {
			String originalLocation = p.getXPos() + "" + p.getYPos(); // Getting current position of piece (before it's moved)
			return new Command(COMMAND, p.toString(), originalLocation);
		} else {
			return null; // Return null if the piece is not an animal
		}
		
	}
	
	/**
	 * Undoes current move and stores command to revert it in redo
	 * 
	 * @author Nazifa Tanzim
	 */
	private void undo() {
		// Check if the stack is empty
		if(!game.undo()) {
			gameView.displayMessage("No more undo's left");
		} else {
			gameView.update(); // Update the view
		}
	}

	/**
	 * Allows user to redo a move
	 * 
	 * @author Nazifa Tanzim
	 */
	private void redo() {
		// Check if stack is empty
		if(redo.isEmpty()) {
			gameView.displayMessage("No more redo's left");
		} else{
			Command c = redo.pop(); // Get the most recent redo command
			processCommand(c.getPiece(), c.getDestination()); // Process undo move
			gameView.update(); // Update the view
		}
		
	}

	
	/**
	 * Listens for undo option/command
	 * 
	 * @author Nazifa Tanzim
	 *
	 */
	class UndoListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			undo();
		}
	}
	
	/**
	 * Listens for redo option/command
	 * 
	 * @author Nazifa Tanzim
	 *
	 */
	class RedoListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			redo();
		}
	}
	
	/**
	 * Listener for button events. Holds information on the button it is associated with.
	 * @author Mariam
	 *
	 */
	class ButtonListener implements ActionListener {

		private int i, j; //the coordinates of the button

		public ButtonListener(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}

		/**
		 * Get the row of the button
		 * @return int of the row (y val)
		 */
		public int getRow() {
			return i;
		}

		/**
		 * Get the column of the button
		 * @return int column (x val)
		 */
		public int getCol() {
			return j;
		}

		/**
		 * The board's[i][j] correspond to rowCol, but in the text based version the
		 * user inputs the position as XY (i.e. ColRow), so here they are flipped.
		 * 
		 * @return
		 */
		public String getStringXY() {
			return this.getCol() + "" + this.getRow();
		}

		/**
		 * Handle the action event by reporting to the controller
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			reportButtonPressed(this);
		}

	}

}
