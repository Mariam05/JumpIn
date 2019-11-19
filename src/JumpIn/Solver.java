package JumpIn;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * What we'll end up with is a tree, not a graph.
 * This class creates a solver for a game at a certain state. 
 * 
 * 
 * @author Mariam Almalki
 *
 */
public class Solver {

	/**
	 * The state of the game at which the player pressed hint. 
	 */
	private Node source;
	
	/**
	 * The animals on that board
	 */
	private List<Piece> sourceAnimals;
	
	/**
	 * Create a new solver with the current state of the game. 
	 * @param game
	 */
	public Solver(Game game) {
		// Get all the animals that are on the board currently
		sourceAnimals = game.getAnimalsExceptTails();

		source = new Node(null, sourceAnimals, null);

	}

	/**
	 * Return the first move that will lead to the solution
	 * @return a Command object containing the move. 
	 */
	public Command getHint() {
		Node n = BFS(); //solve the puzzle and get the node with the winning state
		
		//move up through the nodes until we reach the first first move in this solution
		while (n.getParentNode() != source) { 
			n = n.getParentNode();
			
		}
		return n.getCommand(); //return the command that led to that solution
	}

	/**
	 * An implementation of the breadth first search algorithm for the game. 
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

				if (currNode.isWinningState()) { //check if the current node contains a winning state
					return currNode;
				}

				for (Piece p : currNode.getPieces()) { // for each animal in the game
					ArrayList<Command> commands = p.getAllValidCommands(currNode.getBoard());// get all the possible
																								// commands for that
																								// piece
					// System.out.println("Animal: " + p.toString());
					for (Command c : commands) { // for each command, create a new node based on what state it leads to

						Node newNode = new Node(currNode, currNode.getPieces(), c);

						queue.add(newNode); // add the new node to the queue so that we can explore it

					}

				}
			}

		}

		return null; // solution not found, or there was an error
	}

}
