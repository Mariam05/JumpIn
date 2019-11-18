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
 * 
 * @author tomar
 *
 */
public class Solver {

	private Node source;
	private List<Piece> sourceAnimals;
	
	public Solver(Game game) {
		System.out.println("Getting help...");

		// Get all the animals that are on the board currently
		sourceAnimals = game.getAnimalsExceptTails();

		source = new Node(null, sourceAnimals, null);

	}

	public Command getHint() {
		Node n = BFS();
		
		while (n.getParentNode() != source) { //print moves to solution, for testing
			System.out.println(n.getCommand());
			n = n.getParentNode();
			
		}

		System.out.println("Done. Hint is: " + n.getCommand()); //for testing
		return n.getCommand();
	}

	private Node BFS() {

		Queue<Node> queue = new ArrayDeque<>(); // A queue to store the nodes that we want to explore
		queue.add(source);

		Set<Integer> visited = new HashSet<>(); // A set of visited nodes so that we don't loop infinitely

		while (!queue.isEmpty()) {

			Node currNode = queue.remove(); // get and remove the first element in the queue

			if (!visited.contains(currNode.hashCode())) { // check that the state of the node has not yet been visited
															// before proceeding
				visited.add(currNode.hashCode()); // add it to the visited list

				if (currNode.isWinningState()) {
					System.out.println("Found solution");
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
