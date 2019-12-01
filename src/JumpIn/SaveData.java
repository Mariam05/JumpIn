package JumpIn;

import java.io.Serializable;

public class SaveData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	
	public SaveData(Game game) {
		this.game = game;
	}
	
}
