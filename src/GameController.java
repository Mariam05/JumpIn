import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author Team members of //TODO: Get Team Name
 *
 */
public class GameController {

	private Game game;
	private GameView gameView;

	public GameController(Game game, GameView gameView) {
		this.game = game;
		this.gameView = gameView;
		gameView.addHelpListener(new HelpListener());
		gameView.addQuitListener(new QuitListener());
	}


	/**
	 * This listener is for the help option / command
	 * @author tomar
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
	 * @author tomar
	 *
	 */
	class QuitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String quitMessage = game.quitMessage();
			gameView.displayMessage(quitMessage);
			gameView.dispose();
			
		}
		
	}

}


