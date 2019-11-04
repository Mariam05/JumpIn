package JumpIn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
	
	private int x,y;
	
	public ButtonListener(int x, int y) {

		this.x = x;
		this.y = y;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		System.out.print(this.x + ""+ this.y);

	}

}
