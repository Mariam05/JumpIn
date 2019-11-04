package contoller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class Listener implements ActionListener{

	private Game model;
	private GameView view;
	
	private GameButton cm1;
	private GameButton cm2;
	
	public Listener(GameView view) {
		cm1 = new GameButton(" ",-1,-1);
		cm2 = new GameButton(" ",-1,-1);
		this.view = view;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		
		if(cm1.getRow()==-1) {
			cm1 = (GameButton) e.getSource();
		}else {
			cm2 = (GameButton) e.getSource();
			
			if((cm1.getText().substring(0, 1).equals("F")) || (cm1.getText().substring(0, 1).equals("R")) ){
				Command command = new Command("move",cm1.getText(),cm1.toString());
				model.handleMove(command);
			}
			
			
			
		}
		
	}
	
	
	

}
