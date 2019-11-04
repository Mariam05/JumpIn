package JumpIn;
import java.util.EventObject;

public class GameEvent extends EventObject {
	private int x,y;
	
	public GameEvent(Game source,int x, int y) {
		super(source);
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int gety() {
		return this.y;
	}

}
