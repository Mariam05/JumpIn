package JumpIn;

/**
 * The main program for the MVC Gui that initializes the three classes
 * and ties everything together. 
 * @version JumpIn 2.0
 *
 */
public class JumpInMVC {
	
	public static void main(String[] args) {
		Game game = new Game();
		GameView view = new GameView(game);
		GameController controller = new GameController(game, view);
		
		view.setVisible(true);
	}
}
