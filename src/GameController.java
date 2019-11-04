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
		System.out.println("Button " + b.getStringXY() + " was pressed.");
		numOfButtonsPressed++;
		if (numOfButtonsPressed == 1) {
			getPieceSelected(src);
		} else if (numOfButtonsPressed == 2) {
			getDestinationPos(src);
			numOfButtonsPressed = 0; // reset to 0 because at 2 a complete play has been made
		}
	}

	private void getPieceSelected(ActionEvent src) {
		
	}

	private void getDestinationPos(ActionEvent src) {

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
	
	class ButtonEvent extends EventObject{

		public ButtonEvent(Object arg0) {
			super(arg0);
			// TODO Auto-generated constructor stub
		}
		
	}

}
