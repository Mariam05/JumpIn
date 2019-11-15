package JumpIn;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A node contains the locations of the pieces on a board as well as the status of the board. 
 * @author tomar
 * - the source is the current state of the game. 
 * - then we choose a piece
 * - then we get all the valid moves for a piece, passing the source's board
 * - then we create a new node with the location of the new piece. 
 * 
 */
public class Node {

		private int identifier;
	 	private HashMap<Piece, int[]> animals; //a hashmap with all the pieces and their current locations
	 	private Node parentNode;
	 	private Game game;
	 	
	 	public Node(int identifier, Game game, Node parentNode) {
	 		this.parentNode = parentNode;
	 		
	 		
	 	}
	 	
}



