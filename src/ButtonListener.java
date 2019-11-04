import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class  ButtonListener implements ActionListener {
	
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
