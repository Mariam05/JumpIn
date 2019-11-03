package contoller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Listener implements ActionListener{

	private int ij;
	private String name;
	
	public Listener(int ij) {
		this.ij = ij;
	
	}
	
	public void setName(String name) {
		this.name= name;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		System.out.print(button.getText());
	}
	
	

}
