
/**
 * The main program for the MVC Gui that initializes the three classes
 * and ties everything together. 
 * @author Mariam 
 * @version JumpIn 2.0
 *
 */
package JumpIn;

public class JumpInMVC {

	
	public static void main(String[] args) {
		Game game = new Game();
		GameView view = new GameView(game);
		GameController controller = new GameController(game, view);
		
		view.setVisible(true);
	}
}