package JumpIn;

public class JumpInMVC {

	
	public static void main(String[] args) {
		Game game = new Game();
		GameView view = new GameView(game);
		GameController controller = new GameController(game, view);
		
		view.setVisible(true);
	}
}
