package JumpIn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private Command command; //The command to form
	private String word2, word3; //The words that will make up the command
	private static final String COMMAND = "move"; //The default command (other commands are represented by buttons in view)

	/**
	 * Create the game controller instance by adding listeners to the view
	 * @param game the model
	 * @param gameView the view
	 */
	public GameController(Game game, GameView gameView) {
		this.game = game;
		this.gameView = gameView;
		numOfButtonsPressed = 0;
		
		// Adds listener for when user wants to start a new game
		gameView.addNewGameListener(new GameListener()); 

	}
	
	/**
	 * Add a button action listener to each button in the view
	 */
	private void addActionListeners() {
		// Iterating through board and adding button listeners to every square
		for (int i = 0; i < gameView.getBoardSize(); i++) {
			for (int j = 0; j < gameView.getBoardSize(); j++) {
				gameView.board[i][j].addActionListener(new ButtonListener(i, j));
			}
		}
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
		boolean validMove = game.processCommand(command); //send it to the model

		if (!validMove) { // if the move is invalid notify the player
			gameView.displayMessage("Invalid move");
		} else { // otherwise check for winner and update view
			if (game.getBoard().hasWon()) {
				gameView.update();
				gameView.displayMessage("CONGRATS! You solved the puzzle!");
				gameView.dispose();
			}
			gameView.update();
		}
	}
	
	/**
	 * Listens to see if the user has chosen a level to play
	 * Allows controller to add action listeners at the appropriate time
	 * 
	 * @author Nazifa Tanzim
	 *
	 */
	class LevelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			game.setBoard(gameView.getSelectedLevel());
			gameView.goToGame();
			addActionListeners();
		}
	}
	
	/**
	 * Listens to see if user wants to start a new game
	 * 
	 * @author Nazifa Tanzim
	 *
	 */
	class GameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameView.goToLevelPage();
			gameView.addLevelListener(new LevelListener());
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
