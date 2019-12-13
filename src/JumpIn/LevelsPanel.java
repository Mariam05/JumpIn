package JumpIn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LevelsPanel extends JPanel implements ActionListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// These will hold the names of all the levels
	private transient JList<String> defaultLevels, customLevels;
	private transient DefaultListModel<String> defaultList, customList;
	private String selectedLevel;
	private GameView g;

	/**
	 * creating levels page
	 * 
	 */
	public LevelsPanel(GameView g) {
		super(new BorderLayout());
		this.g = g;

		defaultList = new DefaultListModel<>();
		customList = new DefaultListModel<>();

		setName("Levels Panel");
		initializeLevels();

		setBackground(new Color(0, 204, 0));

		// Adding headers for the different levels options
		JPanel headers = new JPanel();
		add(headers, BorderLayout.NORTH);

		JLabel defaultLvl = new JLabel("Default Levels        ");
		defaultLvl.setFont(new Font("Monospaced", Font.BOLD, 20));
		headers.add(defaultLvl);

		JLabel customLvl = new JLabel("|      Custom Levels   ");
		customLvl.setFont(new Font("Monospaced", Font.BOLD, 20));
		headers.add(customLvl);

		// Adding buttons to navigate the page
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2));
		add(buttons, BorderLayout.SOUTH);

		JButton backBtn = new JButton("Back");
//		backBtn.addActionListener(e ->{ setVisible(false); g.remove(this);g.add(new StartPagePanel(g));});
		backBtn.addActionListener(e -> g.goBack());

		buttons.add(backBtn);

		JButton startBtn = new JButton("Start");
		buttons.add(startBtn);
		startBtn.setName("Start");
		startBtn.addActionListener(this);

		JButton buildLvlBtn = new JButton("Build A Level");
		buttons.add(buildLvlBtn);
		buildLvlBtn.addActionListener(e -> {
			setVisible(false);
			g.remove(this);
			new LevelBuilderPanel(g.getBoardSize(), g);
		});

		// Adding the levels lists to scroll panes
		JScrollPane pane1 = new JScrollPane(defaultLevels);
		JScrollPane pane2 = new JScrollPane(customLevels);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pane1, pane2);

		splitPane.setDividerLocation(345);
		splitPane.setBounds(0, 30, 700, 600);
		add(splitPane, BorderLayout.CENTER);

		setVisible(true);
		g.add(this);
	}

	/**
	 * Initializes the list of levels based on what is in the json
	 */
	public void initializeLevels() {
		// Getting all the levels from the json
		HashMap<String, String> d = LevelsParser.getDefaultLevels();
		HashMap<String, String> c = LevelsParser.getCustomLevels();

		selectedLevel = null;

		// Ensures default list is initialized once
		// Prevents the levels list being duplicated after going back to start page
		defaultList.removeAllElements();
		d.forEach((key, value) -> defaultList.addElement("Level " + key));

		customList.removeAllElements();
		c.forEach((key, value) -> customList.addElement(key));

		defaultLevels = new JList<>(defaultList);
		customLevels = new JList<>(customList);

		// Add listener to default levels list
		defaultLevels.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					// Stores only the number associated with the level name
					selectedLevel = defaultLevels.getSelectedValue().split("Level ")[1];
				}
			}
		});

		// Add listener to custom levels list
		customLevels.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					// Stores the whole custom level name
					selectedLevel = customLevels.getSelectedValue();
				}
			}
		});
	}

	/**
	 * Returns the most recently selected level
	 * 
	 * @return level selected
	 */
	public String getSelectedLevel() {
		return this.selectedLevel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o instanceof JButton) {
			String name = ((JButton) o).getName();
			switch (name) {
			case ("Start"):
				if (selectedLevel == null) {
					g.displayMessage("Please select a level");
					return;
				} else {
					g.game.setBoard(selectedLevel);
					setVisible(false);
					g.remove(this);
					g.displayGame();
				}
			}

		}

	}

}
