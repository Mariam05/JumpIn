package JumpIn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import JumpIn.Fox.FoxType;

/**
 * Can make this an inner class to game view after so that some of gameView's
 * methods could be used.
 * 
 * @author Mariam Almalki
 *
 */
public class LevelBuilderPanel extends JPanel implements ActionListener {

	/**
	 * size of the grid = size of baord = 5
	 */
	private int size;

	/**
	 * panel to hole the board buttons
	 */
	private JPanel boardPanel;

	private JMenuItem rabbit, vFox, hFox, mushroom, save;
	private JMenuBar menuBar;
	private Image mushroomPic, foxHPic, foxTPic, rabbit1Pic, rollOverPic, holePic, grassPic, rabbit2Pic, rabbit3Pic;
	private JButton[][] bBoard;
	private Board board;
	private int dimensions[] = { 0, 0, 0, 4, 4, 0, 4, 4, 2, 2 };

	private String[] rabbits = { "RA1", "RA2", "RA3" };
	private Color[] rabbitColours = { Color.WHITE, Color.GRAY, Color.YELLOW };
	private int rCount, fCount, mCount; // counts of rabbits, foxes, and mushrooms, respectively

	String currPieceSelected = null;

	private Piece F1H, F2V, RA1, RA2, RA3, MSH;

	public LevelBuilderPanel(int size) {
		super(new BorderLayout());
		rCount = 0;
		fCount = 0;
		mCount = 0;

		boardPanel = new JPanel(new GridLayout(size, size));
		instantiateIcons();

		this.size = size;

		board = new Board();
		bBoard = new JButton[size][size];
		createPieceMenu();
		createButtonsBoard(bBoard);

//		instantiatePieces();

		add(boardPanel, BorderLayout.CENTER);
	}

	private void createPieceMenu() {

		JPanel pieceMenu = new JPanel();
		pieceMenu.setLayout(new BorderLayout());

		menuBar = new JMenuBar();

		save = new JMenuItem("Save");
		save.addActionListener(this);
		save.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		save.setName("Save");

		rabbit = new JMenuItem("Rabbit");
		rabbit.addActionListener(this);
		rabbit.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		rabbit.setName("Rabbit");

		hFox = new JMenuItem("Horizontal Fox");
		hFox.addActionListener(this);
		hFox.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		hFox.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		hFox.setName("HFox");

		vFox = new JMenuItem("Vertical Fox");
		vFox.addActionListener(this);
		vFox.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		vFox.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		vFox.setName("VFox");

		mushroom = new JMenuItem("Mushroom");
		mushroom.addActionListener(this);
		mushroom.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		mushroom.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		mushroom.setName("Mushroom");
		// mushroom.setBackground(Color.red);
		// mushroom.setForeground(Color.WHITE);

		menuBar.add(save);
		menuBar.add(rabbit);
		menuBar.add(hFox);
		menuBar.add(mushroom);
		menuBar.add(vFox);

		add(menuBar, BorderLayout.PAGE_START);

	}

	/*
	 * All the animals, mushrooms and holes will be added by using this method
	 */
	private void instantiateIcons() {

		rollOverPic = null;
		// we are adding the holes on the board
		holePic = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/black-hole.png")).getImage();

		// instantiate the mushroom image
		mushroomPic = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/mushroom.png")).getImage();

		grassPic = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/grass.png")).getImage();
//
//		// instantiate the white rabbit image
		rabbit1Pic = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/whiteRabbit.png")).getImage();
//
//		// instantiate the grey rabbit image
		rabbit2Pic = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/rabbit.png")).getImage();
//
//		// instantiate the yellow rabbit image
		rabbit3Pic = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/yellowandBlack.png")).getImage();
//
//		// instantiate the fox face image
		foxHPic = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/foxface.png")).getImage();
//
//		// instantiate the fox tail image 
		foxTPic = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/foxtail.png")).getImage();
	}

	/*
	 * The board will be created then we are adding the buttons in side it and also
	 * adding background color
	 */
	private void createButtonsBoard(JButton[][] board) {

		Image grassIcon = grassPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);

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
		holePic = new ImageIcon(this.getClass().getResource("/JumpIn/Icons/black-hole.png")).getImage();
		Image piece = holePic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
		for (int i = 1; i < dimensions.length; i = i + 2) {
			board[dimensions[i - 1]][dimensions[i]].setIcon(new ImageIcon(piece));
			board[dimensions[i - 1]][dimensions[i]].setBackground(new Color(153, 0, 0));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if (o instanceof JMenuItem) {
			JMenuItem item = (JMenuItem) o;
			String name = item.getName();
			switch (name) {
			case ("Rabbit"):
				rollOverPic = rabbit1Pic;
				currPieceSelected = "Rabbit";
				break;
			case ("HFox"):
				rollOverPic = foxHPic;
				currPieceSelected = "HFox";
				break;
			case ("VFox"):
				rollOverPic = foxHPic;
				currPieceSelected = "VFox";
				break;
			case ("Mushroom"):
				rollOverPic = mushroomPic;
				currPieceSelected = "Mushroom";
				break;
			case("Save"):
				handleSave();break;
			}

		}

	}
	
	private void handleSave() {
		if (board.hasWon()) {
			JOptionPane.showMessageDialog(this, "There is nothing to solve, this game is in winning state!");
		}
		Solver solver = new Solver(board);
		
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
				rCount++;
			break;
		case ("HFox"):

				F1H = new Fox("F" + j + "H", FoxType.HORIZONTAL, true);
				Piece tail = ((Fox) F1H).getAssociatedPart();
				board.addPiece(F1H, j, i);
				board.addPiece(tail, tail.getXPos(), tail.getYPos());
				fCount++;
			break;
		case ("VFox"):
				F2V = new Fox("F" + j + "H", FoxType.VERTICAL, true);
				Piece vtail = ((Fox) F2V).getAssociatedPart();
				board.addPiece(F2V, j, i);
				board.addPiece(vtail, vtail.getXPos(), vtail.getYPos());
				fCount++;
			break;
		case ("Mushroom"):
				MSH = new Mushroom("MSH");
				board.addPiece(MSH, j, i);
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
			return (fCount >= 2) ;
		}

		return false; // input was wrong
	}

	
	/**
	 * Informs the user that they can't place anymore of that specific piece. 
	 * This method is separate from arePiecesUsedUp because the latter is used in different contexts. 
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

		// TODO: put the stuff inside setIcon in a variable so that the code is
		// cleaner(ie. have var for rollOverPic and foxtails
		/**
		 * Set the icon permanently to the button board and add the respective piece to
		 * the board.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {

			if (rollOverPic != null) {
				if (isSelectionValid(currPieceSelected, i, j)) {

					bBoard[i][j].setIcon(
							new ImageIcon(rollOverPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));

					if (currPieceSelected.equals("HFox")) {
						bBoard[i][j - 1].setIcon(
								new ImageIcon(foxTPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
						hFoxSelected = false;

					}

					if (currPieceSelected.equals("VFox")) {
						bBoard[i - 1][j].setIcon(
								new ImageIcon(foxTPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
						vFoxSelected = false;
					}

					addPieceToBoard(i, j);

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
						bBoard[i][j - 1].setIcon(
								new ImageIcon(foxTPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
					}
					if (currPieceSelected.equals("VFox")) {
						vFoxSelected = true;
						bBoard[i - 1][j].setIcon(
								new ImageIcon(foxTPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
					}

				} else{ //if it's an invalid location, disable rollover. Otherwise, it will show previously selected icon
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
				bBoard[i][j - 1]
						.setIcon(new ImageIcon(grassPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
				hFoxSelected = false;
			}
			if (vFoxSelected) {
				bBoard[i - 1][j]
						.setIcon(new ImageIcon(grassPic.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH)));
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
