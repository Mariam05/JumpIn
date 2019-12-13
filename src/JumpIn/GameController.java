package JumpIn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;


/**
 * Listens to the buttons of the board
 * 
 * @author tomar
 *
 */
public class GameController implements ActionListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Game game;

	private static int numOfButtonsPressed;
	private static String word2, word3; // The words that will make up the command
	private static Command command;
	private static final String COMMAND = "move"; // The default command (other commands are represented by buttons in
													// view)

	private int i, j;

	public GameController(int i, int j, Game game) {
		this.game = game;
		this.i = i;
		this.j = j;

		numOfButtonsPressed = 0;
	}

	/**
	 * Process the command of the user
	 * 
	 * @param word2 the piece to move
	 * @param word3 the destination
	 */
	private void processCommand(String word2, String word3) {
		command = new Command(COMMAND, word2, word3); // create a command object
		game.processCommand(command); // send it to the model

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		numOfButtonsPressed = (numOfButtonsPressed + 1) % 2;
		if (numOfButtonsPressed == 1) {
			word2 = game.getBoard().getSquare(j, i).getPieceString(); /// col, row

		} else {
			word3 = j + "" + i; // colrow
			processCommand(word2, word3); // round is over so process command

		}

	}

}
