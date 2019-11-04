import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	public GameController(Game game, GameView gameView) {
		this.game = game;
		this.gameView = gameView;
		gameView.addHelpListener(new HelpListener());
		gameView.addQuitListener(new QuitListener());

		numOfButtonsPressed = 0;
	}

	private void addActionListeners() {
		for (int i = 0; i < gameView.getBoardSize(); i++) {
			for (int j =0; j < gameView.getBoardSize(); j++) {
				gameView.board[i][j].addActionListener(new ButtonListener(i,j));
			}
		}
	}

	// Pass the source of the button listener call?
	public void reportButtonPressed(Object src) {
		numOfButtonsPressed++;
		if (numOfButtonsPressed == 1) {
			getPieceSelected(src);
		} else if (numOfButtonsPressed == 2) {
			getDestinationPos(src);
			numOfButtonsPressed = 0; // reset to 0 because at 2 a complete play has been made
		}
	}

	private void getPieceSelected(Object src) {

	}

	private void getDestinationPos(Object src) {

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

	class  ButtonListener implements ActionListener {
		
		private int x,y;
		
		public ButtonListener(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

}
