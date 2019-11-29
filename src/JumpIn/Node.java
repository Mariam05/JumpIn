package JumpIn;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A node represents the state of the game, which includes all its pieces and
 * their locations. Each node must know it's parent node, and will inherit it's
 * parent's animals and a command.
 * 
 * @author Mariam Almalki
 */
public class Node {

	/**
	 * The Node's parent. the source will have this as null
	 */
	private Node parentNode;

	/**
	 * A new board to hold the pieces, complete the moves, and check for the winning
	 * status
	 */
	private Board board;

	/**
	 * The list of animals on the board
	 */
	private List<Piece> animalList; // a list of animals

	/**
	 * The name of the piece to move.
	 */
	private String pieceToMove;

	/**
	 * The new location for the piece to go to
	 */
	private int newX, newY;

	/**
	 * The command inherited from the parent node.
	 */
	private Command command;

	/**
	 * Constructor to create a new node. It makes a deep copy of all the animals
	 * such that it is not altering the parent's animals, then it sets up the board
	 * and implements the move / command on the board
	 * 
	 * @param parentNode
	 * @param parentsAnimals
	 * @param command
	 */
	public Node(Node parentNode, String parentBoard, Command command) {
		this.parentNode = parentNode;
		this.command = command;
		animalList = new ArrayList<>();

		board = Board.makeBoard(parentBoard);
		
		animalList = board.getPieceObjects();

		if (command != null) { // command could be null if this is the source
			pieceToMove = command.getPiece();
			newX = command.getX();
			newY = command.getY();

			getAnimalToMove().handleMove(board, newX, newY);

		}

	}

	/**
	 * Return animals on the board
	 * 
	 * @return
	 */
	public List<Piece> getPieces() {
		return animalList;
	}

	/**
	 * Get the command that was handled in this node.
	 * 
	 * @return Command object
	 */
	public Command getCommand() {
		return command;
	}

	/**
	 * Get this node's board. This is used to get all valid moves later on
	 * 
	 * @return Board object
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Get the parent node of this node.
	 * 
	 * @return Node object
	 */
	public Node getParentNode() {
		return parentNode;
	}

	/**
	 * Determine which animal is being moved
	 * 
	 * @return
	 */
	private Piece getAnimalToMove() {
		for (Piece p : animalList) {
			if ((p.toString()).equals(pieceToMove))
				return p;
		}
		return null; //animal not found
	}

	/**
	 * Check if this node contains a winning state (i.e. all rabbits in hole)
	 * 
	 * @return true if winning state.
	 */
	public boolean isWinningState() {
		return board.hasWon();
	}


	/**
	 * Generate a hashcode for the state of the board in order to ensure that we
	 * aren't checking the same state twice.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		return result;
	}

	/**
	 * This is simply to check if the animals are in the same positions in 2 nodes.
	 * i.e. checking if the "state" of the game is equal in both.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Node other = (Node) obj;
		if (animalList == null) {
			if (other.animalList != null)
				return false;
		}
		if (animalList.size() != other.animalList.size())
			return false;

		int numMatched = 0; // a variable to hold the number of animals that were matched in both arrayLists

		// compare the animals in both lists, if they have the same name but diff
		// positions return false
		for (Piece p : animalList) {
			for (Piece p2 : other.animalList) {
				if (p.toString().equals(p2.toString())) {
					if (!p.equals(p2)) {
						return false;
					} else {
						numMatched++;
					}

				}
			}
		}

		if (numMatched != animalList.size())
			return false; // if not the same in each

		return true;
	}

}
