package JumpIn;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Create a new board in each node and add all the pieces to that board.
 */
/**
 * A node contains the locations of the pieces on a board as well as the status
 * of the board.
 * 
 * @author tomar - the source is the current state of the game. - then we choose
 *         a piece - then we get all the valid moves for a piece, passing the
 *         source's board - then we create a new node with the location of the
 *         new piece. - may need to store the command that got it there - maybe
 *         we don't need a new board for each node? Could potentially have just
 *         one board to copy the source and then store each state in a hash...
 *         but then we'd need
 */
public class Node {

	private int identifier;
	private Node parentNode;
	private Board board;
	private List<Piece> animalList; // a list of animals
	private List<Node> children;
	private String pieceToMove;
	private int newX, newY;
	private Command command;

	public Node(Node parentNode, List<Piece> parentsAnimals, Command command) {
		this.parentNode = parentNode;
		this.command = command;

		board = new Board();

		children = new ArrayList<>();
		
		animalList = getCopyOfAnimalList(parentsAnimals); //Deep copy the animals of the parent node
		addAnimalsToBoard();

		if (command != null) { //command could be null if this is the source
			pieceToMove = command.getPiece();
			newX = command.getX();
			newY = command.getY();

			getAnimalToMove().handleMove(board, newX, newY);
			
		}

	}
	
	public List<Piece> getPieces(){
		return animalList;
	}
	
	public Command getCommand() {
		return command;
	}
	
	public Board getBoard() {
		return board;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public int getidentifier() {
		return identifier;
	}

	private Piece getAnimalToMove() {

		for (Piece p : animalList) {
			if ((p.toString()).equals(pieceToMove))
				return p;
		}
		return null;
	}

	public void addAnimalsToBoard() {
		for (Piece animal : animalList) {
			board.addPiece(animal, animal.getXPos(), animal.getYPos());
		}
	}

	public boolean isWinningState() {
		return board.hasWon();
	}

	public void addChild(Node n) {
		children.add(n);
	}

	public List<Piece> getCopyOfAnimalList(List<Piece> list) {
		List<Piece> copyAnimals = new ArrayList<>();
		for (Piece p : list) {
			Piece p2 = p.manufacturePiece(p.getStringRepresentation());
			copyAnimals.add(p2);
		}
		return copyAnimals;
	}

	/**
	 * Generate a hashcode for the state of the board in order to ensure that we
	 * aren't checking the same state twice.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((animalList == null) ? 0 : animalList.hashCode());
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
				if (p.toString().equals(other.toString())) {
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

	/**
	 * Just to test if aspects of this class work
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<Piece> a1 = new ArrayList<>();
		List<Piece> a2 = new ArrayList<>();
		
		Fox fox1 = new Fox("F1H", Fox.FoxType.HORIZONTAL, true);
		Fox fox1T = new Fox("F1T", Fox.FoxType.HORIZONTAL, false);
		((Fox) fox1).addAssociatedPart((Fox) fox1T);
		((Fox) fox1T).addAssociatedPart((Fox) fox1);
		
		fox1.setPosition(4, 3);
		fox1T.setPosition(fox1.getXPos() - 1, fox1.getYPos());
		
		
		Rabbit rabbit1 = new Rabbit("RA1", Color.WHITE); 
		rabbit1.setPosition(3, 0);
		Rabbit rabbit2 = new Rabbit("RA2", Color.GRAY);
		rabbit2.setPosition(4, 2);
		
		a1.add(fox1); a1.add(fox1T); a1.add(rabbit1); a1.add(rabbit2);
		
		a2.add(fox1); a2.add(fox1T); a2.add(rabbit1); a2.add(rabbit2); 
		
		Command c = new Command("move", "RA1" , "32");
		Node n1 = new Node( null, a1, null);
		Node n2 = new Node( null, a2, c);
		
		System.out.println("n1: " + n1.hashCode());
		System.out.println("n2: " + n2.hashCode());	
		Set<Integer> visited = new HashSet<>();
		visited.add(n1.hashCode());
	
		System.out.println(visited.contains(n2.hashCode()));
		
		//System.out.println(((Integer)n1.hashCode()).equals((Integer)n2.hashCode()));
	}

}
