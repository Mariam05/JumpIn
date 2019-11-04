package contoller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Listener implements ActionListener{

	private Game model;
	private GameView view;
	
	private GameButton cm1;
	private GameButton cm2;
	
	public Listener(GameView view) {
		cm1 = new GameButton(" ",-1,-1);
		cm2 = new GameButton(" ",-1,-1);
		this.view = view;
		model = new Game(this.view);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		
		if(cm1.getRow()==-1) {
			
			cm1 = (GameButton) e.getSource();
			System.out.println(cm1.getText() + "  cm1");
			
		
		}else {
			
		
			cm2 = (GameButton) e.getSource();
			
			System.out.println(cm2.getText()+ "  cm2");
			//if((cm1.getText().substring(0, 1).equals("F")) || (cm1.getText().substring(0, 1).equals("R")) ){
				Command command = new Command("move",cm1.getText(),cm2.toString());
				model.handleMove(command);
			//}
				cm1 = new GameButton(" ",-1,-1);
				cm2 = new GameButton(" ",-1,-1);
		}		
			
			
			
		}
		
	}
	
	
	

