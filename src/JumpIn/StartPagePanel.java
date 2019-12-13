package JumpIn;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartPagePanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton newGameBtn, loadGameBtn;

	public StartPagePanel(GameView g) {
		super(null);
		setBackground(new Color(0, 204, 0));

		// Adding button to go to levels page
		newGameBtn = new JButton("Start a New Game");
		newGameBtn.setBounds(250, 350, 210, 50);
		newGameBtn.setFont(new Font("Monospaced", Font.BOLD, 20));
		newGameBtn.setBackground(Color.WHITE);
		newGameBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		newGameBtn.setForeground(Color.BLUE);
		newGameBtn.addActionListener(e -> {
			setVisible(false);
			g.remove(this);
			new LevelsPanel(g);
		});

		// Adding button to load previous game
		loadGameBtn = new JButton("Load Previous Game");
		loadGameBtn.setBounds(230, 450, 250, 50);
		loadGameBtn.setFont(new Font("Monospaced", Font.BOLD, 20));
		loadGameBtn.setBackground(Color.WHITE);
		loadGameBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		loadGameBtn.setForeground(Color.BLUE);
		loadGameBtn.addActionListener(e -> {
			g.goToLoadedGame(g.game.loadGame());
		});

		// Adding game title
		JLabel title = new JLabel("JUMP IN");
		title.setFont(new Font("Monospaced", Font.BOLD, 125));
		title.setBounds(70, 100, 900, 100);
		title.setForeground(Color.WHITE);

		// Adding all components to panel
		add(title);
		add(newGameBtn);
		add(loadGameBtn);
		setName("StartPage");

		setVisible(true);
		g.add(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
