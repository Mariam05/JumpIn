package JumpIn;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * What we'll end up with is a tree, not a graph. This class creates a solver
 * for a game at a certain state.
 * 
 * 
 * @author Mariam Almalki
 *
 */
public class Solver  {
	
	/**
	 * The state of the game at which the player pressed hint.
	 */
	private Node source;

	/**
	 * Create a new solver with the current state of the game.
	 * 
	 * @param game
	 */
	public Solver(Board board) {
		source = new Node(null, board.getStringRepresentation(), null);
	}

	/**
	 * Return the first move that will lead to the solution
	 * 
	 * @return a Command object containing the move.
	 */
	public Command getHint() {
		Node n = BFS(); // solve the puzzle and get the node with the winning state

		if (n == null) return null; //no solution found
		
		// move up through the nodes until we reach the first first move in this
		// solution
		while (n.getParentNode() != null && n.getParentNode() != source) {
			n = n.getParentNode();

		}
		return n.getCommand(); // return the command that led to that solution
	}

	/**
	 * An implementation of the breadth first search algorithm for the game.
	 * 
	 * @return Node object that contains the winning state.
	 */
	private Node BFS() {

		Queue<Node> queue = new ArrayDeque<>(); // A queue to store the nodes that we want to explore
		queue.add(source);

		Set<Integer> visited = new HashSet<>(); // A set of visited nodes so that we don't loop infinitely

		while (!queue.isEmpty()) {

			Node currNode = queue.remove(); // get and remove the first element in the queue
		
			if (!visited.contains(currNode.hashCode())) { // check that the state of the node has not yet been visited
															// before proceeding
				visited.add(currNode.hashCode()); // add it to the visited list

				if (currNode.isWinningState()) { // check if the current node contains a winning state
					return currNode;
				}

				for (Piece p : currNode.getPieces()) { // for each animal in the game
					if (!(p instanceof Mushroom)) { //to save time because mushrooms don't have any valid moves
						ArrayList<Command> commands = p.getAllValidCommands(currNode.getBoard());// get all the possible
																									// commands for that
																									// piece
						for (Command c : commands) { // for each command, create a new node based on what state it leads
														// to
							
							Node newNode = new Node(currNode, currNode.getBoard().getStringRepresentation(), c);

							queue.add(newNode); // add the new node to the queue so that we can explore it

						}
					}

				}
			}

		}

		return null; // solution not found, or there was an error
	}

}
