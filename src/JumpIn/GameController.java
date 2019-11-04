package JumpIn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.JButton;

/**
 * The controller of the MVC Pattern
 * 
 * @author Team members of //TODO: Get Team Name
 *
 */
public class GameController {

	private Game game;
	private GameView gameView;
	private int numOfButtonsPressed;
	private Command command;
	private String word2, word3;
	
	private static final String COMMAND = "move";

	public GameController(Game game, GameView gameView) {
		this.game = game;
		this.gameView = gameView;
		gameView.addHelpListener(new HelpListener());
		gameView.addQuitListener(new QuitListener());

		numOfButtonsPressed = 0;
		addActionListeners();
		
	}

	private void addActionListeners() {
		for (int i = 0; i < gameView.getBoardSize(); i++) {
			for (int j = 0; j < gameView.getBoardSize(); j++) {
				gameView.board[i][j].addActionListener(new ButtonListener(i,j));
			}
		}
	}

	// Pass the source of the button listener call?
	public void reportButtonPressed(ActionEvent src, ButtonListener b) {
		numOfButtonsPressed++;
		if (numOfButtonsPressed == 1) {
			getPieceSelected(b);
		} else if (numOfButtonsPressed == 2) {
			getDestinationPos(b);
			processCommand(word2, word3);
			numOfButtonsPressed = 0; // reset to 0 because at 2 a complete play has been made
		}
	}

	private void getPieceSelected(ButtonListener b) {
		word2 = game.getBoard().getSquare(b.getCol(), b.getRow()).getPieceString();
		
	}

	private void getDestinationPos(ButtonListener b) {
		word3 = b.getStringXY();
	}
	
	
	private void processCommand(String word2, String word3) {
		command = new Command(COMMAND, word2, word3);
		boolean validMove = game.processCommand(command);
		
		if(!validMove) { //if the move is invalid notify the player and let them know
			gameView.displayMessage("Invalid move");
		} else { //otherwise check for winner and update view
			if (game.hasWon()) {
				gameView.update();
				gameView.displayMessage("CONGRATS! You solved the puzzle!");
				gameView.dispose();
			}
			gameView.update();
		}
		
	}

	/**
	 * This listener is for the help option / command
	 * 
	 * @author Mariam
	 *
	 */
	class HelpListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String helpMessage = game.printGameInstructions();
			gameView.displayMessage(helpMessage);
		}

	}

	/**
	 * This listener is for the quit option/command
	 * 
	 * @author Mariam
	 *
	 */
	class QuitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String quitMessage = game.quitMessage();
			gameView.displayMessage(quitMessage);
			gameView.dispose();

		}
	}

	class ButtonListener implements ActionListener {
		
		private int i,j;
		
		public ButtonListener(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}

		public int getRow() {
			return i;
		}
		
		public int getCol() {
			return j;
		}
		
		
		/**
		 * The board's[i][j] correspond to rowCol, but in the text based version the user inputs 
		 * the position as XY (i.e. ColRow), so here they are flipped. 
		 * @return
		 */
		public String getStringXY() {
			return this.getCol() + "" + this.getRow();
		}
		

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Got here");
			reportButtonPressed(e, this);
			
		}
		
		

		{
			
		}
	}
	
}
