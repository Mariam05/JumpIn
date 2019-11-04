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
	}


	
	class HelpListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Getting here");
			String helpMessage = game.printGameInstructions();
			gameView.displayMessage(helpMessage);
		}

	}

}


