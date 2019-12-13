package JumpIn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import JumpIn.Fox.FoxType;

/**
 * This class holds the view and logic of the level builder
 * 
 * @author Mariam Almalki
 *
 */
public class LevelBuilderPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * size of the grid = size of baord = 5
	 */
	private int size;

	/**
	 * panel to hole the board buttons
	 */
	private JPanel boardPanel;

	/**
	 * Options for user as a menu
	 */
	private JMenuItem rabbit, vFox, hFox, mushroomMenuItem, done, removeLast, back;
	private JMenuBar menuBar;

	/**
	 * The picture to use as a rollover
	 */
	private Image rollOverPic;

	/**
	 * Board of buttons
	 */
	private JButton[][] bBoard;

	/**
	 * Actual model board
	 */
	private Board board;

	/**
	 * Holds coordinates of holes
	 */
	private int dimensions[] = { 0, 0, 0, 4, 4, 0, 4, 4, 2, 2 };

	private String[] rabbits = { "RA1", "RA2", "RA3" }; // all the possible rabbits
	private Color[] rabbitColours = { Color.WHITE, Color.GRAY, Color.YELLOW };
	private int rCount, fCount, mCount; // counts of rabbits, foxes, and mushrooms, respectively

	String currPieceSelected = null;

	private Piece F1H, F2V, RA1, MSH;
	private GameView gameView;

	private Stack<String> piecesAdded;

	/**
	 * Create a new panel and set it up to start building
	 * 
	 * @param size
	 * @param gameView
	 */
	public LevelBuilderPanel(int size, GameView gameView) {
		super(new BorderLayout());
		rCount = 0;
		fCount = 0;
		mCount = 0;

		this.gameView = gameView;

		boardPanel = new JPanel(new GridLayout(size, size));
		instantiateIcons();

		this.size = size;

		board = new Board();
		bBoard = new JButton[size][size];
		createPieceMenu();
		createButtonsBoard(bBoard);

		piecesAdded = new Stack<>();

		add(boardPanel, BorderLayout.CENTER);
		setVisible(true);
		gameView.add(this);
	}

	/**
	 * Instantiate the menu items and add them to panel. Add action listeners.
	 */
	private void createPieceMenu() {

		JPanel pieceMenu = new JPanel();
		pieceMenu.setLayout(new BorderLayout());

		menuBar = new JMenuBar();

		done = new JMenuItem("Done");
		done.addActionListener(this);
		done.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		done.setName("Done");

		rabbit = new JMenuItem("Rabbit");
		rabbit.addActionListener(this);
		rabbit.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		rabbit.setName("Rabbit");

		hFox = new JMenuItem("Horizontal Fox");
		hFox.addActionListener(this);
		hFox.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		hFox.setName("HFox");

		vFox = new JMenuItem("Vertical Fox");
		vFox.addActionListener(this);
		vFox.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		vFox.setName("VFox");

		mushroomMenuItem = new JMenuItem("Mushroom");
		mushroomMenuItem.addActionListener(this);
		mushroomMenuItem.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		mushroomMenuItem.setName("Mushroom");

		removeLast = new JMenuItem("Undo");
		removeLast.addActionListener(this);
		removeLast.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		removeLast.setName("Undo");
		removeLast.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		back = new JMenuItem("Back");
		back.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		back.addActionListener(e -> menuItemBackPressed());

		menuBar.add(done);
		menuBar.add(rabbit);
		menuBar.add(hFox);
		menuBar.add(mushroomMenuItem);
		menuBar.add(vFox);
		menuBar.add(removeLast);
		menuBar.add(back);

		add(menuBar, BorderLayout.PAGE_START);

	}

	/**
	 * If the user wants to go back (ie quit the level builder)
	 */
	private void menuItemBackPressed() {
		int i = JOptionPane.showConfirmDialog(this, "Would you like to save before leaving?");
		if (i == 2)
			return; // cancel, don't do anything
		if (i == 0) { // used said yes
			handleDone();
		}
		gameView.goBack();
	}

	/*
	 * All the animals, mushrooms and holes will be added by using this method
	 */
	private void instantiateIcons() {

		gameView.instantiateIcons();

		rollOverPic = null;

	}

	/*
	 * The board will be created then we are adding the buttons in side it and also
	 * adding background color
	 */
	private void createButtonsBoard(JButton[][] board) {

		Image grassIcon = gameView.grassPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				JButton button = new JButton();
				board[i][j] = button;
				button.setPreferredSize(new Dimension(200, 200));
				button.setBackground(new Color(0, 204, 0));
				button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
				button.setIcon(new ImageIcon(grassIcon));
				button.setRolloverEnabled(true);
				button.addMouseListener(new ButtonHoverListener(i, j));
				boardPanel.add(button);
			}
		}

		// Set the holes
		for (int i = 1; i < dimensions.length; i = i + 2) {
			board[dimensions[i - 1]][dimensions[i]]
					.setIcon(new ImageIcon(gameView.hole.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
			board[dimensions[i - 1]][dimensions[i]].setBackground(new Color(153, 0, 0));
		}
	}

	/**
	 * What to do if each menu item was pressed. Basically,change the rollover pic
	 * if the user changes a piece. if undo, execute stack logic.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o instanceof JMenuItem) {
			JMenuItem item = (JMenuItem) o;
			String name = item.getName();
			switch (name) {
			case ("Rabbit"):
				rollOverPic = gameView.whiteRabbit;
				currPieceSelected = "Rabbit";
				break;
			case ("HFox"):
				rollOverPic = gameView.foxface;
				currPieceSelected = "HFox";
				break;
			case ("VFox"):
				rollOverPic = gameView.foxface;
				currPieceSelected = "VFox";
				break;
			case ("Mushroom"):
				rollOverPic = gameView.mushroom;
				currPieceSelected = "Mushroom";
				break;
			case ("Done"):
				handleDone();
				break;
			case ("Undo"):
				if (!piecesAdded.isEmpty()) {
					String pieceRemoved = piecesAdded.pop();
					Piece p = board.getPiecesOnBoard().get(pieceRemoved);
					board.removePiece(p.getXPos(), p.getYPos());
					if (p instanceof Fox) {
						fCount--;
						Piece tail = ((Fox) p).getAssociatedPart();
						board.removePiece(tail.getXPos(), tail.getYPos());

						bBoard[p.getYPos()][p.getXPos()].setIcon(new ImageIcon(
								gameView.grassPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
						bBoard[tail.getYPos()][tail.getXPos()].setIcon(new ImageIcon(
								gameView.grassPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
					}
					if (p instanceof Rabbit) {
						rCount--;
						removeMushroomOrRabbit(p);
					}
					if (p instanceof Mushroom) {
						mCount--;
						removeMushroomOrRabbit(p);
					}

				}

			}
		}

	}

	/**
	 * Remove a rabbit or a mushroom from the board. this is a helper method
	 * 
	 * @param p
	 */
	private void removeMushroomOrRabbit(Piece p) {
		if (board.isHole(p.getXPos(), p.getYPos())) {
			bBoard[p.getYPos()][p.getXPos()]
					.setIcon(new ImageIcon(gameView.hole.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
		} else {
			bBoard[p.getYPos()][p.getXPos()]
					.setIcon(new ImageIcon(gameView.grassPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
		}
		board.removePiece(p.getXPos(), p.getYPos());
	}

	/**
	 * If the user is done making their level, this method checks if it is
	 * solveable.
	 */
	private void handleDone() {
		if (board.hasWon()) {
			JOptionPane.showMessageDialog(this, "There is nothing to solve, this game is in winning state!");
			return;
		}

		// Check if the level built is solveable
		Solver solver = new Solver(board);
		if (solver.getHint() == null) {
			JOptionPane.showMessageDialog(this, "This game is unsolveable!");
		} else {
			String levelName = JOptionPane.showInputDialog("Enter a name for your level");

			// Won't save level to json without a valid name
			if (levelName != null) {
				String boardRepresentation = board.getStringRepresentation();
				LevelsParser.addCustomLevelToFile(levelName, boardRepresentation);
				gameView.goBack();
				gameView.goBack();
			}

		}

	}

	/**
	 * After a mouse click, adds the current piece to the board
	 * 
	 * @param i
	 * @param j
	 */
	public void addPieceToBoard(int i, int j) {
		switch (currPieceSelected) {
		case ("Rabbit"):
			RA1 = new Rabbit(rabbits[rCount], rabbitColours[rCount]);
			board.addPiece(RA1, j, i);
			board.addToPieceHashmap(RA1.toString(), RA1);
			piecesAdded.add(RA1.toString());
			rCount++;
			break;
			
		case ("HFox"):

			F1H = new Fox("F1H", FoxType.HORIZONTAL, true);
			Piece tail = ((Fox) F1H).getAssociatedPart();

			board.addPiece(F1H, j, i);
			board.addPiece(tail, tail.getXPos(), tail.getYPos());

			board.addToPieceHashmap(F1H.toString() + fCount, F1H);
			board.addToPieceHashmap(tail.toString() + fCount, tail);

			piecesAdded.add(F1H.toString() + fCount);

			fCount++;
			break;
			
		case ("VFox"):

			F2V = new Fox("F2V", FoxType.VERTICAL, true);
			Piece vtail = ((Fox) F2V).getAssociatedPart();

			board.addPiece(F2V, j, i);
			board.addPiece(vtail, vtail.getXPos(), vtail.getYPos());
			

			board.addToPieceHashmap(F2V.toString() + fCount, F2V);
			board.addToPieceHashmap(vtail.toString() + fCount, vtail);

			piecesAdded.add(F2V.toString() + fCount);

			fCount++;
			break;
			
		case ("Mushroom"):
			MSH = new Mushroom("MSH");
			board.addPiece(MSH, j, i);
			board.addToPieceHashmap("MSH" + mCount, MSH); // add mCount to uniquely identify each mushroom
			piecesAdded.add("MSH" + mCount);
			mCount++;
			break;
		}

	}

	/**
	 * Check that the square(s) that the user wants to place the piece on is/are
	 * valid, and that
	 * 
	 * @param currPiece the piece to place
	 * @param i         the row coordinate
	 * @param j         the col coordinate
	 * @return true if it is valid
	 */
	public boolean isSelectionValid(String currPiece, int i, int j) {
		switch (currPiece) {
		case ("Mushroom"):
			if (board.hasPiece(j, i) || mCount >= 3) {
				return false;
			}
			break;
		case ("Rabbit"):
			if (board.hasPiece(j, i) || rCount >= 3) {
				return false;
			}
			break;
		case ("HFox"):
			if (j == 0 || board.hasPiece(j, i) || board.hasPiece(j - 1, i) || board.isHole(j, i)
					|| board.isHole(j - 1, i) || fCount >= 2) {
				return false;
			}
			break;
		case ("VFox"):
			if (i == 0 || board.hasPiece(j, i) || board.hasPiece(j, i - 1) || board.isHole(j, i)
					|| board.isHole(j, i - 1) || fCount >= 2) {
				return false;
			}
			break;
		}
		return true;
	}

	/**
	 * Check if the user can place anymore pieces
	 * 
	 * @param currPieceSelected
	 * @return true if pieces are all used up
	 */
	public boolean arePiecesUsedUp(String currPieceSelected) {
		switch (currPieceSelected) {
		case ("Mushroom"):
			return (mCount >= 3);
		case ("Rabbit"):
			return (rCount >= 3);
		case ("HFox"):
		case ("VFox"):
			return (fCount >= 2);
		}

		return false; // input was wrong
	}

	/**
	 * Informs the user that they can't place anymore of that specific piece. This
	 * method is separate from arePiecesUsedUp because the latter is used in
	 * different contexts.
	 * 
	 * @param currPieceSelected
	 */
	public void sendPiecesUsedUpMessage(String currPieceSelected) {
		switch (currPieceSelected) {
		case ("Mushroom"):
			JOptionPane.showMessageDialog(this, "No more mushroom pieces");
			break;
		case ("Rabbit"):
			JOptionPane.showMessageDialog(this, "No more rabbit pieces");
			break;
		case ("HFox"):
		case ("VFox"):
			JOptionPane.showMessageDialog(this, "No more fox pieces");
			break;
		}
	}

	/**
	 * This class is created to handle a mouse hovering over a button and then
	 * clicking on it
	 * 
	 * @author Mariam
	 *
	 */
	class ButtonHoverListener implements MouseListener {

		private boolean hFoxSelected;
		private boolean vFoxSelected;
		private int i, j; // coordinates of button

		public ButtonHoverListener(int i, int j) {
			this.i = i;
			this.j = j;
			hFoxSelected = false;
			vFoxSelected = false;
		}

		/**
		 * Set the icon permanently to the button board and add the respective piece to
		 * the board.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {

			if (rollOverPic != null) { // make sure a piece was selected
				if (isSelectionValid(currPieceSelected, i, j)) { // make sure destination is valid

					bBoard[i][j].setIcon(
							new ImageIcon(rollOverPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));

					// if fox, also change icon of tail
					if (currPieceSelected.equals("HFox")) {
						bBoard[i][j - 1].setIcon(new ImageIcon(
								gameView.foxtail.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
						hFoxSelected = false;

					}

					if (currPieceSelected.equals("VFox")) {
						bBoard[i - 1][j].setIcon(new ImageIcon(
								gameView.foxtail.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
						vFoxSelected = false;
					}

					addPieceToBoard(i, j); // add the piece to the board

				} else if (arePiecesUsedUp(currPieceSelected)) {
					sendPiecesUsedUpMessage(currPieceSelected);
				}
			}

		}

		/**
		 * What to do if user hovers over a square
		 */
		@Override
		public void mouseEntered(MouseEvent e) {
			if (rollOverPic != null) { // make sure a piece was selected

				if (isSelectionValid(currPieceSelected, i, j)) { // make sure the location is valid

					// set the rolloverimage for the square. If it's a fox, set the icon for the
					bBoard[i][j].setRolloverIcon(
							new ImageIcon(rollOverPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));

					// piece before it as well\

					if (currPieceSelected.equals("HFox")) {
						hFoxSelected = true;
						bBoard[i][j - 1].setIcon(new ImageIcon(
								gameView.foxtail.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
					}
					if (currPieceSelected.equals("VFox")) {
						vFoxSelected = true;
						bBoard[i - 1][j].setIcon(new ImageIcon(
								gameView.foxtail.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
					}

				} else { // if it's an invalid location, disable rollover. Otherwise, it will show
							// previously selected icon
					bBoard[i][j].setRolloverEnabled(false);
				}
			}
		}

		/**
		 * if it's a fox, then the tail replaced the icon of the square so we need to
		 * reset it the icon will always be grass because a fox can't go over anything
		 * else
		 */
		@Override
		public void mouseExited(MouseEvent e) {
			if (hFoxSelected) {
				bBoard[i][j - 1].setIcon(
						new ImageIcon(gameView.grassPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
				hFoxSelected = false;
			}
			if (vFoxSelected) {
				bBoard[i - 1][j].setIcon(
						new ImageIcon(gameView.grassPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
				vFoxSelected = false;
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

}
