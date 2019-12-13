package JumpIn;

/**
 * The main program for the MVC Gui that initializes the three classes
 * and ties everything together. 
 * @version JumpIn 2.0
 *
 */
public class Main {
	
	public static void main(String[] args) {
		Game game = new Game("1");
		GameView view = new GameView(game);
		
		view.setVisible(true);
	}
}
